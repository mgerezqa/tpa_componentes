package domain.visitas;

import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.Heladera;
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

    @ManyToOne
    @JoinColumn(name = "tecnico_id", nullable = false)
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "heladera_id", nullable = false)
    private Heladera heladera;

    @Column(name = "fechaVisita", columnDefinition = "DATE")
    private LocalDateTime fechaVisita;

    @Column(name = "comentario")
    private String comentario;
    @Column(name = "foto")
    private String foto;

    @Column(name = "incidenteResuelto")
    public void incidenteResuelto(Boolean resuelto){
        if(resuelto) {
            heladera.setEstadoHeladera(EstadoHeladera.ACTIVA);
        }
    }
}
