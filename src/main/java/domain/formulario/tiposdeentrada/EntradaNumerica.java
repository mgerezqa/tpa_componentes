package domain.formulario.tiposdeentrada;

import lombok.Getter;

@Getter
public class EntradaNumerica extends Entrada{
    private Integer entrada;

    @Override
    public void ingresarRespuesta(String entrada) {
        this.entrada = Integer.parseInt(entrada);
    }

    @Override
    public String obtenerRespuesta() {
        return entrada.toString();
    }


}
