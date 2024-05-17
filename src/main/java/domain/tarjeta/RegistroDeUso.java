package domain.tarjeta;

import domain.heladera.Heladera;

import java.time.LocalDate;

public class RegistroDeUso { //Cuándo la usó, y en cuál heladera.
    private LocalDate datetime;
    private Heladera heladera;

    public RegistroDeUso(Heladera heladera){
        this.datetime = LocalDate.now();
        this.heladera = heladera;
    }
}