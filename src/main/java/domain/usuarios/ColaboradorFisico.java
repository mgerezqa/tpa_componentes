package domain.usuarios;

import domain.carga_masiva.ContribucionCSV;
import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.donaciones.TipoContribucion;
import domain.formulario.Campo;
import domain.formulario.TipoCampo;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class ColaboradorFisico extends Colaborador {

    private String nombre;
    private String apellido;
    private Integer numeroDocumento;
    private TipoDocumento tipoDocumento;

    public ColaboradorFisico(String nombre, String apellido, MedioDeContacto medioDeContacto){
        this.nombre = nombre;
        this.apellido = apellido;
        this.unMedioDeContacto = medioDeContacto;
        this.darDeAlta();
        this.mediosDeContacto = new HashSet<>();
        this.agregarMedioDeContacto(medioDeContacto);
        this.completarFormulario();
    }
    // este constructor se usa cuando no se encuentra al colaborador al hacer la migracion
    public ColaboradorFisico(ContribucionCSV unaLectura) {
        this.nombre = unaLectura.getNombre();
        this.apellido = unaLectura.getApellido();
        this.tipoDocumento = unaLectura.getTipoDoc();
        this.numeroDocumento = unaLectura.getNroDoc();
        this.unMedioDeContacto = new Email(unaLectura.getMail());
    }

    @Override
    public void completarFormulario() {
        super.completarFormulario();

        formulario.agregarCampo("Nombre",new Campo(TipoCampo.CAMPO_NOMBRE));
        formulario.agregarCampo("Apellido",new Campo(TipoCampo.CAMPO_NOMBRE));
        formulario.agregarCampo("Medio de contacto",new Campo(TipoCampo.CAMPO_MULTIPLE));

        formulario.cargarValor("Nombre", this.getNombre());
        formulario.cargarValor("Apellido",this.getApellido());

        for (MedioDeContacto contacto : this.mediosDeContacto) {
            formulario.cargarValor("Medio de contacto", contacto.obtenerDescripcion());
        }
    }

    @Override
    public List<TipoContribucion> colaboracionesDisponibles() {
        return Arrays.asList(TipoContribucion.DINERO, TipoContribucion.VIANDA, TipoContribucion.DISTRIBUCION, TipoContribucion.TARJETA);
    }

    public boolean identificarPorDocumento(TipoDocumento tipoDoc, Integer nroDoc) {
        return getNumeroDocumento().equals(nroDoc) && getTipoDocumento().equals(tipoDoc);
    }
}
