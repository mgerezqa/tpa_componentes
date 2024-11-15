package domain.geografia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
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
