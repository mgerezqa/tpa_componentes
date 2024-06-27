package domain.temperatura;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.IncidenteFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


public class VerificadorTemperaturas {

    private List<Heladera> heladeras;

    public VerificadorTemperaturas(List<Heladera> heladeras) {
        this.heladeras = heladeras;
    }

    // Un cronjob ejecuta este "verificarTemperaturas", de existir alguna falla, la reporta.
    public void verificarTemperaturas(){
        LocalDateTime ahora = LocalDateTime.now();
        for (Heladera heladera : heladeras) {
            Duration duracion = Duration.between(heladera.ultimaTemperaturaRegistrada.getFechaYhora(), ahora);

            // FALLA CONEXION
            if (duracion.toMinutes() > 5) {
                IncidenteFactory.crearAlerta(heladera, "falla_conexion");
            }
        }
    }

}
