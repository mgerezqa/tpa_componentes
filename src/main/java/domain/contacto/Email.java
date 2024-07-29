package domain.contacto;

import domain.heladera.Heladera.Heladera;
import domain.incidentes.Alerta;
import domain.incidentes.FallaTecnica;
import domain.incidentes.Incidente;
import domain.mensajeria.EmailSender;
import domain.suscripciones.TipoDeSuscripcion;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.Tecnico;
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
    public void enviarMensaje(ColaboradorFisico colaboradorFisico, Heladera heladera, TipoDeSuscripcion tipoDeSuscripcion) throws MessagingException {
    emailSender.sendEmail(
                "Aviso de suscripción",
                "Hola " + colaboradorFisico.getNombre() + " " + colaboradorFisico.getApellido() +
                ", la heladera " + heladera.getNombreIdentificador() +
                tipoDeSuscripcion.getDescripcion(), getEmail()
    );
    }


    public void enviarMensaje(Tecnico tecnico, Alerta alerta) throws MessagingException {
        emailSender.sendEmail(
                "Alerta " + alerta.getId(),
                "Hola " + tecnico.getNombre() + " " + tecnico.getApellido() +
                ", se ha generado una alerta de tipo " + alerta.getTipoAlerta() +
                        " en la heladera " + alerta.getHeladera().getNombreIdentificador() +
                        " a las " + alerta.getFechaYHora(),
                getEmail()
        );
    }

    public void enviarMensaje(Tecnico tecnico, FallaTecnica falla) throws MessagingException {
        emailSender.sendEmail(
                "Falla Tecnica " + falla.getId(),
                "Hola " + tecnico.getNombre() + " " + tecnico.getApellido() +
                ", se ha generado una falla tecnica de tipo " + falla.getDescripcion() +
                        " en la heladera " + falla.getHeladera().getNombreIdentificador() +
                        " a las " + falla.getFechaYHora() + " reportada por " + falla.getReportadoPor(),
                getEmail()
        );
    }


}
