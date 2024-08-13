package domain.formularioNuevo;

import java.util.List;

public interface iDatosDeRegistro {
    TipoCampo obtenerTipoCampo();
    String obtenerRespuesta();
    String obtenerRespuesta(Integer indice);
    List<String> obtenerRespuestas();
}
