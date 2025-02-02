package domain.visitas;

import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import domain.usuarios.Tecnico;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "visitas")
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "tecnico_id", nullable = false)
    private Tecnico tecnico;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "heladera_id", nullable = false)
    private Heladera heladera;

    @Column(name = "fechaVisita", columnDefinition = "DATETIME")
    private LocalDateTime fechaVisita;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "foto")
    private String foto;

    //Testeo de como funcionaria el tema de mostrar al juridico las visitas vinculadas a un incidente
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "incidente_id", nullable = false)
    private Incidente incidente;

    @Column(name = "resuelto")
    private Boolean resuelto;

    public void incidenteResuelto(Boolean resuelto){
        if(resuelto) {
            heladera.setEstadoHeladera(EstadoHeladera.ACTIVA);
            heladera.setUltimaTemperaturaRegistrada(15f,LocalDateTime.now());
            incidente.resolverIncidente();
        }
    }
}
