package domain.donaciones;

import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;

import java.time.LocalDate;

public class Dinero  {
    private Integer cantidad;
    private FrecuenciaDeDonacion frecuencia;
    private LocalDate fechaDeDonacion;
    private Colaborador colaboradorQueLaDono;

    public Dinero(Integer cantidad, FrecuenciaDeDonacion frecuencia, LocalDate fechaDeDonacion, Colaborador colaboradorQueLaDono) {
        this.cantidad = cantidad;
        this.frecuencia = frecuencia;
        this.fechaDeDonacion = fechaDeDonacion;
        this.colaboradorQueLaDono = colaboradorQueLaDono;

    }
//
//    @Override
//    public boolean puedeserDonada() {
//        return true;
//    }
//
//    @Override
//    public String msgError() {
//        return "El dinero no puede ser donado";
//    }



}
