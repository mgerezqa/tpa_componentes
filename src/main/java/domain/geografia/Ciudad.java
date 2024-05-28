package domain.geografia;

import lombok.Getter;
import lombok.Setter;

public class Ciudad {
    @Getter @Setter
    private String nombreCiudad;

    public Ciudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }
}
