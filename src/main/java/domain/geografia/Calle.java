package domain.geografia;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Calle {

    private String nombre;
    private String altura;

    public Calle(String calle, String altura) {
        this.nombre = calle;
        this.altura = altura;
    }
}
