package utils.Broker.receptors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.SolicitudApertura;
import domain.usuarios.Colaborador;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import repositorios.interfaces.IRepositorioColaboradores;
import repositorios.interfaces.IRepositorioHeladeras;

import java.time.LocalDateTime;
import java.util.Optional;

public class ReceptorAutorizacion implements IMqttMessageListener {
    private IRepositorioHeladeras repositorioHeladeras;
    private IRepositorioColaboradores repositorioColaboradores;
    public ReceptorAutorizacion(IRepositorioHeladeras repositorioHeladeras, IRepositorioColaboradores repositorioColaboradores) {
        this.repositorioHeladeras = repositorioHeladeras;
        this.repositorioColaboradores = repositorioColaboradores;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        Gson gson = new Gson();
        String jsonString = mqttMessage.toString();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        Integer idHeladera = Integer.parseInt(jsonObject.get("idH").getAsString());
        Optional<Heladera> heladera = repositorioHeladeras.obtenerHeladeraPorID(idHeladera);
        Integer idColaborador = Integer.parseInt(jsonObject.get("idC").getAsString());
        Colaborador colaborador = repositorioColaboradores.buscarColaborador(idColaborador);
        if(heladera.isPresent()){
            System.out.println("Mensaje recibido del topic "+ topic + ": "+ mqttMessage);
            SolicitudApertura solicitud = new SolicitudApertura(LocalDateTime.now(), "Solicitud apertura heladera.", colaborador);
            heladera.get().registrarSolicitud(solicitud);
        }
    }
}
