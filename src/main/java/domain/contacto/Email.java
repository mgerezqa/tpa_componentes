package domain.contacto;

import lombok.Getter;
import lombok.ToString;

@ToString
public class Email implements MedioDeContacto {
    private String email;

    public Email(String email) {
        if (email == null) {
            throw new IllegalArgumentException("El email no puede ser nulo");
        }

        if (!email.contains("@")) {
            throw new IllegalArgumentException("El email debe contener un @");
        }

        if(!email.contains(".")){
            throw new IllegalArgumentException("El email debe contener un punto");
        }
        // el email no puden ser solo numeros
        if (email.matches("[0-9]+")) {
            throw new IllegalArgumentException("El email no puede ser solo números");
        }

        this.email = email;
    }

    @Override
    public String obtenerDescripcion() {
        return email;
    }
}
