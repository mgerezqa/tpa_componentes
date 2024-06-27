package domain.usuarios;

import domain.contacto.MedioDeContacto;
import domain.formulario.Campo;
import domain.formulario.TipoCampo;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

@Getter @Setter
public class ColaboradorFisico extends Colaborador {

    private String nombre;
    private String apellido;

    // ============================================================ //
    // Constructor //
    // ============================================================ //
    public ColaboradorFisico(String nombre, String apellido, MedioDeContacto medioDeContacto){
        this.nombre = nombre;
        this.apellido = apellido;
        this.unMedioDeContacto = medioDeContacto;
        this.activo = true;
        this.mediosDeContacto = new HashSet<>();
        this.agregarMedioDeContacto(medioDeContacto);
        this.completarFormulario();
    }

    // ============================================================ //
    // Metodos //
    // ============================================================ //
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

}
