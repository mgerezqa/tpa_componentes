package domain.contacto;

import domain.heladera.Heladera.Heladera;
import domain.suscripciones.TipoDeSuscripcion;
import domain.usuarios.ColaboradorFisico;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Telefono extends MedioDeContacto {
    private String numero;
    private String username;

    public abstract String tipoMedioDeContacto();

    public abstract String informacionDeMedioDeContacto();

    public void enviarMensaje(ColaboradorFisico colaborador, Heladera heladera, TipoDeSuscripcion tipoDeSuscripcion){


    }

}
