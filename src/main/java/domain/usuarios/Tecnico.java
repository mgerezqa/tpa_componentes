package domain.usuarios;
import domain.contacto.MedioDeContacto;
import domain.formulario.documentos.Cuil;
import domain.formulario.documentos.Documento;
import domain.formulario.documentos.TipoDocumento;
import domain.geografia.area.AreaDeCobertura;
import domain.geografia.area.TamanioArea;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "tecnicos")
public class Tecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "documento_id", referencedColumnName = "id")
    private Documento documento;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "cuil_id", referencedColumnName = "id")
    private Cuil cuil;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "tecnico_id", referencedColumnName = "id")
    private List<MedioDeContacto> mediosDeContacto;

    @Embedded
    @Transient
    private AreaDeCobertura area;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "notificacion_recibida")
    private boolean notificacionRecibida;

    public Tecnico(String nombre, String apellido, Documento documento, Cuil cuil) {
        this.activo = true;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.cuil = cuil;
        this.mediosDeContacto = new ArrayList<>();
        this.area = new AreaDeCobertura(); // GENERICO
        //this.area = new AreaDeCobertura(null, TamanioArea.PEQUENA); // GENERICO

    }

    public Boolean estaActivo() {
        return activo;
    }

    public void darDeBaja(){
        this.activo = false;
    }

    public TipoDocumento tipoDeDocumento(){
        return documento.getTipo();
    }

    public String numeroDeDocumento(){
        return documento.getNumeroDeDocumento();
    }

    public void agregarMedioDeContacto(MedioDeContacto medioDeContacto){
        this.mediosDeContacto.add(medioDeContacto);
    }

    public void quitarMedioDeContacto(MedioDeContacto medioDeContacto){
        this.mediosDeContacto.remove(medioDeContacto);
    }

    public void limpiarMediosDeContacto(){
        this.mediosDeContacto.clear();
    }

    public void setAreaDeCobertura(AreaDeCobertura areaDeCobertura){
        this.area.setUbicacionPrincipal(areaDeCobertura.getUbicacionPrincipal());
        this.area.setTamanioArea((areaDeCobertura.getTamanioArea()));
    }

}
