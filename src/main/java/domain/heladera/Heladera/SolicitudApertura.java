package domain.heladera.Heladera;

import domain.Config;
import domain.excepciones.ExcepcionSolicitudExpirada;
import domain.usuarios.Colaborador;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Entity
@Table(name = "solicitud_apertura")
@NoArgsConstructor
public class SolicitudApertura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha_hora_inicio",columnDefinition = "DATETIME")
    private LocalDateTime fechaHoraInicio;
    @Column(name = "detalle")
    private String detalle;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "id_colaborador")
    private Colaborador colaborador;
    @Column(name = "fecha_hora_concretado",columnDefinition = "DATETIME")
    private LocalDateTime fechaHoraConcretado;
    @Transient
    private Config config;

    public SolicitudApertura(LocalDateTime fechaHora, String detalle, Colaborador colaborador) {
        this.fechaHoraInicio = fechaHora;
        this.detalle = detalle;
        this.colaborador = colaborador;

        config = Config.getInstance();
    }

    public void completarSolicitud(LocalDateTime fechaHora) {
        if ((int) ChronoUnit.MINUTES.between(this.fechaHoraInicio, fechaHora) > Integer.parseInt(config.getProperty("solicitudApertura.expiryHours"))*60) {
            throw new ExcepcionSolicitudExpirada("Su solicitud expiró. Debe generar una nueva solicitud.");
        }else {
            this.fechaHoraConcretado = fechaHora;
        }
    }
}
