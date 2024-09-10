package domain.incidentes;
import domain.heladera.Heladera.Heladera;
import domain.usuarios.Usuario;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("FALLA-TECNICA")
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
}
