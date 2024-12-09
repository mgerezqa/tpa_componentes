package utils.Broker.receptors;

import com.google.gson.JsonObject;
import config.ServiceLocator;
import domain.donaciones.Distribuir;
import domain.donaciones.Donacion;
import domain.donaciones.Vianda;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.SolicitudApertura;
import domain.puntos.CalculadoraPuntos;
import domain.tarjeta.RegistroDeUso;
import domain.tarjeta.Tarjeta;
import domain.tarjeta.TarjetaColaborador;
import domain.usuarios.ColaboradorFisico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.hibernate.Hibernate;
import repositorios.Repositorio;
import repositorios.repositoriosBDD.RepositorioTarjetas;

import java.util.Optional;

public class ReceptorApertura extends Receptor implements WithSimplePersistenceUnit {
    private Repositorio repositorio;
    public ReceptorApertura(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        entityManager().clear();
        try {
            JsonObject jsonObject = getJsonObjectFrom(mqttMessage);
            Long idHeladera = jsonObject.get("idH").getAsLong();
            Optional<Object> heladera = repositorio.buscarPorID(Heladera.class,idHeladera);
            String uuidTarjeta = jsonObject.get("uuidT").getAsString();
            Optional<Tarjeta> tarjetaDelColaborador = ServiceLocator.instanceOf(RepositorioTarjetas.class).obtenerPorUUID(uuidTarjeta);

            if(heladera.isEmpty() || tarjetaDelColaborador.isEmpty()){
                throw new RuntimeException("Hubo algun error en la busqueda de la heladera o tarjeta");
            }
            System.out.println("Mensaje recibido del topic "+ topic + ": "+ mqttMessage);
            Heladera heladeraEncontrada = (Heladera) heladera.get();

            TarjetaColaborador tarjetaColaborador = (TarjetaColaborador) tarjetaDelColaborador.get();
            //Registro uso de la tarjeta
            RegistroDeUso registroDeUso = RegistroDeUso.create(heladeraEncontrada,tarjetaColaborador);
            tarjetaColaborador.usoDeTarjeta(registroDeUso);
            //Busqueda de la sol apertura para permitir abrir la heladera.
            SolicitudApertura solicitudApertura =
                    heladeraEncontrada.getSolicitudesPendientes()
                            .stream()
                            .filter(s -> s.getColaborador().getId().equals(tarjetaColaborador.getColaborador().getId()))
                            .findFirst()
                            .orElse(null);

            if(solicitudApertura == null){
                throw new RuntimeException("No tienes permisos para realizar la apertura");
            }
            System.out.println(solicitudApertura);
            //Por ahora solo funciona con viandas
            //Busco la donacion vincula, y lo completo.
            Optional<Object> donacionVinculada = repositorio.buscarPorID(Donacion.class,solicitudApertura.getDonacionVinculada().getId());
            if(donacionVinculada.isEmpty()){
                throw new RuntimeException("No se encuentra la vienda vinculada a la sol de apertura, por favor vefique que tenga la donación como pendiente en las donaciones");
            }
            Donacion donacion = (Donacion) donacionVinculada.get();
            donacion.completar();
            //Agrego los puntos al colaborador segun si es donar vianda o distribuir
            if(donacion instanceof Vianda){
                ColaboradorFisico colaboradorFisico = (ColaboradorFisico) donacion.getColaboradorQueLaDono();

                //Lo correcto seria ir directamente a buscar a repo de viandas, y no traer todas las donaciones, y este calculo deberia
                //ir al calculador de puntos, ya que asi funciona con todas las donaciones de este tipo.
                int viandasDonadas = (int) repositorio.buscarTodos(Donacion.class)
                        .stream()
                        .filter(d -> d instanceof Vianda)
                        .filter(v -> ((Vianda) v).getColaboradorQueLaDono() == colaboradorFisico)
                        .count();
                System.out.println("Vianda donadas previamente: "+ viandasDonadas);
                int puntos = ServiceLocator.instanceOf(CalculadoraPuntos.class)
                        .puntosViandasDonadas(viandasDonadas + 1);
                donacion.setPuntosOtorgados(puntos);
                colaboradorFisico.sumarPuntos(puntos);
                heladeraEncontrada.ingresarVianda();
            } else if (donacion instanceof Distribuir) {
                //TODO otra logica de calculo
            }

            heladeraEncontrada.registrarApertura(solicitudApertura);
            withTransaction(() -> {
                repositorio.actualizar(heladeraEncontrada);
                repositorio.actualizar(solicitudApertura);
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
