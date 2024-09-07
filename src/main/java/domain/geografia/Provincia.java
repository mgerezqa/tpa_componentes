package domain.geografia;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "provincia")
@NoArgsConstructor
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
