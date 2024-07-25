package domain.usuarios;

import domain.contacto.MedioDeContacto;
import domain.formulario.Formulario;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public abstract class  Colaborador {
    @Getter @Setter protected Formulario formulario;
    @Getter @Setter protected Set<MedioDeContacto> mediosDeContacto; //Set para que no se repitan los medios de contacto este campo es comun en todos los colaboradores.
    /*ALTA Y BAJA*/
    @Getter @Setter protected Boolean activo; //protected para que las clases hijas puedan acceder a este atributo
    @Getter public int puntosAcumulados = 0;

    public void agregarMedioDeContacto(MedioDeContacto medio) {
        mediosDeContacto.add(medio);
    }

    public void darDeAlta(){
        if(this.activo != true){
            this.activo = true;
        }
    }
    public void darDeBaja(){ this.activo = false; }

    public void sumarPuntos(int puntos) {
        this.puntosAcumulados += puntos;
    }
    public void restarPuntos(int puntos) {
        this.puntosAcumulados -= puntos;
    }
}
