package dtos;
import domain.heladera.Heladera.Heladera;
import domain.usuarios.Usuario;
import lombok.Data;

@Data
public class FallaTecnicaInputDTO {
    private Heladera heladera;
    private Usuario reportadoPor;
    private String descripcion;
    private String foto;
}
