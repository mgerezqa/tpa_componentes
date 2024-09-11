package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import domain.usuarios.ColaboradorFisico;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Suscripciones")
public class Suscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private EventManager eventManager;

    @ManyToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    private ColaboradorFisico colaboradorFisico;

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
