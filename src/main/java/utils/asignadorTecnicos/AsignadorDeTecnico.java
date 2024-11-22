package utils.asignadorTecnicos;
import domain.excepciones.IncidenteNoExisteException;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Alerta;
import domain.incidentes.FallaTecnica;
import domain.incidentes.Incidente;
import domain.usuarios.Tecnico;
import lombok.Getter;
import lombok.Setter;
import utils.notificador.Notificador;

import java.util.List;

@Setter
@Getter
public class AsignadorDeTecnico {

    // Recibe: incidente y lista de tecnicos (todos)
    // Paso 1: Buscar al tecnico mas cercano
    // Paso 2: Asignar al tecnico al incidentes
    // Paso 3: Notificar al tecnico

    private Notificador notificador;
    private List<Tecnico> tecnicos;

    private Tecnico tecnicoMasCercano(Incidente incidente){
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
        System.out.println(tecnicoMasCercano);
        return tecnicoMasCercano;
    }

    public void asignarTecnicoA(Incidente incidente){

        // Técnico mas cercano
        Tecnico tecnico = this.tecnicoMasCercano(incidente);

        // Set técnico al incidente
        incidente.setTecnicoAsignado(tecnico);

        // Notificar al técnico
        if(incidente instanceof FallaTecnica){
            notificador.notificar(tecnico, (FallaTecnica) incidente);
        }
        else if(incidente instanceof Alerta){
            notificador.notificar(tecnico, (Alerta) incidente);
        }
        else {
            throw new IncidenteNoExisteException("Tipo de incidente desconocido");
        }
    }
}
