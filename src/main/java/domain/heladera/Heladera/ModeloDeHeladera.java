package domain.heladera.Heladera;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "modelos_de_heladera")
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
    public ModeloDeHeladera(String nombreModelo, Float temperaturaMin, Float temperaturaMax) {
        this.nombreModelo = nombreModelo;
        this.temperaturaMinima = temperaturaMin;
        this.temperaturaMaxima = temperaturaMax;
    }

}
