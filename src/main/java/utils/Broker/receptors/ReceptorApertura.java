package utils.Broker.receptors;

import com.google.gson.JsonObject;
import config.ServiceLocator;
import domain.donaciones.Donacion;
import domain.donaciones.Vianda;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.SolicitudApertura;
import domain.tarjeta.RegistroDeUso;
import domain.tarjeta.Tarjeta;
import domain.tarjeta.TarjetaColaborador;
import domain.usuarios.ColaboradorFisico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import repositorios.Repositorio;
import repositorios.repositoriosBDD.RepositorioTarjetas;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReceptorApertura extends Receptor implements WithSimplePersistenceUnit {
    private Repositorio repositorio;
    public ReceptorApertura(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        JsonObject jsonObject = getJsonObjectFrom(mqttMessage);
        Long idHeladera = jsonObject.get("idH").getAsLong();
        Optional<Object> heladera = repositorio.buscarPorID(Heladera.class,idHeladera);
        String uuidTarjeta = jsonObject.get("uuidT").getAsString();
        Optional<Tarjeta> tarjetaDelColaborador = ServiceLocator.instanceOf(RepositorioTarjetas.class).obtenerPorUUID(uuidTarjeta);
        if(heladera.isPresent() && tarjetaDelColaborador.isPresent()){
            System.out.println("Mensaje recibido del topic "+ topic + ": "+ mqttMessage);
            Heladera heladeraEncontrada = (Heladera) heladera.get();

            TarjetaColaborador tarjetaColaborador = (TarjetaColaborador) tarjetaDelColaborador.get();
            //Registro uso de la tarjeta
            RegistroDeUso registroDeUso = new RegistroDeUso(heladeraEncontrada);
            registroDeUso.setTarjeta(tarjetaColaborador);
            tarjetaColaborador.usoDeTarjeta(registroDeUso);
            SolicitudApertura solicitudApertura =
                    heladeraEncontrada.getSolicitudesPendientes()
                            .stream()
                            .filter(s -> s.getColaborador().getId().equals(tarjetaColaborador.getColaborador().getId()))
                            .findFirst()
                            .orElse(null);
            //Por ahora solo funciona con viandas
            if (solicitudApertura != null) {
                Optional<Object> donacionVinculada = repositorio.buscarPorID(Donacion.class,solicitudApertura.getDonacionVinculada().getId());
                Donacion donacion = (Donacion) donacionVinculada.get();
                donacion.completar();
                heladeraEncontrada.registrarApertura(solicitudApertura);
                withTransaction(() -> {
                    repositorio.actualizar(heladeraEncontrada);
                    repositorio.actualizar(solicitudApertura);
                    repositorio.actualizar(donacion);
                });
            } else {
                throw new RuntimeException("No tienes permisos para realizar la apertura");
            }
        }
    }
}
