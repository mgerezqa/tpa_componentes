package domain.usuarios;

import java.util.ArrayList;
import java.util.List;

import domain.contacto.MedioDeContacto;
import domain.donaciones.Donable;
import domain.formulario.Formulario;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ColaboradorFisico {

    @NonNull @Getter @Setter  private String nombre;
    @NonNull @Getter @Setter private String apellido;
    @Getter private List <MedioDeContacto> medioContacto; //value object, que al menos complete un valor
    @Getter @Setter private Formulario formulario;
    @Getter private List <Donable> donacionesRealizadas = new ArrayList<>();

    public ColaboradorFisico(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.medioContacto = new ArrayList<>();
    }

    //-----------------------------Validaciones---------------------------------//
    public void agregarMedioContacto(MedioDeContacto nuevoMedio) {
        // Verificar el tipo del nuevo medio de contacto y contar cuántos hay en la lista actual
        int cantidadActual = contarMediosPorTipo(nuevoMedio.getClass());

        // Permitir agregar el nuevo medio de contacto solo si no hay otro del mismo tipo
        if (cantidadActual == 0) {
            medioContacto.add(nuevoMedio);
            System.out.println("Medio de contacto agregado correctamente: " + nuevoMedio.obtenerDescripcion());
        } else {
            System.out.println("No se puede agregar el medio de contacto. Ya existe otro medio de tipo: " + nuevoMedio.getClass().getSimpleName());
        }
    }

    // Método  para contar la cantidad de medios de un tipo específico en la lista
    private int contarMediosPorTipo(Class<?> tipoMedio) {
        int contador = 0;
        for (MedioDeContacto medio : medioContacto) {
            if (tipoMedio.isInstance(medio)) {
                contador++;
            }
        }
        return contador;
    }

    //-----------------------------Validaciones---------------------------------//


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
