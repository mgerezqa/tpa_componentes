package domain.formulario;

import domain.usuarios.Rubro;
import domain.usuarios.TipoRazonSocial;

import java.time.LocalDate;
import java.util.List;

public abstract class Entrada {


    public void cargarEntrada(String entrada){}
    public  void cargarEntrada(Rubro entrada){}
    public void cargarEntrada(Cuit entrada){}
    public void cargarEntrada(TipoRazonSocial entrada){}
    public String mostrarEntrada(){ return null; }
    public Integer mostrarNumero(){ return null; }
    public LocalDate mostrarFecha() { return null; }
    public List<String> mostrarEntradas(){ return null; }
    public String mostrarEntrada(Integer index){ return null; }

}
