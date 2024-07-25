package domain.contacto;

import domain.heladera.Heladera.Heladera;
import domain.usuarios.ColaboradorFisico;

public class Whatsapp extends Telefono{

    public Whatsapp(String numero){
        super(numero);
    }

    @Override
    public String tipoMedioDeContacto() {
        return "Whatsapp";
    }

    @Override
    public String informacionDeMedioDeContacto() {
        return getNumero();
    }

    @Override
    public void enviarMensaje(ColaboradorFisico colaborador, Heladera heladera) {
        //TODO: Implementar envío de mensaje por Whatsapp
        System.out.println("Enviando mensaje por Whatsapp a " + getNumero());
    }
}

//Un número de WhatsApp en Estados Unidos sería +1 seguido del número de teléfono móvil (por ejemplo, +1234567890).
//Un número de WhatsApp en México sería +52 seguido del número de teléfono móvil (por ejemplo, +521234567890).