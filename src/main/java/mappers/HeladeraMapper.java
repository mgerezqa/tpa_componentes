package mappers;

import domain.heladera.Heladera.Heladera;
import mappers.dtos.HeladeraDTO;

import java.time.format.DateTimeFormatter;

public class HeladeraMapper {

    public static HeladeraDTO toDTO(Heladera heladera) {
        HeladeraDTO dto = new HeladeraDTO();
        dto.setId(heladera.getId());
        dto.setEstado(heladera.getEstadoHeladera().toString());
        dto.setEstacion(heladera.getNombreIdentificador());
        dto.setDireccion(heladera.getUbicacion().getCalle().obtenerFormatoCalleAltura());
        dto.setLatitud(heladera.getUbicacion().getLatitud());
        dto.setLongitud(heladera.getUbicacion().getLongitud());
        dto.setTemperatura(heladera.getUltimaTemperaturaRegistrada());
        dto.setCapacidadMax(heladera.getCapacidadMax());
        dto.setViandasDisponibles(heladera.getCapacidadActual());
        dto.setInicioOperacion(heladera.getFechaInicioFuncionamiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return dto;
    }
} 