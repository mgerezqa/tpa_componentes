package domain.heladera.Heladera;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ModeloDeHeladera {

    @Getter
    private String nombreModelo;
    @Setter @Getter
    private Float temperaturaMinima;
    @Setter @Getter
    private Float temperaturaMaxima;

    public ModeloDeHeladera(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

}
