package domain.heladera.Sensores;
import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.IncidenteFactory;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@NoArgsConstructor
public class SensorMovimiento {

    private Heladera heladera;

    public SensorMovimiento(Heladera heladera) {
        this.heladera = heladera;
    }

    // ============================================================ //
    // MéTODOS //
    // ============================================================ //

    public void recibirMovimientoDetectado(){
        // A traves de este metodo, nuestro sistema es capaz de recibir una alerta del sensor fisico.
        IncidenteFactory.crearAlerta(heladera, "fraude");
        System.out.println("Activación de alerta de fraude!");
    }

}
