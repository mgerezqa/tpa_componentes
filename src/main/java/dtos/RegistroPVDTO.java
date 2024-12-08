package dtos;
import lombok.Data;
import java.time.LocalDate;


@Data
public class RegistroPVDTO {
    private Long id;
    private Long colaboradorId;
    private String nombreColaborador;
    private LocalDate fechaDeDonacion;
    private Integer cantidad;
}
