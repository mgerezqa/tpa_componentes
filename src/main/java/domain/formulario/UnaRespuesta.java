package domain.formulario;

import lombok.Getter;

@Getter
public class UnaRespuesta<T> {
    private Campo campo;
    private T respuesta;

    // constructor para una respuesta sin contenido
    public UnaRespuesta(Campo unCampo){
        this.campo = unCampo;
        this.respuesta = null;
    }
    // constructor para una respuesta
    public UnaRespuesta(Campo unCampo, T unaRespuesta){
        this.campo = unCampo;
        this.respuesta = unaRespuesta;
    }

    public void responder(T unaRespuesta){
        this.respuesta = unaRespuesta;
    }

}
