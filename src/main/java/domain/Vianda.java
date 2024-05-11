package domain;

import java.time.LocalDate;

public class Vianda implements Donable{

    private Heladera heladera;
    private LocalDate fechaVencimiento;
    private LocalDate fechaDeDonacion;
    private ColaboradorFisico donante;


    public Vianda(Heladera heladera, LocalDate fechaVencimiento, LocalDate fechaDeDonacion,ColaboradorFisico donante) {
        this.heladera = heladera;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaDeDonacion = fechaDeDonacion;
        this.donante = donante;
    }

    @Override
    public boolean puedeserDonada() {
        return true;
    }

    public String msgError(){
        return "La vianda no puede ser donada";
    }


}
