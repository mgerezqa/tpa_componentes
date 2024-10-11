package dtos.requests;
import domain.contacto.Email;
import lombok.Data;

@Data
public class ColaboradorFisicoInputDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private Boolean activo;
}
