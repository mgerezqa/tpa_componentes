package services.implem;
import domain.contacto.Email;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;
import dtos.requests.ColaboradorFisicoInputDTO;
import dtos.responses.ColaboradorFisicoOutputDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import services.interfaces.IServiceColaboradorFisico;

import java.util.ArrayList;
import java.util.List;

public class ServiceColaboradorFisico implements IServiceColaboradorFisico, WithSimplePersistenceUnit {

    private RepositorioColaboradores repositorioColaboradores;

    public ServiceColaboradorFisico(RepositorioColaboradores repositorioColaboradores) {
        this.repositorioColaboradores = repositorioColaboradores;
    }

    @Override
    public ColaboradorFisicoOutputDTO obtener(ColaboradorFisicoOutputDTO colaboradorFisico) {
        return null;
    }

    @Override
    public List<ColaboradorFisicoOutputDTO> obtenerTodos() {

        List<ColaboradorFisico> colaboradores = repositorioColaboradores.obtenerColaboradoresFisicos();
        List<ColaboradorFisicoOutputDTO> colaboradoresFisicosOutputDTO = new ArrayList<>();

        for(ColaboradorFisico colaborador : colaboradores){

            ColaboradorFisicoOutputDTO colaboradorFisicoOutputDTO = new ColaboradorFisicoOutputDTO();

            colaboradorFisicoOutputDTO.setNombre(colaborador.getNombre());
            colaboradorFisicoOutputDTO.setApellido(colaborador.getApellido());
            colaboradorFisicoOutputDTO.setId(colaborador.getId());
            colaboradorFisicoOutputDTO.setActivo(colaborador.activo);
            colaboradorFisicoOutputDTO.setPuntosAcumulados(colaborador.puntosAcumulados);
            colaboradorFisicoOutputDTO.setEmail(colaborador.email());

            colaboradoresFisicosOutputDTO.add(colaboradorFisicoOutputDTO);
        }
        return colaboradoresFisicosOutputDTO;
    }

    @Override
    public void crear(ColaboradorFisicoInputDTO colaboradorFisicoInputDTO) {
        ColaboradorFisico colaboradorFisico = new ColaboradorFisico();

        colaboradorFisico.setNombre(colaboradorFisicoInputDTO.getNombre());
        colaboradorFisico.setApellido(colaboradorFisicoInputDTO.getApellido());
        colaboradorFisico.setId(colaboradorFisicoInputDTO.getId());
        colaboradorFisico.setActivo(colaboradorFisicoInputDTO.getActivo());

        Email email = new Email(colaboradorFisicoInputDTO.getEmail());
        colaboradorFisico.agregarMedioDeContacto(email);

        colaboradorFisico.puntosAcumulados = 0;

        withTransaction(()->{
            repositorioColaboradores.guardar(colaboradorFisico);
        });
    }

    @Override
    public void modificar(ColaboradorFisicoInputDTO colaboradorFisico) {

    }

    @Override
    public void eliminar(ColaboradorFisicoInputDTO colaboradorFisico) {

    }
}
