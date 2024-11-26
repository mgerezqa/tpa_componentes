package dtos;

import domain.donaciones.FrecuenciaDeDonacion;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DineroDTO {

    private Integer cantidad;
    private FrecuenciaDeDonacion frecuencia;
    private String colaboradorId;
    private LocalDate fechaDeDonacion;

}
