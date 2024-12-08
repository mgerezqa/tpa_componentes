package dtos.responses;
import domain.contacto.Email;
import lombok.Data;

@Data
public class ColaboradorFisicoOutputDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private Boolean activo;
    private Integer puntosAcumulados;
}
