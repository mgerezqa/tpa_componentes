package domain.heladera.Heladera;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "modeloDeHeladera")
public class ModeloDeHeladera {

    @Id
    @GeneratedValue
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
