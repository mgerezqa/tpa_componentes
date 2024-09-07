package domain.geografia;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "localidad")
@NoArgsConstructor
public class Localidad {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(name = "localidad",nullable = false)
    private String localidad;

    public Localidad(String localidad) {
        this.localidad = localidad;
    }
}
