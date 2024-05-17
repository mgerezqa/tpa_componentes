package domain.usuarios;

import domain.formulario.Formulario;
import lombok.Getter;
import lombok.Setter;

public abstract class Colaborador {
    @Getter
    @Setter
    private Formulario formulario;


    /*Punto de arranque*/
    public void completarFormulario(){
        this.formulario = new Formulario();
    }

    /*Sobrecarga*/
    public void completarFormulario(Formulario formulario){
        this.formulario = formulario;
    }

    public void agregarRespuesta(String label, String valor) {
        formulario.cargarValor(label,valor);
    }


    public void leerFormulario(){
        formulario.mostrarCampos();
    }
}
