package domain.formulario;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EntradaMultiple extends Entrada{
    private List<String> entradas;

    public EntradaMultiple() {
        this.entradas = new ArrayList<>();
    }

    @Override
    public void cargarEntrada(String entrada) {
        this.entradas.add(entrada);
    }

    @Override
    public String mostrarEntrada() {
        return entradas.toString();
    }
    @Override
    public List<String> mostrarEntradas(){
        return entradas;
    }
    @Override
    public String mostrarEntrada(Integer index){
        return entradas.get(index);
    }

}
