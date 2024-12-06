package controladores;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import domain.incidentes.IncidenteFactory;
import domain.reportes.Reporte;
import domain.reportes.ReporteFallas;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.Usuario;
import dtos.FallaTecnicaDTO;
import repositorios.interfaces.IRepositorioHeladeras;
import repositorios.interfaces.IRepositorioIncidentes;
import repositorios.interfaces.IRepositorioReportes;
import repositorios.interfaces.IRepositorioUsuarios;

public class ControladorFallasTecnicas {

    private IRepositorioIncidentes repositorioIncidentes;
    private IRepositorioHeladeras repositorioHeladeras;
    private IRepositorioUsuarios repositorioUsuarios;
    private IRepositorioReportes repositorioReportes;

    public ControladorFallasTecnicas(IRepositorioIncidentes repositorioIncidentes, IRepositorioHeladeras repositorioHeladeras, IRepositorioUsuarios repositorioUsuarios) {
        this.repositorioIncidentes = repositorioIncidentes;
        this.repositorioHeladeras = repositorioHeladeras;
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public void crearFallaTecnica(FallaTecnicaDTO fallaTecnicaDTO){

        // Encuentro al usuario y la heladera aqui en el controlador, ya que tengo acceso a los repos.
        // De aca, lo envio al "factory" junto con la fallaTecnicaDTO.
        Heladera heladera = repositorioHeladeras.obtenerHeladeraPorNombre(fallaTecnicaDTO.getNombreHeladera());
        Usuario usuario = repositorioUsuarios.buscarUsuarioPorNombre(fallaTecnicaDTO.getNombreUsuario());
        ColaboradorFisico colaboradorFisico = new ColaboradorFisico("asdwa","adsawda");
        colaboradorFisico.setUsuario(usuario);
        Incidente incidente = IncidenteFactory.crearFallaTecnica(fallaTecnicaDTO, heladera, colaboradorFisico);
        repositorioIncidentes.agregarIncidente(incidente);
    }

}


