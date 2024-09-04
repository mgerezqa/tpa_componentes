package domain.geografia;

import javax.persistence.*;

@Entity
@Table(name = "provincia")
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(name = "provincia", nullable = false)
    private String provincia;
    public Provincia(String provincia) {
        this.provincia = provincia;
    }
}
