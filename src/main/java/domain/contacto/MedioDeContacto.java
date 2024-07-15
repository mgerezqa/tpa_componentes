package domain.contacto;

import lombok.Getter;
import lombok.Setter;

public abstract class MedioDeContacto {

    @Getter @Setter
    private boolean notificar;

    public abstract String obtenerDescripcion();
}
