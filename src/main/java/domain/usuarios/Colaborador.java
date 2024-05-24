package domain.usuarios;

import domain.donaciones.TipoDonacion;
import domain.formulario.Formulario;

public interface Colaborador {
    // chequea si el colaborador puede efectuar ese tipo de donacion
    public Boolean puedeDonar(TipoDonacion tipoDonacion);
    public void darAlta();
    public void darBaja();
    public Boolean estaActivo();

    // se plantea la opcion de modificar un colaborador en base a un formulario. la implementacion esta pendiente
    // public void modificarColaborador(Formulario unFormulario);

    // para las clases que
}
