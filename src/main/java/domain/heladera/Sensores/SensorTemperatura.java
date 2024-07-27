package domain.heladera.Sensores;
import domain.heladera.Heladera.Heladera;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
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
        // El sensor fisico nos envia la temperatura sensada.
        // Nosotros como sistema debemos ser capaces de "recibirla".
        // Se recibe el dato en un "string" que bien podria ser un archivo json u otro.
        Float temperaturaActual = Float.parseFloat(dato);
        //heladera.setUltimaTemperaturaRegistrada(dato);
        heladera.setUltimaTemperaturaRegistrada(temperaturaActual, LocalDateTime.now());
        if(heladera.temperaturaFueraDeRango()){
            heladera.fallaTemperatura();
            System.out.println("Activación de alerta de falla de temperatura!");
        }
    }
}
