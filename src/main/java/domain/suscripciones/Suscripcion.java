package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import domain.usuarios.ColaboradorFisico;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Suscripciones")
public class Suscripcion {
    @Id
    @GeneratedValue
    private String id;

    @Setter @Getter
    private EventManager eventManager;

    @Setter @Getter
    @ManyToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    @Setter @Getter
    @ManyToOne
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    private ColaboradorFisico colaboradorFisico;
    @Setter @Getter
    @ManyToOne
    @JoinColumn(name = "tipo_id", referencedColumnName = "id")
    private TipoDeSuscripcion tipoDeSuscripcion;


    public Suscripcion(Heladera heladera, ColaboradorFisico colaboradorFisico, TipoDeSuscripcion tipoDeSuscripcion) {
        this.heladera = heladera;
        this.tipoDeSuscripcion = tipoDeSuscripcion;

        if(!colaboradorFisico.getZona().estaEnZonaQueFrecuenta(colaboradorFisico,heladera))
            throw new RuntimeException("No esta cerca de la heladera");

        this.colaboradorFisico = colaboradorFisico;
        this.eventManager = heladera.getEventManager();
        this.eventManager.suscribe(this);
    }




}
