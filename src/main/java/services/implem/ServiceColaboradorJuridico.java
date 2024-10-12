package services.implem;

import domain.contacto.Email;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.ColaboradorJuridico;
import domain.usuarios.Rubro;
import domain.usuarios.TipoRazonSocial;
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


        return null;
    }

    @Override
    public void crear(ColaboradorJuridicoInputDTO colaboradorJuridicoInputDTO) {

    }

    @Override
    public void modificar(ColaboradorJuridicoInputDTO colaboradorJuridico) {

    }

    @Override
    public void eliminar(ColaboradorJuridicoInputDTO colaboradorJuridico) {

    }
}
