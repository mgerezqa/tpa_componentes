package services.implem;

import domain.contacto.Email;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.ColaboradorJuridico;
import domain.usuarios.Rubro;
import dtos.requests.ColaboradorJuridicoInputDTO;
import dtos.responses.ColaboradorFisicoOutputDTO;
import dtos.responses.ColaboradorJuridicoOutputDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import services.interfaces.IServiceColaboradorFisico;
import services.interfaces.IServiceColaboradorJuridico;

import java.util.ArrayList;
import java.util.List;

public class ServiceColaboradorJuridico implements IServiceColaboradorJuridico, WithSimplePersistenceUnit {

    private RepositorioColaboradores repositorioColaboradores;

    public ServiceColaboradorJuridico(RepositorioColaboradores repositorioColaboradores) {
        this.repositorioColaboradores = repositorioColaboradores;
    }

    @Override
    public ColaboradorJuridicoOutputDTO obtener(ColaboradorJuridicoOutputDTO colaboradorJuridico) {
        return null;
    }

    @Override
    public List<ColaboradorJuridicoOutputDTO> obtenerTodos() {
        List<ColaboradorJuridico> colaboradores = repositorioColaboradores.obtenerColaboradoresJuridicos();
        List<ColaboradorJuridicoOutputDTO> colaboradoresJuridicoOutputDTO = new ArrayList<>();

        for(ColaboradorJuridico colaborador : colaboradores){

            ColaboradorJuridicoOutputDTO colaboradorJuridicoOutputDTO = new ColaboradorJuridicoOutputDTO();

            colaboradorJuridicoOutputDTO.setId(colaborador.getId());
            colaboradorJuridicoOutputDTO.setActivo(colaborador.activo);
            colaboradorJuridicoOutputDTO.setRazonSocial(colaborador.getRazonSocial());
            colaboradorJuridicoOutputDTO.setRubro(String.valueOf(colaborador.getTipoDeRubro()));
            colaboradorJuridicoOutputDTO.setPuntosAcumulados(colaborador.puntosAcumulados);
            colaboradorJuridicoOutputDTO.setEmail(colaborador.email());

            colaboradoresJuridicoOutputDTO.add(colaboradorJuridicoOutputDTO);
        }
        return colaboradoresJuridicoOutputDTO;
    }

    @Override
    public void crear(ColaboradorJuridicoInputDTO colaboradorJuridicoInputDTO) {
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico();

        colaboradorJuridico.setId(colaboradorJuridicoInputDTO.getId());
        colaboradorJuridico.setActivo(colaboradorJuridicoInputDTO.getActivo());
        colaboradorJuridico.setRazonSocial(colaboradorJuridicoInputDTO.getRazonSocial());
        colaboradorJuridico.setTipoDeRubro(Rubro.valueOf(colaboradorJuridicoInputDTO.getRubro()));

        Email email = new Email(colaboradorJuridicoInputDTO.getEmail());
        colaboradorJuridico.agregarMedioDeContacto(email);

        colaboradorJuridico.puntosAcumulados = 0;

        withTransaction(()->{
            repositorioColaboradores.guardar(colaboradorJuridico);
        });
    }

    @Override
    public void modificar(ColaboradorJuridicoInputDTO colaboradorJuridico) {

    }

    @Override
    public void eliminar(ColaboradorJuridicoInputDTO colaboradorJuridico) {

    }
}
