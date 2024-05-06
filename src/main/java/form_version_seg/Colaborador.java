package form_version_seg;

import java.util.HashMap;
import java.util.Map;

public class Colaborador {

    private Map<String,String> informacion = new HashMap<>(); //Par clave valor, dato y contenido



    private Formulario formulario;


    public Colaborador() {
        this.formulario = new Formulario(this);
    }


    public void completarFormulario() {
        formulario.guardarRespuesta(this);
   }

    public void verificarInformacion() {
        formulario.leer(this);
    }

    public void setInformacion(String campo, String valor) {
        informacion.put(campo,valor);
    }

    public Formulario getFormulario() {
        return formulario;
    }

}
