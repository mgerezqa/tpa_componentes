package domain.heladera.Heladera;

import domain.Config;
import domain.donaciones.Donacion;
import domain.excepciones.ExcepcionSolicitudExpirada;
import domain.usuarios.Colaborador;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "solicitudes_apertura")
public class SolicitudApertura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_inicio", columnDefinition = "DATETIME")
    private LocalDateTime fechaHoraInicio;

    @Column(name = "detalle")
    private String detalle;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "colaborador_id")
    private Colaborador colaborador;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    private Donacion donacionVinculada;

    @Column(name = "fecha_concrecion",columnDefinition = "DATETIME")
    private LocalDateTime fechaHoraConcretado;

    public SolicitudApertura(LocalDateTime fechaHora, String detalle, Colaborador colaborador) {
        this.fechaHoraInicio = fechaHora;
        this.detalle = detalle;
        this.colaborador = colaborador;
    }

    public void completarSolicitud(LocalDateTime fechaHora) {
        if ((int) ChronoUnit.MINUTES.between(this.fechaHoraInicio, fechaHora) > Integer.parseInt(Config.getInstance().getProperty("solicitudApertura.expiryHours"))*60) {
            throw new ExcepcionSolicitudExpirada("Su solicitud expiró. Debe generar una nueva solicitud.");
        }else {
            this.fechaHoraConcretado = fechaHora;
        }
    }
}
