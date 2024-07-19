package domain.suscripciones;

import domain.geografia.area.AreaDeCobertura;
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
    @Getter
    private AreaDeCobertura zona;

    public Suscripcion(Heladera heladera, ColaboradorFisico colaboradorFisico, TipoDeSuscripcion tipoSuscripcion) {
        this.heladera = heladera;
        this.tipoSuscripcion = tipoSuscripcion;
        this.zona = colaboradorFisico.getZonaQueFrecuenta();
        if(!zona.estaEnZonaQueFrecuenta(colaboradorFisico,heladera))
            throw new RuntimeException("No esta cerca de la heladera");

        this.colaboradorFisico = colaboradorFisico;
        this.eventManager = heladera.getEventManager();
        this.eventManager.suscribe(this);
    }




}
