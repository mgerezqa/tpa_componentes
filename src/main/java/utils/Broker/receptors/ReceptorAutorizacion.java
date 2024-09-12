package utils.Broker.receptors;

import com.google.gson.JsonObject;
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
        JsonObject jsonObject = getJsonObjectFrom(mqttMessage);
        Long idHeladera = Long.parseLong(jsonObject.get("idH").getAsString());
        Optional<Object> heladera = repositorio.buscarPorID(Heladera.class,idHeladera);
        Long idColaborador = Long.parseLong(jsonObject.get("idC").getAsString());
        Optional<Object> colaborador = repositorio.buscarPorID(ColaboradorFisico.class,idColaborador);

        if(heladera.isPresent() && colaborador.isPresent()){
            System.out.println("Mensaje recibido del topic "+ topic + ": "+ mqttMessage);
            Heladera heladeraEncontrada = (Heladera) heladera.get();
            ColaboradorFisico colaboradorEncontrado = (ColaboradorFisico) colaborador.get();
            SolicitudApertura solicitud = new SolicitudApertura(LocalDateTime.now(), "Solicitud apertura heladera.", colaboradorEncontrado);
            heladeraEncontrada.registrarSolicitud(solicitud);
            withTransaction(() -> {
                repositorio.actualizar(heladeraEncontrada);
            });
            System.out.println(heladeraEncontrada.getSolicitudesPendientes());
        }
    }
}
