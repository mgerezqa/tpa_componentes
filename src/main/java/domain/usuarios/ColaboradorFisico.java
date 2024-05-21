package domain.usuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import domain.contacto.MedioDeContacto;
import domain.donaciones.Donacion;
import domain.formulario.Formulario;
import domain.formulario.UnaRespuesta;
import lombok.Getter;
import lombok.ToString;

@ToString
public class ColaboradorFisico {

    @Getter private String nombre;
    @Getter private String apellido;
    @Getter private List <Donacion> donacionesRealizadas;
    @Getter private List <MedioDeContacto> medioContacto; //value object, que al menos complete un valor
    private LocalDate fechaNac;
    private String direccion;
    private Boolean activo;

    public ColaboradorFisico() {
        this.donacionesRealizadas = new ArrayList<>();
        this.medioContacto = new ArrayList<>();
        this.activo = false;
    }

    /*Punto de arranque*/
    public void cargarDatos(Formulario unFormulario){
        // aca se puede crear un metodo que valide si los campos son validos, pero en si ya deberian
        // ser validados por fuera antes de invocar este metodo
        if(!corroborarFormulario(unFormulario))
            throw new RuntimeException("Formulario incompleto/invalido!");
        unFormulario.getRespuestas().forEach(this::cargarDato);
    }

    private Boolean corroborarFormulario(Formulario unForm){
        // aca checkea si los campos minimos estan en el formulario
        //NO chequea si los datos son correctos, deberia estar chequeado de antemano
        List<String> camposNecesarios = Arrays.asList("nombre", "apellido", "contacto");
        return new HashSet<>(unForm.getDescripciones()).containsAll(camposNecesarios);
    }

    private void cargarDato(UnaRespuesta<?> unaResp){
        switch (unaResp.getCampo().getDescripcion()){
            case "nombre":
                this.nombre = (String) unaResp.getRespuesta();
            case "apellido":
                this.apellido = (String) unaResp.getRespuesta();
            case "contacto":
                this.medioContacto.add((MedioDeContacto) unaResp.getRespuesta());
            case "fecha de nacimiento":
                this.fechaNac = (LocalDate) unaResp.getRespuesta();
            case "direccion":
                this.direccion = (String) unaResp.getRespuesta();
            default:
        }
    }

    // como en discord dijo que no se hace la donacion, sino que se guarda el registro de donacion
    // ingresando el colaborador como un parametro de registro hacia la donacion, veo medio innecesario
    // que la clase de colaborador almacene las donaciones realizadas
    // pero si es necesario, solo es agregar a la lista de donaciones del colaborador
    public void cargarDonacion(Donacion donacion) {
        if (!donacion.puedeserDonada()){
            throw new RuntimeException(donacion.msgError());
        }
        donacionesRealizadas.add(donacion);
    }

    public void agregarRespuesta(UnaRespuesta respuesta) {
        //formulario.guardar(respuesta);
    }

    public void leerFormulario(){
        //formulario.leer();
    }
}
