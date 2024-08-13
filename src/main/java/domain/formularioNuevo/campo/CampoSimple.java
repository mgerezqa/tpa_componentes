package domain.formularioNuevo.campo;

import java.util.Collections;
import java.util.List;

public class CampoSimple implements iCampo {
    private String respuesta;

    @Override
    public void agregarRespuesta(String unaRespuesta) {
        this.respuesta = unaRespuesta;
    }

    @Override
    public String obtenerRespuesta() {
        return respuesta;
    }

    @Override
    public String obtenerRespuesta(Integer indice) {
        return null;
    }

    @Override
    public List<String> obtenerRespuestas() {
        return Collections.singletonList(respuesta);
    }
}
