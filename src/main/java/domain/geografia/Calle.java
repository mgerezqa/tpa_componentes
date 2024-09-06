package domain.geografia;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Setter
@Getter
@Embeddable
public class Calle {
    @Column(name = "nombre_calle")
    private String nombre;
    @Column(name = "altura_calle")
    private String altura;

    public Calle(String calle, String altura) {
        this.nombre = calle;
        this.altura = altura;
    }
}
