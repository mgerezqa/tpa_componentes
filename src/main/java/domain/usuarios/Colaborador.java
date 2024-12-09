package domain.usuarios;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.formulario.documentos.Documento;
import domain.formulario.documentos.TipoDocumento;
import domain.geografia.Ubicacion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
// USE ESTA STRATEGY Y NO MAPPED SUPERCLASS, SOLO POR SI ES NECESARIO "RECUPERAR" A TODOS LOS COLABORADORES!
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Embedded
    private Documento documento;

    @JoinColumn(name = "usuario_id", nullable = false)
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Usuario usuario;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "colaborador_id")
    protected Set<MedioDeContacto> mediosDeContacto = new HashSet<>(); //Set para que no se repitan los medios de contacto este campo es comun en todos los colaboradores.

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "usuario_id")
    private List<Ubicacion> direcciones = new ArrayList<>();

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
        this.puntosAcumulados += Math.round(puntos);
    }

    public void restarPuntos(int puntos) {
        this.puntosAcumulados -= Math.round(puntos);
    }


    public String email() {
        for (MedioDeContacto medio : this.getMediosDeContacto()) {
            if (medio instanceof Email) {
                return ((Email) medio).getEmail();
            }
        }
        return null;
    }

    public void agregarDireccion(Ubicacion direccion) {
        this.direcciones.add(direccion);
    }

    public boolean identificarPorDocumento(TipoDocumento tipo, String numeroDeDocumento) {
        if (this.getDocumento() == null) {
            return false; // Si no hay documento, no puede haber coincidencia.
        }
        return this.getDocumento().getTipo() == tipo &&
                this.getDocumento().getNumeroDeDocumento().equals(numeroDeDocumento);
    }

}
