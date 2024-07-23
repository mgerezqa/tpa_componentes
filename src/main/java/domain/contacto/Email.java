package domain.contacto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Email extends MedioDeContacto {
    private String email;
    @Setter
    private boolean notificar;

    public Email(String email) {
        validarEmail(email);
        this.email = email;
    }

    private void validarEmail(String email){
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
    }

    @Override
    public String tipoMedioDeContacto() {
        return "Email";
    }

    @Override
    public String informacionDeMedioDeContacto() {
        return getEmail();
    }
}
