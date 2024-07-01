package models.entities.donaciones;


import models.entities.tarjeta.Tarjeta;
import models.entities.usuarios.ColaboradorFisico;
import lombok.Getter;

@Getter
public class RegistroDePersonaVulnerable {
    private ColaboradorFisico colaborador;
    private Tarjeta tarjeta;

    public RegistroDePersonaVulnerable(ColaboradorFisico colaborador, Tarjeta tarjeta) {
        this.colaborador = colaborador;
        this.tarjeta = tarjeta;
    }

}