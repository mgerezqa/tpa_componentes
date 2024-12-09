package dtos;
import lombok.Data;
import java.time.LocalDate;

@Data
public class DistribuirDTO {
    private Long id;
    private Long colaboradorId;
    private String nombreColaborador;
    private Long heladeraOrigenId;
    private Long heladeraDestinoId;
    private String motivo;
    private LocalDate fechaDonacion;
    private Integer cantidad;
}
