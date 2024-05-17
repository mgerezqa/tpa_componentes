package domain.formulario;

import lombok.Getter;

@Getter
public class EntradaNumerica extends Entrada{
    private Integer entrada;

    @Override
    public void cargarEntrada(String entrada) {
        this.entrada = Integer.parseInt(entrada);
    }

    @Override
    public String mostrarEntrada() {
        return entrada.toString();
    }

    @Override
    public Integer mostrarNumero(){
        return this.entrada;
    }
}
