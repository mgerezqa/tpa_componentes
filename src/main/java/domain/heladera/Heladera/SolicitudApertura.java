package domain.heladera.Heladera;

import domain.Config;
import domain.excepciones.ExcepcionSolicitudExpirada;
import domain.usuarios.Colaborador;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SolicitudApertura {
    private LocalDateTime fechaHoraInicio;
    private String detalle;
    private Colaborador colaborador;
    @Getter @Setter
    private LocalDateTime fechaHoraConcretado;

    public SolicitudApertura(LocalDateTime fechaHora, String detalle, Colaborador colaborador) {
        this.fechaHoraInicio = fechaHora;
        this.detalle = detalle;
        this.colaborador = colaborador;
    }

    public void completarSolicitud(LocalDateTime fechaHora) {
        if ((int) ChronoUnit.MINUTES.between(this.fechaHoraInicio, fechaHora) > Integer.parseInt(Config.getProperty("solicitudApertura.expiryHours"))*60) {
            throw new ExcepcionSolicitudExpirada("Su solicitud expiró. Debe generar una nueva solicitud.");
        }else {
            this.fechaHoraConcretado = fechaHora;
        }
    }
}
