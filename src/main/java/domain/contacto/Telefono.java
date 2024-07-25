package domain.contacto;

import domain.heladera.Heladera.Heladera;
import domain.usuarios.ColaboradorFisico;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Telefono extends MedioDeContacto {
    @Setter
    private String numero;

    public Telefono(String numero) {
        //Como las validaciones de formato para whatsapp y telegram son las mismas, se dejan en la clase padre.
        validarNumero(numero);
        this.numero = numero;
    }


    private void validarNumero(String numero) {
        if (numero == null) {
            throw new IllegalArgumentException("El número no puede ser nulo");
        }

        if (!numero.startsWith("+")) {
            throw new IllegalArgumentException("El número debe comenzar con un '+'");
        }

        if (numero.length() < 13 || numero.length() > 14) {
            throw new IllegalArgumentException("El número debe tener entre 13 y 14 caracteres");
        }
    }

    public abstract String tipoMedioDeContacto();

    public abstract String informacionDeMedioDeContacto();

    public void enviarMensaje(ColaboradorFisico colaborador, Heladera heladera){


    }

}
