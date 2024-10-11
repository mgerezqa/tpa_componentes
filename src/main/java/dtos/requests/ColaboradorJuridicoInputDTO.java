package dtos.requests;
import lombok.Data;

@Data
public class ColaboradorJuridicoInputDTO {
    private Long id;
    private Boolean activo;
    private String razonSocial;
    private String tipoRazonSocial;
    private String rubro;
    private String email;
}
