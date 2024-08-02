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
                "📢 INFORME DE SUSCRIPCIÓN\n" +
                        "===============================\n\n" +
                        "Hola %s!\n\n" +
                        "🧊 Heladera %s\n" +
                        "🔔 Tipo de suscripción: %s\n\n" +
                        "¡Gracias por colaborar! 📬",
                colaborador.getNombre(),
                heladera.getNombreIdentificador(),
                tipoDeSuscripcion.getDescripcion()
        );

        bot.notifyUsers(getUsername(), mensaje);
        System.out.println("Enviando mensaje a " + getUsername() + ": \n\n" + mensaje);


    }

    public void enviarMensaje(Tecnico tecnico, Alerta alerta){
        String mensaje = String.format(
                "⚠️ INFORME DE ALERTA\n" +
                        "===============================\n\n" +
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

        bot.notifyUsers(getUsername(), mensaje);
        System.out.println("Enviando mensaje a " + getUsername() + ": " + mensaje);
    }

    public void enviarMensaje(Tecnico tecnico, FallaTecnica falla) {

        String mensaje = String.format(
                "🚨 INFORME DE FALLA TÉCNICA\n" +
                        "===============================\n\n" +
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
        bot.notifyUsers(getUsername(), mensaje);
        System.out.println("Enviando mensaje a " + getUsername() + ": \n\n" + mensaje);
    }

}
