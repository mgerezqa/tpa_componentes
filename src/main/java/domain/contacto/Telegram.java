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

    public Telegram(String userName) {
        this.setUsername(userName);
        validarUserName(userName);
        this.bot = TelegramBot.getInstance();

    }

    private void validarUserName(String username) {
        if (username == null) {
            throw new IllegalArgumentException("El username no puede ser nulo");
        }

        if (username.length() < 5 || username.length() > 20) {
            throw new IllegalArgumentException("El username debe tener entre 5 y 20 caracteres");
        }
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

        String mensaje = String.format(
                "📢 *Hola %s*!\n\n" +
                        "La heladera *%s* tiene una nueva suscripción de tipo: *%s*.\n\n" +
                        "¡Mantente informado! 🧊",
                colaborador.getNombre(),
                heladera.getNombreIdentificador(),
                tipoDeSuscripcion.getDescripcion()
        );

        bot.notifyUsers(getUsername(), mensaje);
        System.out.println("Enviando mensaje a " + getUsername() + ": " + mensaje);


    }

    public void enviarMensaje(Tecnico tecnico, FallaTecnica falla) {

        String mensaje = String.format(
                "🚨 *Falla Técnica* #%s\n\n" +
                        "*Hola %s %s*!\n\n" +
                        "Se ha generado una falla técnica de tipo: *%s*\n" +
                        "En la heladera: *%s*\n" +
                        "Fecha y hora: *%s*\n" +
                        "Reportada por: *%s*",
                falla.getId(),
                tecnico.getNombre(),
                tecnico.getApellido(),
                falla.getDescripcion(),
                falla.getHeladera().getNombreIdentificador(),
                falla.getFechaYHora(),
                falla.getReportadoPor()
        );

        bot.notifyUsers(getUsername(), mensaje);
        System.out.println("Enviando mensaje a " + getUsername() + ": " + mensaje);
    }

    public void enviarMensaje(Tecnico tecnico, Alerta alerta){
        String mensaje = String.format(
                "⚠️ *Alerta* #%s\n\n" +
                        "*Hola %s %s*!\n\n" +
                        "Se ha generado una alerta de tipo: *%s*\n" +
                        "En la heladera: *%s*\n" +
                        "Fecha y hora: *%s*",
                alerta.getId(),
                tecnico.getNombre(),
                tecnico.getApellido(),
                alerta.getTipoAlerta(),
                alerta.getHeladera().getNombreIdentificador(),
                alerta.getFechaYHora()
        );

        bot.notifyUsers(getUsername(), mensaje);
        System.out.println("Enviando mensaje a " + getUsername() + ": " + mensaje);
    }

}
