package controladores;
import domain.excepciones.VisitaNoValidaException;
import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import domain.usuarios.Tecnico;
import domain.visitas.Visita;
import domain.visitas.VisitaFactory;
import dtos.VisitaTecnicaDTO;
import repositorios.interfaces.IRepositorioIncidentes;
import repositorios.interfaces.IRepositorioTecnicos;
import repositorios.interfaces.IRepositorioVisitas;

public class ControladorVisitasTecnicas {

    private IRepositorioVisitas repositorioVisitas;
    private IRepositorioIncidentes repositorioIncidentes;
    private IRepositorioTecnicos repositorioTecnicos;

    public ControladorVisitasTecnicas(IRepositorioVisitas repositorioVisitas, IRepositorioIncidentes repositorioIncidentes, IRepositorioTecnicos repositorioTecnicos) {
        this.repositorioVisitas = repositorioVisitas;
        this.repositorioIncidentes = repositorioIncidentes;
        this.repositorioTecnicos = repositorioTecnicos;
    }

    public void registrarVisitas(VisitaTecnicaDTO visitaTecnicaDTO){

        Tecnico tecnico = repositorioTecnicos.obtenerTecnicoPorNumeroDocumento(visitaTecnicaDTO.getTecnicoNumeroDocumento());
        Incidente incidente = repositorioIncidentes.obtenerIncidentePorId(visitaTecnicaDTO.getIncidenteId());

        Visita visita = VisitaFactory.crearVisita(visitaTecnicaDTO, tecnico, incidente);
        repositorioVisitas.agregarVisita(visita);
    }
}
