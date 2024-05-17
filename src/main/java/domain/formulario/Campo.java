package domain.formulario;

import lombok.Getter;

@Getter
public class Campo implements GenerarEntrada{
    private TipoCampo tipoCampo;
    private Entrada entrada;

    public Campo(TipoCampo tipo){
        this.tipoCampo = tipo;
        this.entrada = GenerarEntrada.generar(tipo);
    }

    public String getValor(){
        return entrada.mostrarEntrada();
    }

    public void setValor(String valor){
        this.entrada.cargarEntrada(valor);
    }
}
