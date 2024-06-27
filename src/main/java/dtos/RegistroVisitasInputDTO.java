package dtos;
import domain.heladera.Heladera.Heladera;
import domain.tecnicos.Tecnico;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RegistroVisitasInputDTO {
    private Tecnico tecnico;
    private Heladera heladera;
    private LocalDateTime fechaVisita;
    private String comentario;
    private String foto;
    private boolean incidenteResuelto;

}
