package domain.incidentes;
import domain.heladera.Heladera.Heladera;
import domain.usuarios.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("FALLA-TECNICA")
public class FallaTecnica extends Incidente {

    @Transient
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", nullable = false)
    private Usuario reportadoPor;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "foto")
    private String foto;

    public FallaTecnica(Heladera heladera) {
        super(heladera);
    }
}
