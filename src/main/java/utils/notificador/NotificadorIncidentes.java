package utils.notificador;
import utils.calculadorDistancia.ICalculadorDistanciaKM;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import domain.usuarios.Tecnico;
import lombok.Data;
import java.util.List;

@Data
public class NotificadorIncidentes {

    private List<Tecnico> tecnicos;
    private ICalculadorDistanciaKM calculadorDistanciaKM;

    public void notificarTecnico(Incidente incidente) {
        Tecnico tecnico = tecnicoMasCercano(incidente);

        // ... Logica de notificar a un tecnico ...

    }

    public Tecnico tecnicoMasCercano(Incidente incidente) {
        Heladera heladera = incidente.getHeladera();
        Tecnico tecnicoMasCercano = null;
        double distanciaMinima = Double.MAX_VALUE;
        for (Tecnico tecnico : tecnicos) {
            if (tecnico.estaActivo() && tecnico.getArea().estaEnElArea(heladera.getUbicacion())) {
                double distancia = calculadorDistanciaKM.calcularDistanciaKM(tecnico.getArea().getUbicacionPrincipal(), heladera.getUbicacion());
                if (distancia < distanciaMinima) {
                    distanciaMinima = distancia;
                    tecnicoMasCercano = tecnico;

                }

            }
        }

        return tecnicoMasCercano;
    }

}
