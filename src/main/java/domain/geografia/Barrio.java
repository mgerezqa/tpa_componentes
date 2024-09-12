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
public class Barrio {
    @Column(name = "barrio")
    private String barrio;

    public Barrio(String barrio) {
        this.barrio = barrio;
    }

}
