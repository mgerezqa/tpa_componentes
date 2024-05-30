package domain.usuarios;

import domain.contacto.MedioDeContacto;
import domain.donaciones.TipoContribucion;
import domain.formulario.Formulario;
import domain.formulario.UnaRespuesta;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ColaboradorJuridico extends Colaborador{
    private String razonSocial;
    private TipoRazonSocial tipoRazonSocial;
    private Rubro tipoDeRubro;
    private String direccion;

    public ColaboradorJuridico(Formulario unFormulario) {
        this.cargarFormulario(unFormulario);
        this.formulario = unFormulario;
        this.darDeAlta();
    }

    private void cargarFormulario(Formulario unFormulario){
        if(!formularioCompleto(unFormulario))
            throw new RuntimeException("Formulario incompleto/invalido!");
        unFormulario.getRespuestas().forEach(this::cargarRespuesta);
    }

    // aca checkea si los campos minimos estan en el formulario
    // NO chequea si los datos son correctos, deberia estar chequeado de antemano
    private Boolean formularioCompleto(Formulario unForm){
        List<String> camposNecesarios = Arrays.asList("razon social" ,"tipo", "rubro", "contacto", "forma de contribucion");
        return unForm.estaCompleto() && new HashSet<>(unForm.getDescripciones()).containsAll(camposNecesarios);
    }

    private void cargarRespuesta(UnaRespuesta<?> unaResp){
        switch (unaResp.getCampo().getDescripcion()){
            case "razon social":
                this.razonSocial = (String) unaResp.getRespuesta();
                break;
            case "tipo":
                this.tipoRazonSocial = (TipoRazonSocial) unaResp.getRespuesta();
                break;
            case "rubro":
                this.tipoDeRubro = (Rubro) unaResp.getRespuesta();
                break;
            case "contacto":
                this.agregarContacto((MedioDeContacto) unaResp.getRespuesta());
                break;
            case "direccion":
                this.direccion = (String) unaResp.getRespuesta();
                break;
            case "forma de contribucion":
                this.agregarFormaContribucionQueRealizara((TipoContribucion) unaResp.getRespuesta());
                break;
            default:
        }
    }

    public List<String> mostrarInformacionDeContacto(){
        return this.mediosDeContacto.stream().map(MedioDeContacto::obtenerDescripcion).collect(Collectors.toList());
    }

    @Override
    public List<TipoContribucion> contribucionesDisponibles() {
        return Arrays.asList(TipoContribucion.DINERO, TipoContribucion.HELADERA);
    }

    @Override
    public String toString() {
        return "ColaboradorJuridico{" +
                "razonSocial='" + razonSocial + '\'' +
                ", tipoRazonSocial=" + tipoRazonSocial +
                ", tipoDeRubro=" + tipoDeRubro +
                ", direccion='" + direccion + '\'' +
                ", formulario=" + formularioPresente() +
                ", mediosDeContacto=" + mostrarInformacionDeContacto() +
                ", puntosAcumulados=" + puntosAcumulados +
                ", contribucionesQueRealizara=" + contribucionesQueRealizara +
                '}';
    }

}
