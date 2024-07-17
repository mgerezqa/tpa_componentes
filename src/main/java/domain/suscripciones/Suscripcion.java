package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import domain.usuarios.ColaboradorFisico;
import lombok.Getter;
import lombok.Setter;

public class Suscripcion {

    @Setter @Getter
    private EventManager eventManager;
    @Setter @Getter
    private Heladera heladera;
    @Setter @Getter
    private ColaboradorFisico colaboradorFisico;
    @Setter @Getter
    private TipoDeSuscripcion tipoSuscripcion;

    public Suscripcion(Heladera heladera, ColaboradorFisico colaboradorFisico, TipoDeSuscripcion tipoSuscripcion) {
        this.heladera = heladera;
        this.colaboradorFisico = colaboradorFisico;
        this.tipoSuscripcion = tipoSuscripcion;

        this.eventManager = heladera.getEventManager();
        this.eventManager.setSuscripcion(this);
        this.eventManager.suscribe(colaboradorFisico);
    }



}
