package dtos;
import lombok.Data;

@Data
public class ColaboradorJuridicoDTO {
    private Long id;
    private Boolean activo;
    private String razonSocial;
    private String tipoRazonSocial;
    private String rubro;
    private String email;
    private int puntosAcumulados;
}
