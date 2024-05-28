package domain.usuarios;

import domain.contacto.MedioDeContacto;
import domain.donaciones.TipoDonacion;
import domain.formulario.Formulario;
import domain.formulario.UnaRespuesta;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@Setter
public class ColaboradorJuridico implements Colaborador{
    private String razonSocial;
    private TipoRazonSocial tipo;
    private List <TipoDonacion> donacionesPermitidas;
    private List <MedioDeContacto> medioContacto;
    private String direccion;
    private Rubro rubro;
    private Boolean activo;
    private Formulario formularioColaborador;

    public ColaboradorJuridico(Formulario unFormulario) {
        this.medioContacto = new ArrayList<>();
        this.activo = false;
        this.donacionesPermitidas = new ArrayList<>(Arrays.asList(TipoDonacion.DONA_DINERO, TipoDonacion.DONA_HELADERA));
        this.cargarFormulario(unFormulario);
        this.formularioColaborador = unFormulario;
    }

    private void cargarFormulario(Formulario unFormulario){
        if(!formularioCompleto(unFormulario))
            throw new RuntimeException("Formulario incompleto/invalido!");
        unFormulario.getRespuestas().forEach(this::cargarRespuesta);
    }

    // aca checkea si los campos minimos estan en el formulario
    // NO chequea si los datos son correctos, deberia estar chequeado de antemano
    private Boolean formularioCompleto(Formulario unForm){
        List<String> camposNecesarios = Arrays.asList("razon social" ,"tipo", "contacto");
        return unForm.estaCompleto() && new HashSet<>(unForm.getDescripciones()).containsAll(camposNecesarios);
    }

    private void cargarRespuesta(UnaRespuesta<?> unaResp){
        switch (unaResp.getCampo().getDescripcion()){
            case "razon social":
                this.razonSocial = (String) unaResp.getRespuesta();
                break;
            case "tipo":
                this.tipo = (TipoRazonSocial) unaResp.getRespuesta();
                break;
            case "rubro":
                this.rubro = (Rubro) unaResp.getRespuesta();
                break;
            case "contacto":
                this.agregarContacto((MedioDeContacto) unaResp.getRespuesta());
                break;
            case "direccion":
                this.direccion = (String) unaResp.getRespuesta();
                break;
            default:
        }
    }

    public List<String> mostrarInformacionDeContacto(){
        return this.medioContacto.stream().map(MedioDeContacto::obtenerDescripcion).collect(Collectors.toList());
    }

    public void agregarContacto(MedioDeContacto unContacto){
        this.medioContacto.add(unContacto);
    }
    public void removerContacto(String descripcionContacto){
        medioContacto.removeIf(contacto -> contacto.obtenerDescripcion().equals(descripcionContacto));
    }

    @Override
    public Boolean puedeDonar(TipoDonacion tipoDonacion) {
        return this.donacionesPermitidas.contains(tipoDonacion);
    }

    @Override
    public void darAlta() {
        this.activo = true;
    }

    @Override
    public void darBaja() {
        this.activo = false;
    }

    @Override
    public Boolean estaActivo() {
        return this.activo;
    }

}
