package domain.Colaboraciones;


import domain.tarjeta.Tarjeta;
import domain.usuarios.ColaboradorFisico;

public class RegistroDePersonaVulnerable {
    private ColaboradorFisico colaborador;
    private Tarjeta tarjeta;

    public RegistroDePersonaVulnerable(ColaboradorFisico colaborador, Tarjeta tarjeta) {
        this.colaborador = colaborador;
        this.tarjeta = tarjeta;
    }

}