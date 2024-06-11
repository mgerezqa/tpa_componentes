package domain.formulario;

import lombok.Getter;

@Getter
public class UnaRespuesta<T> {
    private String tipoCampo;
    private T respuesta;

    // constructor para una respuesta sin contenido
    public <E extends Enum<E>> UnaRespuesta(E unTipo){
        this.tipoCampo = unTipo.name();
        this.respuesta = null;
    }
    // constructor para una respuesta
    public <E extends Enum<E>> UnaRespuesta(E unTipo, T unaRespuesta){
        this.tipoCampo = unTipo.name();
        this.respuesta = unaRespuesta;
    }

    public void responder(T unaRespuesta){
        this.respuesta = unaRespuesta;
    }

    public <E extends Enum<E>> Boolean identificarPorTipo(E tipoCampo){
        return this.tipoCampo.equals(tipoCampo.name());
    }

}
