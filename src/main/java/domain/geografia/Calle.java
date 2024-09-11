package domain.geografia;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class Calle {
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "altura")
    private String altura;

    public Calle(String calle, String altura) {
        this.nombre = calle;
        this.altura = altura;
    }
}
