package utils.temperatura;
import domain.Config;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.IncidenteFactory;
import lombok.Getter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VerificadorTemperaturas {

    private List<Heladera> heladeras;
    @Getter
    private List<Heladera> heladerasConFallasDeConexion;
    @Getter
    private List<Heladera> heladerasConFallasDeTemperatura;
    @Getter
    public Long tiempoMaxConexion=5L;

    public VerificadorTemperaturas(List<Heladera> heladeras) {
        this.heladeras = heladeras;
        this.heladerasConFallasDeConexion = new ArrayList<>();
        this.heladerasConFallasDeTemperatura = new ArrayList<>();
        //this.tiempoMaxConexion = Config.getPropertyToLong("tiempo_max_conexion");
    }

    // Un cronjob ejecuta este "verificarTemperaturas", de existir alguna falla, la reporta.
    public void verificarTemperaturas(){
        LocalDateTime ahora = LocalDateTime.now();

        for (Heladera heladera : heladeras) {
            Duration duracion = Duration.between(heladera.ultimaTemperaturaRegistrada.getFechaYhora(), ahora);

            // FALLA CONEXIÓN
            if (duracion.toMinutes() > tiempoMaxConexion) {
                heladerasConFallasDeConexion.add(heladera);
                IncidenteFactory.crearAlerta(heladera, "falla_conexion");
            }

            // FALLA TEMPERATURA
            if (heladera.temperaturaFueraDeRango()){
                heladerasConFallasDeTemperatura.add(heladera);
                IncidenteFactory.crearAlerta(heladera,"temperatura");
            }
        }
    }

}
