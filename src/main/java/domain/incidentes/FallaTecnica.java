package domain.incidentes;
import domain.heladera.Heladera.Heladera;
import domain.usuarios.Usuario;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("falla_tecnica")
public class FallaTecnica extends Incidente {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario reportadoPor;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "foto")
    private String foto;

    public FallaTecnica(Heladera heladera) {
        super(heladera);
    }

    @Override
    public String obtenerDescripcion() {
        return "Falla Técnica: " + this.descripcion;
    }
}
