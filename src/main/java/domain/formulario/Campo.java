package domain.formulario;

import lombok.Getter;

@Getter
public class Campo implements EntradaFactory {
    private eTipoCampo tipoCampo;
    private Entrada entrada;

    public Campo(eTipoCampo tipo){
        this.tipoCampo = tipo;
        this.entrada = EntradaFactory.crear(tipo);
    }

    public String getValor(){
        return entrada.obtenerRespuesta();
    }

    public void setValor(String valor){
        this.entrada.ingresarRespuesta(valor);
    }


}
