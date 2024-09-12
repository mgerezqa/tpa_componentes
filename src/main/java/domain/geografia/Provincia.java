package domain.geografia;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "provincias")
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provincia", nullable = false)
    private String provincia;

    public Provincia(String provincia) {
        this.provincia = provincia;
    }
}
