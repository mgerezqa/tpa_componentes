package utils.notificador;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import domain.usuarios.Tecnico;
import lombok.Data;
import java.util.List;

@Data
public class NotificadorIncidentes implements INotificadorIncidentes{

    private List<Tecnico> tecnicos;

    public NotificadorIncidentes(List<Tecnico> tecnicos) {
        this.tecnicos = tecnicos;
    }

    @Override
    public void notificarTecnico(Incidente incidente) {
        Tecnico tecnico = tecnicoMasCercano(incidente);

        // ... Logica de notificar a un tecnico ...

    }
    @Override
    public Tecnico tecnicoMasCercano(Incidente incidente) {
        Heladera heladera = incidente.getHeladera();

        Tecnico tecnicoMasCercano = null;
        double distanciaMinima = Double.MAX_VALUE;
        for (Tecnico tecnico : tecnicos) {
            if (tecnico.estaActivo() && tecnico.getArea().estaEnElAreaDelTecnico(heladera.getUbicacion())) {
                double distancia = (heladera.getUbicacion().calcularDistanciaKmA(tecnico.getArea().getUbicacionPrincipal()));
                if (distancia < distanciaMinima) {
                    distanciaMinima = distancia;
                    tecnicoMasCercano = tecnico;

                }

            }
        }

        return tecnicoMasCercano;
    }
}
