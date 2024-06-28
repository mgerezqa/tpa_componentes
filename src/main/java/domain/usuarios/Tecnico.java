package domain.usuarios;
import domain.contacto.MedioDeContacto;
import domain.formulario.Cuil;
import domain.formulario.Documento;
import domain.formulario.TipoDocumento;
import domain.geografia.AreaDeCobertura;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter
public class Tecnico {

    private String nombre;
    private String apellido;
    private Documento documento;
    private Cuil cuil;
    private List<MedioDeContacto> mediosDeContacto;
    private AreaDeCobertura area;

    private Boolean activo;

    // ============================================================ //
    // < CONSTRUCTOR > //

    public Tecnico(String nombre, String apellido, Documento documento, Cuil cuil) {

        this.activo = true;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.cuil = cuil;
        this.mediosDeContacto = new ArrayList<>();
    }

    // ============================================================ //

    public TipoDocumento tipoDeDocumento(){
        return documento.getTipo();
    }

    public String numeroDeDocumento(){
        return documento.getNumeroDeDocumento();
    }

    public void darDeBaja() {
        this.activo = false;
    }

    public Boolean estaActivo() {
        return activo;
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

}

    // < ALTA > //
    // Para dar de alta un tecnico, solamente se crea una instancia del mismo, la cual
    // se inicializa con el booleano "activo" en true.
    // new Tecnico(nombre,apellido,numDocumento,cuil,[mediosDeContacto],area);

    // < BAJA > //
    // Para dar de baja un tecnico, se cambia el estado a activo a ´false´.

    // < MODIFICACIÓN > //
    // Al tener los atributos, que pueden ser modificados, con "setters",
    // estos pueden ser MODIFICADOS.

