package domain.contacto;

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
}

//Un número de WhatsApp en Estados Unidos sería +1 seguido del número de teléfono móvil (por ejemplo, +1234567890).
//Un número de WhatsApp en México sería +52 seguido del número de teléfono móvil (por ejemplo, +521234567890).