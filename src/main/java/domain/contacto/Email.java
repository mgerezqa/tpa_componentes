package domain.contacto;

import domain.heladera.Heladera.Heladera;
import domain.mensajeria.EmailSender;
import domain.suscripciones.TipoDeSuscripcion;
import domain.usuarios.ColaboradorFisico;
import jakarta.mail.MessagingException;
import lombok.Getter;

@Getter
public class Email extends MedioDeContacto {
    private String email;
    private EmailSender emailSender;


    public Email(String email) {
        validarEmail(email);
        this.email = email;
        emailSender = EmailSender.getInstance();
    }

    private void validarEmail(String email){
        if (email == null) {
            throw new IllegalArgumentException("El email no puede ser nulo");
        }

        if (!email.contains("@")) {
            throw new IllegalArgumentException("El email debe contener un @");
        }

        if(!email.contains(".")){
            throw new IllegalArgumentException("El email debe contener un punto");
        }
        // el email no puden ser solo numeros
        if (email.matches("[0-9]+")) {
            throw new IllegalArgumentException("El email no puede ser solo números");
        }
        //validar formato de correo
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new IllegalArgumentException("El email no tiene un formato valido");
        }
    }

    @Override
    public String tipoMedioDeContacto() {
        return "Email";
    }

    @Override
    public String informacionDeMedioDeContacto() {
        return getEmail();
    }

    @Override
    public void enviarMensaje(ColaboradorFisico c, Heladera h, TipoDeSuscripcion tipoDeSuscripcion) throws MessagingException {


        emailSender.sendEmail(
                "Aviso de suscripción",
                "Hola " + c.getNombre() + " " + c.getApellido() +
                ", la heladera " + h.getNombreIdentificador() +
                tipoDeSuscripcion.getDescripcion(), getEmail());

    }


}
