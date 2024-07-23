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
    public void ingresarRespuesta(String entrada) {
        this.entradas.add(entrada);
    }

    @Override
    public String obtenerRespuesta() {
        return entradas.toString();
    }


    @Override
    public String obtenerRespuesta(Integer index){
        return entradas.get(index);
    }

    public List<String> mostrarEntradas(){
        return entradas;
    }

}
