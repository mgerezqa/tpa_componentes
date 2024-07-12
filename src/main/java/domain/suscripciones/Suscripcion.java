package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import domain.usuarios.ColaboradorFisico;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Suscripcion {

    private EventManager eventManager;
    private Heladera heladera;
    private ColaboradorFisico colaboradorFisico;
    private TipoDeSuscripcion tipoSuscripcion;

    public Suscripcion(Heladera heladera, ColaboradorFisico colaboradorFisico, TipoDeSuscripcion tipoSuscripcion) {
        this.eventManager = new EventManager();
        this.heladera = heladera;
        this.colaboradorFisico = colaboradorFisico;
        this.tipoSuscripcion = tipoSuscripcion;
    }


}
