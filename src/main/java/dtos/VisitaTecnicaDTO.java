package dtos;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VisitaTecnicaDTO {
    private String tecnicoNumeroDocumento;
    private String incidenteId;
    private LocalDateTime fechaYhora;
    private String comentario;
    private String foto;
    private boolean solucionado;
}