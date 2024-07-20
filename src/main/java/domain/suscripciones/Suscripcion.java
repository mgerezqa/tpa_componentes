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
    private CriterioDeSuscripcion criterioDeSuscripcion;


    public Suscripcion(Heladera heladera, ColaboradorFisico colaboradorFisico, CriterioDeSuscripcion criterioDeSuscripcion) {
        this.heladera = heladera;
        this.criterioDeSuscripcion = criterioDeSuscripcion;

        if(!colaboradorFisico.getZona().estaEnZonaQueFrecuenta(colaboradorFisico,heladera))
            throw new RuntimeException("No esta cerca de la heladera");

        this.colaboradorFisico = colaboradorFisico;
        this.eventManager = heladera.getEventManager();
        this.eventManager.suscribe(this);
    }




}
