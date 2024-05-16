package domain.formulario;

import java.time.LocalDate;
import java.util.List;

public abstract class Entrada {
    public void cargarEntrada(String entrada){}
    public String mostrarEntrada(){ return null; }
    public Integer mostrarNumero(){ return null; }
    public LocalDate mostrarFecha() { return null; }
    public List<String> mostrarEntradas(){ return null; }
    public String mostrarEntrada(Integer index){ return null; }
}
