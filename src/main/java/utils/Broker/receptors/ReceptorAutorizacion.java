package utils.Broker.receptors;

import com.google.gson.JsonObject;
import domain.donaciones.Donacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.SolicitudApertura;
import domain.usuarios.ColaboradorFisico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import repositorios.Repositorio;

import java.time.LocalDateTime;
import java.util.Optional;

public class ReceptorAutorizacion extends Receptor implements WithSimplePersistenceUnit {
    private Repositorio repositorio;
    public ReceptorAutorizacion(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        entityManager().clear();
        JsonObject jsonObject = getJsonObjectFrom(mqttMessage);
        Long idHeladera = jsonObject.get("idH").getAsLong();
        Optional<Object> heladera = repositorio.buscarPorID(Heladera.class,idHeladera);
        Long idDonacion = jsonObject.get("idD").getAsLong();
        Optional<Object> donacion = repositorio.buscarPorID(Donacion.class,idDonacion);

        if(heladera.isPresent() && donacion.isPresent()){
            System.out.println("Mensaje recibido del topic "+ topic + ": "+ mqttMessage);
            Heladera heladeraEncontrada = (Heladera) heladera.get();
            Donacion donacionEncontrada = (Donacion) donacion.get();
            SolicitudApertura solicitud = new SolicitudApertura(LocalDateTime.now(), "Solicitud apertura heladera.", donacionEncontrada.getColaboradorQueLaDono());
            solicitud.setDonacionVinculada(donacionEncontrada);
            heladeraEncontrada.registrarSolicitud(solicitud);
            withTransaction(() -> {
                repositorio.actualizar(heladeraEncontrada);
            });
            System.out.println(heladeraEncontrada.getSolicitudesPendientes());
        }
    }
}
