package domain.usuarios;
import domain.contacto.MedioDeContacto;
import domain.formulario.Cuil;
import domain.formulario.Documento;
import domain.formulario.TipoDocumento;
import domain.geografia.area.AreaDeCobertura;
import domain.geografia.Ubicacion;
import domain.geografia.area.TamanioArea;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Tecnico {

    private String nombre;
    private String apellido;
    private Documento documento;
    private Cuil cuil;
    private List<MedioDeContacto> mediosDeContacto;
    private AreaDeCobertura area;
    private Boolean activo;
    private String id;

    // ============================================================ //
    // < CONSTRUCTOR > //
    // ============================================================ //

    public Tecnico(String nombre, String apellido, Documento documento, Cuil cuil) {
        this.activo = true;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.cuil = cuil;
        this.mediosDeContacto = new ArrayList<>();
        this.area = new AreaDeCobertura(null, TamanioArea.PEQUENA); // GENERICO
    }

    // ============================================================ //

    public Boolean estaActivo() {
        return activo;
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
    public void darDeBaja(){
        this.activo = false;
    }
    // ============================================================ //

    public void setAreaDeCobertura(AreaDeCobertura areaDeCobertura){
        areaDeCobertura.setUbicacionPrincipal(areaDeCobertura.getUbicacionPrincipal());
        areaDeCobertura.setTamanioArea((areaDeCobertura.getTamanioArea()));
    }
}
