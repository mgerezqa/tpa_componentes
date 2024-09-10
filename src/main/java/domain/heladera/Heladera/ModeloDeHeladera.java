package domain.heladera.Heladera;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "modeloDeHeladera")
public class ModeloDeHeladera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombreModelo;
    @Column(name = "temperaturaMin")
    private Float temperaturaMinima;
    @Column(name = "temperaturaMax")
    private Float temperaturaMaxima;

    public ModeloDeHeladera(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

}