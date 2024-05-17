package domain.usuarios;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ColaboradorFisico extends Colaborador {

    @NonNull @Getter @Setter  private String nombre;
    @NonNull @Getter @Setter private String apellido;

    public ColaboradorFisico(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }


//    public void donar(Donable donacion) {
//        if (!donacion.puedeserDonada()){
//            throw new RuntimeException(donacion.msgError());
//        }
//        donacionesRealizadas.add(donacion);
//    }


}
