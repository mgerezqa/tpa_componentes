package controladores;

import domain.heladera.Heladera.Heladera;
import dtos.HeladeraDTO;
import io.javalin.http.Context;
import repositorios.repositoriosBDD.RepositorioHeladeras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorHeladeras {
    RepositorioHeladeras repositorioHeladeras;

    public ControladorHeladeras(RepositorioHeladeras repositorioHeladeras) {
        this.repositorioHeladeras = repositorioHeladeras;
    }

    public void index(Context context) {
        List<Heladera> heladeras = repositorioHeladeras.obtenerTodasLasHeladeras();
        List<HeladeraDTO> heladerasDTO = new ArrayList<>();
        for (Heladera heladera : heladeras) {
            HeladeraDTO heladeraDTO = new HeladeraDTO();
            heladeraDTO.setNombreIdentificador(heladera.getNombreIdentificador());
            heladeraDTO.setEstadoHeladera(heladera.getEstadoHeladera());
            heladeraDTO.setCapacidadMax(heladera.getCapacidadMax());
            heladeraDTO.setCapacidadActual(heladera.getCapacidadActual());
            heladeraDTO.setTemperatura(heladera.getUltimaTemperaturaRegistrada());
            heladerasDTO.add(heladeraDTO);
        }
        Map<String, Object> model = new HashMap<>();
        model.put("heladeras", heladerasDTO);
        context.render("heladeras/heladeras.hbs", model);
    }
}
