package domain.usuarios;

import domain.contacto.MedioDeContacto;
import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.formulario.TipoCampo;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString
public class ColaboradorFisico extends Colaborador {

    @Getter @Setter  private String nombre;
    @Getter @Setter private String apellido;

    public ColaboradorFisico(String nombre, String apellido, MedioDeContacto medioDeContacto){
        this.nombre = nombre;
        this.apellido = apellido;
        this.unMedioDeContacto = medioDeContacto;
        this.activo = true;
        this.mediosDeContacto = new HashSet<>();

        this.agregarMedioDeContacto(medioDeContacto);

        this.completarFormulario();
    }

    @Override
    public void completarFormulario() {
        super.completarFormulario();
        //formulario.autoCompletarCampo(this);

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
