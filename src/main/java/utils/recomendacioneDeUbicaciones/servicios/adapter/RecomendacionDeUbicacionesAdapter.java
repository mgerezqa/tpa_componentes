package utils.recomendacioneDeUbicaciones.servicios.adapter;

import utils.recomendacioneDeUbicaciones.entidades.Comunidad;
import utils.recomendacioneDeUbicaciones.entidades.ListadoDeComunidades;
import utils.recomendacioneDeUbicaciones.servicios.RecomedacionDeUbicaciones;

import java.io.IOException;
import java.util.List;

public class RecomendacionDeUbicacionesAdapter implements IRecomendacionDeUbicacionesAdapter {
    private RecomedacionDeUbicaciones recomedacionDeUbicaciones;

    public RecomendacionDeUbicacionesAdapter(RecomedacionDeUbicaciones recomedacionDeUbicaciones) {
        this.recomedacionDeUbicaciones = recomedacionDeUbicaciones;
    }

    @Override
    public List<Comunidad> comunidadesRecomendadas(String token, Float latitud, Float longitud, Integer max, Float distanciaMax) throws IOException {
        ListadoDeComunidades listadoDeComunidades = recomedacionDeUbicaciones.listadoCumunidades(token,latitud,longitud,max,distanciaMax);
        return listadoDeComunidades.getComunidades();
    }
}
