package dtos.requests;
import lombok.Data;

@Data
public class ColaboradorJuridicoInputDTO {
    private Long id;
    private Boolean activo;
    private String nombre;
    private String razonSocial;
    private String rubro;
    private String email;
}
