package dtos.responses;
import domain.usuarios.Rubro;
import lombok.Data;

@Data
public class ColaboradorJuridicoOutputDTO {
    private Long id;
    private Boolean activo;
    private String nombre;
    private String razonSocial;
    private String tipoRazonSocial;
    private Rubro rubro;
    private String email;
    private Integer puntosAcumulados;
}
