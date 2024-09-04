package domain.geografia;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Barrio {
    @Column(name = "barrio")
    private String barrio;

    public Barrio(String barrio) {
        this.barrio = barrio;
    }

}
