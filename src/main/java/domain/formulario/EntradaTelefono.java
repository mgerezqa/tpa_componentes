package domain.formulario;

public class EntradaTelefono extends Entrada{
//    private Integer codPais;
//    private Integer codArea;
//    private Integer numeroAbonado;
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
