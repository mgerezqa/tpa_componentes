package domain.heladera.Sensores;
import config.ServiceLocator;
import domain.heladera.Heladera.Heladera;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.asignadorTecnicos.AsignadorDeTecnico;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SensorTemperatura {

    private Heladera heladera;

    private Float temperaturaMax;

    private Float temperaturaMin;

    public SensorTemperatura(Heladera heladera) {
        this.heladera = heladera;
    }

    // ============================================================ //
    // MéTODOS //
    // ============================================================ //

    public void recibirTemperaturaActual(String dato){
        Float temperaturaActual = Float.parseFloat(dato);
        //heladera.setUltimaTemperaturaRegistrada(dato);
        heladera.setUltimaTemperaturaRegistrada(temperaturaActual, LocalDateTime.now());
        if(heladera.temperaturaFueraDeRango()){
            new AsignadorDeTecnico().asignarTecnicoA(heladera.fallaTemperatura()); //Mirar tema de singleton,etc
            System.out.println("Activación de alerta de falla de temperatura!");
        }
    }
}
