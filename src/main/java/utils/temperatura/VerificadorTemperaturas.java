package utils.temperatura;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.IncidenteFactory;
import repositorios.interfaces.IRepositorioIncidentes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


public class VerificadorTemperaturas {
    private List<Heladera> heladeras;
    private IRepositorioIncidentes repositorioIncidentes;

    public VerificadorTemperaturas(List<Heladera> heladeras,IRepositorioIncidentes repositorioIncidentes) {
        this.heladeras = heladeras;
        this.repositorioIncidentes = repositorioIncidentes;
    }

    // Un cronjob ejecuta este "verificarTemperaturas", de existir alguna falla, la reporta.
    public void verificarTemperaturas(){
        LocalDateTime ahora = LocalDateTime.now();
        for (Heladera heladera : heladeras) {
            Duration duracion = Duration.between(heladera.ultimaTemperaturaRegistrada.getFechaYhora(), ahora);
            // FALLA CONEXION
            if (duracion.toMinutes() > 5) {
                //repositorioDeHeladera.modificar(heladera)? Para persistir que el estado pasa a inactiva de la heladera.
                repositorioIncidentes.agregarIncidente(IncidenteFactory.crearAlerta(heladera, "falla_conexion"));
            }
        }
    }

}