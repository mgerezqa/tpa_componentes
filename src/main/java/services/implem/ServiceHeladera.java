package services.implem;

import domain.geografia.Calle;
import domain.geografia.Provincia;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import dtos.requests.HeladeraInputDTO;
import dtos.responses.HeladeraOutputDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.repositoriosBDD.RepositorioHeladeras;
import services.interfaces.IServiceHeladera;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceHeladera implements IServiceHeladera, WithSimplePersistenceUnit {
    private RepositorioHeladeras repositorioHeladeras;

    public ServiceHeladera(RepositorioHeladeras repositorioHeladeras) {
        this.repositorioHeladeras = repositorioHeladeras;
    }

    @Override
    public HeladeraOutputDTO obtener(HeladeraInputDTO heladera) {
        return null;
    }

    @Override
    public List<HeladeraOutputDTO> obtenerTodos() {
        List<Heladera> heladeras = repositorioHeladeras.obtenerTodasLasHeladeras();
        List<HeladeraOutputDTO> heladerasDTO = new ArrayList<>();
        for (Heladera heladera : heladeras) {
            HeladeraOutputDTO heladeraDTO = new HeladeraOutputDTO();
            heladeraDTO.setId(heladera.getId());
            heladeraDTO.setNombreIdentificador(heladera.getNombreIdentificador());
            heladeraDTO.setEstadoHeladera(heladera.getEstadoHeladera());
            heladeraDTO.setCapacidadMax(heladera.getCapacidadMax());
            heladeraDTO.setCapacidadActual(heladera.getCapacidadActual());
            if(heladera.getFechaInicioFuncionamiento()!=null){
                heladeraDTO.setFechaInicioFuncionamiento(heladera.getFechaInicioFuncionamiento().toString());
            }
            heladerasDTO.add(heladeraDTO);
        }
        return heladerasDTO;
    }

    @Override
    public void crear(HeladeraInputDTO heladeraInputDTO) {

        Heladera heladera = new Heladera(heladeraInputDTO.getModeloDeHeladera(),heladeraInputDTO.getNombreIdentificador(),heladeraInputDTO.getUbicacion());
        heladera.setEstadoHeladera(heladeraInputDTO.getEstadoHeladera());
        heladera.setCapacidadActual(heladeraInputDTO.getCapacidadActual());
        heladera.setCapacidadMax(heladeraInputDTO.getCapacidadMax());
        heladera.setFechaInicioFuncionamiento(LocalDate.parse(heladeraInputDTO.getFechaInicioFuncionamiento()));

        withTransaction(()->{
            repositorioHeladeras.guardar(heladera);
        });
    }

    @Override
    public void modificar(HeladeraInputDTO heladera) {

    }

    @Override
    public void eliminar(HeladeraInputDTO heladera) {

    }
}
