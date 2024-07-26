package domain.incidentes;
import domain.heladera.Heladera.Heladera;
import domain.usuarios.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FallaTecnica extends Incidente{
    private Usuario reportadoPor;
    private String descripcion;
    private String foto;

    public FallaTecnica(Heladera heladera) {
        super(heladera);
    }
}