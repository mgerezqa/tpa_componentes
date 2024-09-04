package domain.tarjeta;

import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RegistroDeUso { //Cuándo la usó, y en cuál heladera.
    private LocalDateTime datetime;
    private Heladera heladera;

    public RegistroDeUso(Heladera heladera){
        this.datetime = LocalDateTime.now();
        this.heladera = heladera;
    }
}