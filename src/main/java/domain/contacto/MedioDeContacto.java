package domain.contacto;

import domain.heladera.Heladera.Heladera;
import domain.incidentes.Alerta;
import domain.incidentes.FallaTecnica;
import domain.incidentes.Incidente;
import domain.suscripciones.TipoDeSuscripcion;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.Tecnico;
import jakarta.mail.MessagingException;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class MedioDeContacto {

    private boolean notificar;
    public abstract String tipoMedioDeContacto();
    public abstract String informacionDeMedioDeContacto();
    public abstract void enviarMensaje(ColaboradorFisico colaborador, Heladera heladera, TipoDeSuscripcion tipoDeSuscripcion) throws MessagingException;

    public void enviarMensaje(Tecnico tecnico, Alerta alerta) throws MessagingException {
    }
    public void enviarMensaje(Tecnico tecnico, FallaTecnica alerta) throws MessagingException {
    }
}
