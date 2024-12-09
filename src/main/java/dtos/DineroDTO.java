package dtos;

import domain.donaciones.FrecuenciaDeDonacion;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DineroDTO {

    private Long id;
    private Integer cantidad;
    private String frecuencia;
    private String nombreColaborador;
    private Long colaboradorId;
    private LocalDate fechaDeDonacion;

}
