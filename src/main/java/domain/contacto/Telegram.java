package domain.contacto;

import lombok.Getter;

@Getter

public class Telegram extends Telefono{

    public Telegram(String numero){
        super(numero);
    }

    @Override
    public String tipoMedioDeContacto() {
        return "Telegram";
    }
    @Override
    public String informacionDeMedioDeContacto() {
        return getNumero();
    }


}
