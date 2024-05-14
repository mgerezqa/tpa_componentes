package domain.usuarios;

import java.util.ArrayList;
import java.util.List;

import domain.contacto.MedioDeContacto;
import domain.donaciones.Donable;
import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.formulario.Respuesta;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ColaboradorFisico {

    @NonNull @Getter @Setter  private String nombre;
    @NonNull @Getter @Setter private String apellido;
    @Getter @Setter private Formulario formulario;
    @Getter private List <Donable> donacionesRealizadas = new ArrayList<>();


    @Getter private List <MedioDeContacto> medioContacto; //value object, que al menos complete un valor

    public ColaboradorFisico(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    /*Punto de arranque*/
    public void completarFormulario(){
        this.formulario = new Formulario();
    }

    /*Sobrecarga*/
    public void completarFormulario(Formulario formulario){
        this.formulario = formulario;
    }

    public void donar(Donable donacion) {
        if (!donacion.puedeserDonada()){
            throw new RuntimeException(donacion.msgError());
        }
        donacionesRealizadas.add(donacion);
    }

    public void agregarRespuesta(Respuesta respuesta) {
        formulario.guardar(respuesta);
    }

    public void leerFormulario(){
        formulario.leer();
    }
}
