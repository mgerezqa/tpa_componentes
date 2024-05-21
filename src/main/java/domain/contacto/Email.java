package domain.contacto;

import lombok.Getter;

@Getter
public class Email extends MedioDeContacto {
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
        //validar formato de correo
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new IllegalArgumentException("El email no tiene un formato valido");
        }

        this.email = email;
    }

    @Override
    public String obtenerDescripcion() {
        return email;
    }
}
