package dtos;

import domain.heladera.Heladera.Heladera;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ViandaDTO {

    private Long id;
    private Boolean activa;
    private LocalDate fechaDonacion;
    private LocalDate fechaVencimiento;
    private Long colaboradorDonanteId;
    private String nombreColaborador;
    private Long heladeraActualId;
    private Integer cantidad;
}