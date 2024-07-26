package domain.heladera.Sensores;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Alerta;
import domain.incidentes.IncidenteFactory;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SensorMovimiento {
    private Heladera heladera;

    public void recibirAlertaPorMovimientoDetectado(){
        IncidenteFactory.crearAlerta(this.heladera,"fraude");
        System.out.println("Activación de alerta de fraude!");
    }

}
