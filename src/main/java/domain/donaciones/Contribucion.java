package domain.donaciones;

import domain.usuarios.Colaborador;
import lombok.Getter;

@Getter
public abstract class Contribucion {
    private Colaborador colaboradorResponsable;
    private TipoContribucion tipoContribucion;

    public Contribucion(TipoContribucion tipoContribucion) {
        this.tipoContribucion = tipoContribucion;
    }

    public void asignarColaborador(Colaborador unColaborador){
        if(this.colaboradorResponsable == null)
            setearColaborador(unColaborador);
        else
            throw new RuntimeException("Esta contribucion ya esta vinculada a un colaborador!");
    }
    private void setearColaborador(Colaborador unColaborador){
        if(!unColaborador.puedeContribuir(this))
            throw new RuntimeException("El Colaborador no esta autorizado para realizar esta donacion");
        else
            this.colaboradorResponsable = unColaborador;
    }

    // OJO, metodo solo para importaciones, evita controles y setea directamente
    public void importarColaborador(Colaborador unColaborador){
        this.colaboradorResponsable = unColaborador;
    }

    // metodo creado para testeo, posiblemente no se utilice
    public void reasignarDonacion(Colaborador unColaborador){
        setearColaborador(unColaborador);
    }
}
