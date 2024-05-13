package domain.contacto;

public class Whatsapp extends MedioDeContacto{
    private String numero;

    public Whatsapp(String numero) {
        if (numero == null) {
            throw new IllegalArgumentException("El número de Whatsapp no puede ser nulo");
        }

        if (!numero.startsWith("+")) {
            throw new IllegalArgumentException("El número de Whatsapp debe comenzar con un '+'");
        }

        if (numero.length() < 12 || numero.length() > 13) {
            throw new IllegalArgumentException("El número de Whatsapp debe tener entre 12 y 13 caracteres");
        }

        this.numero = numero;
    }

    @Override
    public String obtenerDescripcion() {
        return numero;
    }
}

//Un número de WhatsApp en Estados Unidos sería +1 seguido del número de teléfono móvil (por ejemplo, +1234567890).
//Un número de WhatsApp en México sería +52 seguido del número de teléfono móvil (por ejemplo, +521234567890).