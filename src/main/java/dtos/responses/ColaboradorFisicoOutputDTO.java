package dtos.responses;
import domain.contacto.Email;
import lombok.Data;

@Data
public class ColaboradorFisicoOutputDTO {
    private Long id;
    private Boolean activo;
    private String nombre;
    private String apellido;
    private String email;
    private Integer puntosAcumulados;
}
