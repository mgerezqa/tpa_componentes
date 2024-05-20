package domain.usuarios;

import domain.contacto.MedioDeContacto;
import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.formulario.TipoCampo;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter @Getter
public class ColaboradorJuridico extends Colaborador{
    private String razonSocial;
    private TipoRazonSocial tipoRazonSocial;
    private Rubro tipoDeRubro;

    //Creando instancia con los campos obligatorios y cargandolos al formulario directamente
    public ColaboradorJuridico(String razonSocial, TipoRazonSocial tipoRazonSocial, Rubro rubro, MedioDeContacto unMedioDeContacto) {
        this.razonSocial = razonSocial;
        this.tipoRazonSocial = tipoRazonSocial;
        this.tipoDeRubro = rubro;
        this.unMedioDeContacto = unMedioDeContacto;
        this.mediosDeContacto = new HashSet<>();
        this.activo = true;

        this.agregarMedioDeContacto(unMedioDeContacto);
        completarFormulario();
    }

    public void completarFormulario(){
        super.completarFormulario();
        formulario.agregarCampo("Razon Social", new Campo(TipoCampo.CAMPO_NOMBRE));
        formulario.agregarCampo("Tipo de Razon Social", new Campo(TipoCampo.CAMPO_NOMBRE));
        formulario.agregarCampo("Rubro", new Campo(TipoCampo.CAMPO_NOMBRE));
        formulario.agregarCampo("Medio de contacto", new Campo(TipoCampo.CAMPO_MULTIPLE));

        formulario.cargarValor("Razon Social", this.getRazonSocial());
        formulario.cargarValor("Tipo de Razon Social", tipoRazonSocial.getDescripcion());
        formulario.cargarValor("Rubro", tipoDeRubro.getDescripcion());
        for (MedioDeContacto contacto : this.mediosDeContacto) {
            formulario.cargarValor("Medio de contacto", contacto.obtenerDescripcion());
        }
    }






}
