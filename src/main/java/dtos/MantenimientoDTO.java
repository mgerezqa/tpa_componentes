package dtos;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MantenimientoDTO {
    private Long id;
    private Long colaboradorID;
    private Long heladeraID;
    private String nombreColaborador;
    private String heladeraOrigen;
    private Integer mesesPuntuados;
    private LocalDate fechaDeDonacion;
}
