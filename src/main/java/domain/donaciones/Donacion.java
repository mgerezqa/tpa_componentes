package domain.donaciones;

import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorJuridico;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Donacion {

    protected Colaborador colaboradorQueRealizo;
    protected TipoDonacion tipoDonacion;

    public Donacion(TipoDonacion unTipo){
        this.tipoDonacion = unTipo;
    }

    public void registrarDonacion(Colaborador unColaborador){
        if(!unColaborador.puedeDonar(this.getTipoDonacion()))
            throw new RuntimeException("El Colaborador no esta autorizado para realizar esta donacion");
        else
            setColaboradorQueRealizo(unColaborador);
    }
}
