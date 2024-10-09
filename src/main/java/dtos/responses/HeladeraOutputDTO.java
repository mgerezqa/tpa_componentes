package dtos.responses;

import domain.heladera.Heladera.EstadoHeladera;
import lombok.Data;

@Data
public class HeladeraOutputDTO {
    private Long id;
    private String nombreIdentificador;
    private Integer capacidadMax;
    private Integer capacidadActual;
    private String fechaInicioFuncionamiento;
    private EstadoHeladera estadoHeladera;
    private Float temperatura;
}
