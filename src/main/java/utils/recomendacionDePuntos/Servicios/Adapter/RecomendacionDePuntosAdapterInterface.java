package utils.recomendacionDePuntos.Servicios.Adapter;

import utils.recomendacionDePuntos.Entidades.Punto;

import java.io.IOException;
import java.util.List;

public interface RecomendacionDePuntosAdapterInterface {
    List<Punto> obtenerPuntosRecomendados(Double latitud, Double longitud, Double radio) throws IOException;
}
