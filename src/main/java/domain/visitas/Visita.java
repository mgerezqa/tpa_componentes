package domain.visitas;

import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import domain.usuarios.Tecnico;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "visitas")
public class Visita {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "tecnico_id", nullable = false)
    private Tecnico tecnico;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "heladera_id", nullable = false)
    private Heladera heladera;

    @Column(name = "fechaVisita", columnDefinition = "DATE")
    private LocalDateTime fechaVisita;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "foto")
    private String foto;

    //Testeo de como funcionaria el tema de mostrar al juridico las visitas vinculadas a un incidente
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "incidente_id", nullable = false)
    private Incidente incidente;

    public void incidenteResuelto(Boolean resuelto){
        if(resuelto) {
            heladera.setEstadoHeladera(EstadoHeladera.ACTIVA);
        }
    }
}
