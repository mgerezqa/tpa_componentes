package dtos.responses;
import lombok.Data;

@Data
public class ColaboradorJuridicoOutputDTO {
    private Long id;
    private Boolean activo;
    private String nombre;
    private String razonSocial;
    private String rubro;
    private String email;
    private Integer puntosAcumulados;
}
