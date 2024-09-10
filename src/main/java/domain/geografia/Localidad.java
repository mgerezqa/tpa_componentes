package domain.geografia;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "localidad")
@NoArgsConstructor
public class Localidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "localidad")
    private String localidad;

    public Localidad(String localidad) {
        this.localidad = localidad;
    }
}
