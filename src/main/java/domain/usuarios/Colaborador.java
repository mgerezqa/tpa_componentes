package domain.usuarios;

import domain.contacto.MedioDeContacto;
import domain.donaciones.Contribucion;
import domain.donaciones.TipoContribucion;
import domain.formulario.Formulario;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

public abstract class Colaborador {
    @Getter @Setter protected Formulario formulario;
    @Getter @Setter protected MedioDeContacto unMedioDeContacto; //protected para que las clases hijas puedan acceder a este atributo
    @Getter @Setter protected Set<MedioDeContacto> mediosDeContacto; //Set para que no se repitan los medios de contacto este campo es comun en todos los colaboradores.
    /*ALTA Y BAJA*/
    // no es necesario acceder a "activo" dado que se puede dar de alta y baja con metodos y un getter basta para mostrarlo
    @Getter @Setter private Boolean activo; //(X) protected para que las clases hijas puedan acceder a este atributo
    @Getter public int puntosAcumulados = 0;

    @Getter protected List<TipoContribucion> colaboracionesQueRealizara;

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
        this.activo = true;
    // no tiene sentido preguntar si lo vas a setear en true
    //    if(this.activo != true){
    //        this.activo = true;
    //    }
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

    public abstract List<TipoContribucion> colaboracionesDisponibles();
    public Boolean puedeContribuir(Contribucion unaContribucion){
        return this.colaboracionesQueRealizara.contains(unaContribucion.getTipoContribucion());
    }

}
