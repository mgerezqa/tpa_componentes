package domain.formulario;

import domain.formulario.Campo;
import lombok.Getter;

@Getter
public class UnaRespuesta<T> {
    private Campo campo;
    private T respuesta;

    public UnaRespuesta(Campo unCampo){
        this.campo = unCampo;
        this.respuesta = null;
    }
    public UnaRespuesta(Campo unCampo, T unaRespuesta){
        this.campo = unCampo;
        this.respuesta = unaRespuesta;
    }

    public void responder(T unaRespuesta){
        this.respuesta = unaRespuesta;
    }

}
