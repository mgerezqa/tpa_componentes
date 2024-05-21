package domain.usuarios;
import domain.contacto.Documentos.Cuil;
import domain.contacto.Documentos.Documento;
import domain.contacto.MedioDeContacto;
import domain.geografia.AreaDeCobertura;
import lombok.Getter;
import lombok.Setter;

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

    public Tecnico(String nombre, String apellido, Documento documento, Cuil cuil,
                   List<MedioDeContacto> mediosDeContacto, AreaDeCobertura area) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.cuil = cuil;
        this.mediosDeContacto = mediosDeContacto;
        this.area = area;
        this.activo = true;
    }


    // ============================================================ //

    public String tipoDeDocumento(){
        return documento.tipoDocumento();
    }

    public String numeroDeDocumento(){
        return documento.numeroDocumento();
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

