package utils.Broker.receptors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.SolicitudApertura;
import domain.usuarios.Colaborador;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioHeladeras;

import java.time.LocalDateTime;

public class ReceptorAutorizacion implements IMqttMessageListener {
    private RepositorioHeladeras repositorioHeladeras;
    private RepositorioColaboradores repositorioColaboradores;
    public ReceptorAutorizacion(RepositorioHeladeras repositorioHeladeras, RepositorioColaboradores repositorioColaboradores) {
        this.repositorioHeladeras = repositorioHeladeras;
        this.repositorioColaboradores = repositorioColaboradores;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        Gson gson = new Gson();
        String jsonString = mqttMessage.toString();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        Integer idHeladera = Integer.parseInt(jsonObject.get("idH").getAsString());
        Heladera heladera = repositorioHeladeras.obtenerHeladeraPorID(idHeladera.toString());
        Integer idColaborador = Integer.parseInt(jsonObject.get("idC").getAsString());
        Colaborador colaborador = repositorioColaboradores.obtenerPorId(String.valueOf(idColaborador));
        if(heladera != null){
            System.out.println("Mensaje recibido del topic "+ topic + ": "+ mqttMessage);
            SolicitudApertura solicitud = new SolicitudApertura(LocalDateTime.now(), "Solicitud apertura heladera.", colaborador);
            heladera.registrarSolicitud(solicitud);
        }
    }
}
