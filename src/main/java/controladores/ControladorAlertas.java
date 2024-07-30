package controladores;

import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import domain.incidentes.IncidenteFactory;
import dtos.AlertaInputDTO;
import repositorios.interfaces.IRepositorioHeladeras;
import repositorios.interfaces.IRepositorioIncidentes;

public class ControladorAlertas {
    private IRepositorioIncidentes repositorioIncidentes;
    private IRepositorioHeladeras repositorioHeladeras;

    public ControladorAlertas(IRepositorioIncidentes repositorioIncidentes, IRepositorioHeladeras repositorioHeladeras) {
        this.repositorioIncidentes = repositorioIncidentes;
        this.repositorioHeladeras = repositorioHeladeras;
    }

    public void crear(AlertaInputDTO alertaInputDTO){
        Heladera heladera = repositorioHeladeras.obtenerHeladeraPorNombre(alertaInputDTO.getNombreDeHeladera());

        Incidente incidente = IncidenteFactory.crearAlerta(heladera, alertaInputDTO.getTipo());

        repositorioIncidentes.agregarIncidente(incidente);
    }
}
