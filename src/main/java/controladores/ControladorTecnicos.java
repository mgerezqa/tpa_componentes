package controladores;

import domain.heladera.Heladera.Heladera;
import domain.usuarios.Tecnico;
import dtos.HeladeraDTO;
import dtos.TecnicoDTO;
import io.javalin.http.Context;
import repositorios.repositoriosBDD.RepositorioTecnicos;
import utils.ICrudViewsHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorTecnicos implements ICrudViewsHandler {
    private RepositorioTecnicos repositorioTecnicos;

    public ControladorTecnicos(RepositorioTecnicos repositorioTecnicos) {
        this.repositorioTecnicos = repositorioTecnicos;
    }

    @Override
    public void index(Context context) {
        List<Tecnico> tecnicos = repositorioTecnicos.buscarTodosTecnicos();
        List<TecnicoDTO> tecnicosDTO = new ArrayList<>();
        for (Tecnico tecnico : tecnicos) {
            TecnicoDTO tecnicoDTO = new TecnicoDTO();
            tecnicoDTO.setId(tecnico.getId());
            tecnicoDTO.setActivo(tecnico.getActivo());
            tecnicoDTO.setNombre(tecnico.getNombre());
            tecnicoDTO.setApellido(tecnico.getApellido());
            tecnicoDTO.setNroDocumento(tecnico.getDocumento().getNumeroDeDocumento());
            tecnicoDTO.setTipoDocumento(tecnico.getDocumento().getTipo().toString());
            tecnicoDTO.setTamanioArea(tecnico.getArea().getTamanioArea().toString());
            tecnicoDTO.setCalle(tecnico.getArea().getUbicacionPrincipal().getCalle().getNombre());
            tecnicoDTO.setAltura(tecnico.getArea().getUbicacionPrincipal().getCalle().getAltura());
            tecnicosDTO.add(tecnicoDTO);
        }
        Map<String,List<TecnicoDTO>> model = new HashMap<>();
        model.put("tecnicos",tecnicosDTO);
        context.render("/dashboard/tecnicos.hbs",model);
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }
}
