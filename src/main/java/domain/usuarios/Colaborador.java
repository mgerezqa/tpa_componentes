package domain.usuarios;
import domain.contacto.MedioDeContacto;
import domain.formulario.Formulario;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.swing.border.EmptyBorder;
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

    @OneToMany
    @JoinColumn(name = "medioDeContacto_id")
    protected Set<MedioDeContacto> mediosDeContacto; //Set para que no se repitan los medios de contacto este campo es comun en todos los colaboradores.

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
}
