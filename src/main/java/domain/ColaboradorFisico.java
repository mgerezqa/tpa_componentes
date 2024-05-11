package domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ColaboradorFisico {

    @NonNull @Getter @Setter  private String nombre;
    @NonNull @Getter @Setter private String apellido;
    @NonNull @Getter @Setter private String medioContacto; //value object, que al menos complete un valor
    @Getter @Setter private Formulario formulario;
    @Getter private List <Donable> donacionesRealizadas = new ArrayList<>();

    public ColaboradorFisico(String nombre, String apellido, String medioContacto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.medioContacto = medioContacto;
    }

    public void completarFormulario(Formulario formulario) {
        setFormulario(formulario);
        formulario.guardarRespuesta(this);
   }

    public void verificarInformacion() {
        formulario.leer(this);
    }

    public void setFormulario(Formulario unFormulario){
        this.formulario = unFormulario;
    }

    public void donar(Donable donacion) {
        if (!donacion.puedeserDonada()){
            throw new RuntimeException(donacion.msgError());
        }
        donacionesRealizadas.add(donacion);
    }






}
