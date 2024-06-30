package domain.heladera.Heladera;

import domain.usuarios.Colaborador;
import dtos.SolicitudAperturaDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SolicitudAperturaFactory {
    public static SolicitudApertura crear(SolicitudAperturaDTO solicitudAperturaDTO, Heladera heladera, Colaborador colaborador) {
        SolicitudApertura solicitudApertura = new SolicitudApertura(LocalDateTime.parse(solicitudAperturaDTO.getFechaHoraInicio(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
                                                                    solicitudAperturaDTO.getDescripcion(),
                                                                    colaborador);
        return solicitudApertura;
    }
}
