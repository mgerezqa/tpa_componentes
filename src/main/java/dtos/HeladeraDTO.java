package dtos;

import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.ModeloDeHeladera;
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
    private String estadoHeladera;
    private Float temperatura;
    private String modeloDeHeladera;
}
