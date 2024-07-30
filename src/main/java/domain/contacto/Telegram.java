package domain.contacto;

import domain.Config;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Alerta;
import domain.incidentes.FallaTecnica;
import domain.mensajeria.TelegramBot;
import domain.suscripciones.TipoDeSuscripcion;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.Tecnico;
import jakarta.mail.MessagingException;
import lombok.Getter;

@Getter

public class Telegram extends Telefono {
    private TelegramBot bot;
    private Config config;

    public Telegram(String numero) {
        super(numero);
        this.bot = TelegramBot.getInstance();

    }

    @Override
    public String tipoMedioDeContacto() {
        return "Telegram";
    }

    @Override
    public String informacionDeMedioDeContacto() {
        return getNumero();
    }

    @Override
    public void enviarMensaje(ColaboradorFisico colaborador, Heladera heladera, TipoDeSuscripcion tipoDeSuscripcion) {
        System.out.println("Enviando mensaje a " + getNumero());
        bot.notifyUsers(
                "Hola " + colaborador.getNombre() +
                        ", la heladera " + heladera.getNombreIdentificador() +
                        tipoDeSuscripcion.getDescripcion());

    }

    public void enviarMensaje(Tecnico tecnico, FallaTecnica falla) {
        bot.notifyUsers("Falla Tecnica " + falla.getId() +
                "Hola " + tecnico.getNombre() + " " + tecnico.getApellido() +
                ", se ha generado una falla tecnica de tipo " + falla.getDescripcion() +
                " en la heladera " + falla.getHeladera().getNombreIdentificador() +
                " a las " + falla.getFechaYHora() + " reportada por " + falla.getReportadoPor()
        );
    }

    public void enviarMensaje(Tecnico tecnico, Alerta alerta){
        bot.notifyUsers(
                "Alerta " + alerta.getId() +
                "Hola " + tecnico.getNombre() + " " + tecnico.getApellido() +
                        ", se ha generado una alerta de tipo " + alerta.getTipoAlerta() +
                        " en la heladera " + alerta.getHeladera().getNombreIdentificador() +
                        " a las " + alerta.getFechaYHora()
        );
    }

}
