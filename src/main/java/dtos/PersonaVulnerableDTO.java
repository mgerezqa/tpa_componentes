package dtos;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PersonaVulnerableDTO {
    private Long id;
    private Boolean activo;
    private String nombre;
    private String apellido;
    private String tipoDocumento;
    private String numeroDocumento;
    private Integer cantidadHijos;
    private LocalDate fechaNacimiento;
    private LocalDate fechaRegistro;

}
