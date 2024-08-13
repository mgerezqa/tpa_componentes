package domain.formularioNuevo.campo;

import java.util.List;

public interface iCampo {
    void agregarRespuesta(String unaRespuesta);
    String obtenerRespuesta();
    String obtenerRespuesta(Integer indice);
    List<String> obtenerRespuestas();
}
