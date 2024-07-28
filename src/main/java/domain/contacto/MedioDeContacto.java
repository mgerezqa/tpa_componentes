package domain.contacto;

import domain.heladera.Heladera.Heladera;
import domain.suscripciones.TipoDeSuscripcion;
import domain.usuarios.ColaboradorFisico;
import jakarta.mail.MessagingException;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class MedioDeContacto {

    private boolean notificar;
    public abstract String tipoMedioDeContacto();
    public abstract String informacionDeMedioDeContacto();
    public abstract void enviarMensaje(ColaboradorFisico c, Heladera h, TipoDeSuscripcion tipoDeSuscripcion) throws MessagingException;

}
