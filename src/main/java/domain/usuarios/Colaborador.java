package domain.usuarios;

import domain.contacto.MedioDeContacto;
import domain.formulario.Formulario;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public abstract class Colaborador {
    @Getter @Setter protected String id;
    @Getter @Setter protected Formulario formulario;
    @Getter @Setter protected MedioDeContacto unMedioDeContacto; //protected para que las clases hijas puedan acceder a este atributo
    @Getter @Setter protected Set<MedioDeContacto> mediosDeContacto; //Set para que no se repitan los medios de contacto este campo es comun en todos los colaboradores.
    /*ALTA Y BAJA*/
    @Getter @Setter protected Boolean activo; //protected para que las clases hijas puedan acceder a este atributo
    @Getter public int puntosAcumulados = 0;



    /*Punto de arranque*/
    public void completarFormulario(){
        this.formulario = new Formulario();
    }

    /*Sobrecarga*/
    public void completarFormulario(Formulario formulario){

        this.formulario = formulario;
    }

    public void agregarMedioDeContacto(MedioDeContacto medio) {
        if (mediosDeContacto.size() >= 3) {
            throw new IllegalArgumentException("No se pueden agregar más de 3 medios de contacto");
        }
        mediosDeContacto.add(medio);
    }

    public void darDeAlta(){
        if(!this.activo){
            this.activo = true;
        }
    }
    public void darDeBaja(){ this.activo = false; }

    public void agregarRespuesta(String label, String valor) {
        formulario.cargarValor(label,valor);
    }

    public void modificarRespuesta(String label, String valor) {
        formulario.modificarValor(label,valor);
    }

    public void leerFormulario(){
//        formulario.mostrarCampos();
        formulario.mostrarCampos(this);
    }

    public void sumarPuntos(int puntos) {
        this.puntosAcumulados += puntos;
    }
    public void restarPuntos(int puntos) {
        this.puntosAcumulados -= puntos;
    }
}
