package domain.geografia;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Setter
@Getter
@Builder
@NoArgsConstructor
@Embeddable
public class Calle {
    @Column(name = "calle")
    private String nombre;
    @Column(name = "altura")
    private String altura;

    public Calle(String calle, String altura) {
        this.nombre = calle;
        this.altura = altura;
    }
    public Calle(String calleAltura) {
        String[] partes = calleAltura.split(", ");
        if (partes.length == 2) {
            this.nombre = partes[0];
            this.altura = partes[1];
        } else {
            throw new IllegalArgumentException("Formato incorrecto. Se espera 'Calle, Altura'");
        }
    }

    public String obtenerFormatoCalleAltura() {
        return this.nombre + ", " + this.altura;
    }
}
