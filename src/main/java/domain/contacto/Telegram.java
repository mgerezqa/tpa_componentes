package domain.contacto;

import domain.heladera.Heladera.Heladera;
import domain.mensajeria.TelegramBot;
import domain.usuarios.ColaboradorFisico;
import lombok.Getter;

@Getter

public class Telegram extends Telefono{
    private TelegramBot bot;

    public Telegram(String numero){
        super(numero);
//        this.bot = TelegramBot.getInstance();

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
    public void enviarMensaje(ColaboradorFisico colaborador, Heladera heladera) {
        System.out.println("Enviando mensaje a " + getNumero());
//        bot.notifyUsers("Hola " + colaborador.getNombre() + ", la heladera " + heladera.getNombreIdentificador() + " necesita mantenimiento.");
    }


}
