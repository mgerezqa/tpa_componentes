package domain.donaciones;

import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorJuridico;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
public abstract class Donacion {

    protected Colaborador colaboradorQueRealizo;
    protected TipoDonacion tipoDonacion;

    public Donacion(TipoDonacion unTipo){
        this.tipoDonacion = unTipo;
        this.colaboradorQueRealizo = null;
    }

    public void registrarDonacion(Colaborador unColaborador){
        if(this.colaboradorQueRealizo == null)
            asignarDonacion(unColaborador);
        else
            throw new RuntimeException("Esta donacion ya esta vinculada a un colaborador!");
    }
    private void asignarDonacion(Colaborador unColaborador){
        if(!unColaborador.puedeDonar(getTipoDonacion()))
            throw new RuntimeException("El Colaborador no esta autorizado para realizar esta donacion");
        else
            this.colaboradorQueRealizo = unColaborador;
    }

    // metodo creado para testeo, posiblemente no se utilice
    public void reasignarDonacion(Colaborador unColaborador){
        asignarDonacion(unColaborador);
    }

    // dejo este metodo aca por si es necesario hacer alguna accion como aplicar la donacion
    // solo se debe ejecutar cuando la donacion esta completamente cargada
    // public abstract void aplicarDonacion();
}
