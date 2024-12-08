package dtos;
import lombok.Data;


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
    private String descripcion;
}