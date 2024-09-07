package domain.geografia;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
public class Barrio {
    @Column(name = "barrio")
    private String barrio;

    public Barrio(String barrio) {
        this.barrio = barrio;
    }

}
