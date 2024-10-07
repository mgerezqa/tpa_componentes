package dtos;

import domain.heladera.Heladera.EstadoHeladera;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class HeladeraDTO {
    private Long id;
    private String nombreIdentificador;
    private Integer capacidadMax;
    private Integer capacidadActual;
    private String fechaInicioFuncionamiento;
    private EstadoHeladera estadoHeladera;
    private Float temperatura;
}
