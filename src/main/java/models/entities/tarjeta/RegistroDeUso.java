package models.entities.tarjeta;

import models.entities.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
public class RegistroDeUso { //Cuándo la usó, y en cuál heladera.
    private LocalDate datetime;
    private Heladera heladera;

    public RegistroDeUso(Heladera heladera){
        this.datetime = LocalDate.now();
        this.heladera = heladera;
    }
}