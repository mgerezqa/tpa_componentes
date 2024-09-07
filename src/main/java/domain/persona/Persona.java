package domain.persona;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "persona")
@NoArgsConstructor
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "edad", nullable = false)
    private Integer edad;
    public Persona(String nombre, Integer edad){
        this.nombre = nombre;
        this.edad = edad;
    }
}
