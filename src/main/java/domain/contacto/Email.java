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
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@NoArgsConstructor
@Getter
@Entity
@DiscriminatorValue("email")
public class Email extends MedioDeContacto {

    @Setter
    @Column(name = "email")
    private String email;
    @Transient
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
        String mensaje = String.format(
                "Hola %s!\n\n" +
                        "🧊 Heladera: %s\n" +
                        "🔔 Tipo de suscripción: %s\n\n" +
                        "¡Gracias por colaborar! 📬",
                colaboradorFisico.getNombre(),
                heladera.getNombreIdentificador(),
                tipoDeSuscripcion.getDescripcion()
        );

        emailSender.sendEmail("📢 INFORME DE SUSCRIPCIÓN",mensaje,getEmail());
    }

    public void enviarMensaje(Tecnico tecnico, Alerta alerta) throws MessagingException {
        String mensaje = String.format(
                "Hola %s %s!\n\n" +
                        "🚨 Alerta: #%s\n" +
                        "📝 Tipo de Alerta: %s\n" +
                        "🧊 Heladera: %s\n" +
                        "🕒 Fecha y hora: %s\n\n",
                tecnico.getNombre(),
                tecnico.getApellido(),
                alerta.getId(),
                alerta.getTipoAlerta(),
                alerta.getHeladera().getNombreIdentificador(),
                alerta.getFechaYHora()
        );
        emailSender.sendEmail("⚠️ INFORME DE ALERTA", mensaje, getEmail());
    }

    public void enviarMensaje(Tecnico tecnico, FallaTecnica falla) throws MessagingException {
        String mensaje = String.format(
                "Hola %s %s!\n\n" +
                        "⚠️ Falla Técnica: #%s\n" +
                        "🔧 Descripción: %s\n" +
                        "🧊 Heladera: %s\n" +
                        "🕒 Fecha y hora: %s\n" +
                        "👤 Reportada por: %s\n\n",
                tecnico.getNombre(),
                tecnico.getApellido(),
                falla.getId(),
                falla.getDescripcion(),
                falla.getHeladera().getNombreIdentificador(),
                falla.getFechaYHora(),
                falla.getReportadoPor()
        );
        emailSender.sendEmail(
                "🚨 INFORME DE FALLA TÉCNICA",
                mensaje,
                getEmail()
        );
    }

}