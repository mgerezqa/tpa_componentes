package domain.incidentes;
import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class FallaTecnica extends Incidente{
    private String reportadoPor;
    private String descripcion;
    private String foto;

    public FallaTecnica(LocalDateTime fechaYHora, Heladera heladera, String reportadoPor, String descripcion, String foto) {
        super(heladera, fechaYHora);
        this.reportadoPor = reportadoPor;
        this.descripcion = descripcion;
        this.foto = foto;
    }
}
