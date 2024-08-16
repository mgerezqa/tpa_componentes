package domain.formularioNuevo.campo;

import java.util.ArrayList;
import java.util.List;

public class CampoMultiple implements iCampo{
    private List<String> respuestas;

    public CampoMultiple(){
        this.respuestas = new ArrayList<>();
    }

    @Override
    public void agregarRespuesta(String unaRespuesta) {
        this.respuestas.add(unaRespuesta);
    }

    @Override
    public String obtenerRespuesta() {
        return null;
    }

    @Override
    public String obtenerRespuesta(Integer indice) {
        return respuestas.get(indice);
    }

    @Override
    public List<String> obtenerRespuestas() {
        return respuestas;
    }

    @Override
    public Integer cantidadRespuestas() {
        return respuestas.size();
    }

    @Override
    public Boolean tieneRespuesta(){
        return (cantidadRespuestas()>0 && respuestas.get(0)!=null);
    }
}
