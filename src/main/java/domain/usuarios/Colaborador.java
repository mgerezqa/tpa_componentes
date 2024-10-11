package domain.usuarios;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.formulario.Formulario;
import lombok.*;

import javax.persistence.*;
import javax.swing.border.EmptyBorder;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
// USE ESTA STRATEGY Y NO MAPPED SUPERCLASS, SOLO POR SI ES NECESARIO "RECUPERAR" A TODOS LOS COLABORADORES!
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class  Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "colaborador_id")
    protected Set<MedioDeContacto> mediosDeContacto = new HashSet<>(); //Set para que no se repitan los medios de contacto este campo es comun en todos los colaboradores.

    @Column(name = "activo")
    public Boolean activo; //protected para que las clases hijas puedan acceder a este atributo -> lo cambie a public x el repo...

    @Column(name = "puntosAcumulados")
    public int puntosAcumulados = 0;

    public void agregarMedioDeContacto(MedioDeContacto medio) {
        mediosDeContacto.add(medio);
    }

    public void darDeAlta(){
        if(!this.activo){
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

    public String email() {
        for (MedioDeContacto medio : this.getMediosDeContacto()) {
            if (medio instanceof Email) {
                return ((Email) medio).getEmail();
            }
        }
        return null;
    }
}
