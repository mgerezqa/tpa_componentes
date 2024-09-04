package domain.contacto;

import domain.heladera.Heladera.Heladera;
import domain.suscripciones.TipoDeSuscripcion;
import domain.usuarios.ColaboradorFisico;

public class Whatsapp extends Telefono{

    public Whatsapp(String numero){
        this.setNumero(numero);
        validarNumero(numero);
    }



    private void validarNumero(String numero) {
        if (numero == null) {
            throw new IllegalArgumentException("El número no puede ser nulo");
        }

        if (!numero.startsWith("+")) {
            throw new IllegalArgumentException("El número debe comenzar con un '+'");
        }

        if (numero.length() < 13 || numero.length() > 14) {
            throw new IllegalArgumentException("El número debe tener entre 13 y 14 caracteres");
        }
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
    public void enviarMensaje(ColaboradorFisico colaborador, Heladera heladera, TipoDeSuscripcion tipoDeSuscripcion) {
        //TODO: Implementar envío de mensaje por Whatsapp
        System.out.println("Enviando mensaje por Whatsapp a " + getNumero());
    }
}

//Un número de WhatsApp en Estados Unidos sería +1 seguido del número de teléfono móvil (por ejemplo, +1234567890).
//Un número de WhatsApp en México sería +52 seguido del número de teléfono móvil (por ejemplo, +521234567890).