package domain.usuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import domain.contacto.MedioDeContacto;
import domain.donaciones.TipoDonacion;
import domain.formulario.Formulario;
import domain.formulario.UnaRespuesta;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ColaboradorFisico implements Colaborador{

    private String nombre;
    private String apellido;
    private List <MedioDeContacto> medioContacto;
    private List <TipoDonacion> donacionesPermitidas;
    private LocalDate fechaNac;
    private String direccion;
    private Boolean activo;

    public ColaboradorFisico(Formulario unFormulario) {
        this.medioContacto = new ArrayList<>();
        this.activo = false;
        this.donacionesPermitidas = new ArrayList<>(Arrays.asList(TipoDonacion.DONA_DINERO, TipoDonacion.DONA_VIANDA, TipoDonacion.DONA_REPARTO));
        this.cargarFormulario(unFormulario);
    }

    /*Punto de arranque*/
    // ingresa un formulario, verifica si el formulario cumple la condicion para ser cargado,
    // si cumple, se itera las respuestas y se van cargando los valores al colaborador,
    // si no cumple, tira excepcion
    private void cargarFormulario(Formulario unFormulario){
        if(!corroborarFormulario(unFormulario))
            throw new RuntimeException("Formulario incompleto/invalido!");
        unFormulario.getRespuestas().forEach(this::cargarRespuesta);
    }

    // checkea si los campos minimamente necesarios para el colaborador estan en el formulario
    // NO chequea si los datos son correctos, deberia estar chequeado de antemano
    private Boolean corroborarFormulario(Formulario unForm){
        List<String> camposNecesarios = Arrays.asList("nombre", "apellido", "contacto");
        return unForm.estaCompleto() && new HashSet<>(unForm.getDescripciones()).containsAll(camposNecesarios);
    }

    // toma una respuesta, y segun el campo asociado se setea su contenido en el atributo apropiado
    // si el campo no corresponde a ningun atributo del colaborador, no hace nada
    private void cargarRespuesta(UnaRespuesta unaResp){
        switch (unaResp.getCampo().getDescripcion()){
            case "nombre":
                this.nombre = (String) unaResp.getRespuesta();
                break;
            case "apellido":
                this.apellido = (String) unaResp.getRespuesta();
                break;
            case "contacto":
                this.medioContacto.add((MedioDeContacto) unaResp.getRespuesta());
                break;
            case "fecha de nacimiento":
                this.fechaNac = (LocalDate) unaResp.getRespuesta();
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

    @Override
    public Boolean puedeDonar(TipoDonacion tipoDonacion) {
        return this.donacionesPermitidas.contains(tipoDonacion);
    }
}
