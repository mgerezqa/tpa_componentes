package domain.contacto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class MedioDeContacto {

    private boolean notificar;

    public abstract String tipoMedioDeContacto();
    public abstract String informacionDeMedioDeContacto();

}
