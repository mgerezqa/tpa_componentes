package domain.geografia;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Localidad {
    @Column
    private String localidad;
    public Localidad(String localidad) {
        this.localidad = localidad;
    }
}
