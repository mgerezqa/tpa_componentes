package utils.notificador;

import domain.incidentes.Incidente;
import domain.suscripciones.Suscripcion;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TipoDeNotificacion {
    private Incidente tipoDeIncidente;
    private Suscripcion tipoDeSuscripcion;

}
