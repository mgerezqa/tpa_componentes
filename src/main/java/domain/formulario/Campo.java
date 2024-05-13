package domain.formulario;

public class Campo {
    private String descripcion;
    private TipoEntrada tipoEntrada;

    public Campo(String descripcion, TipoEntrada tipoEntrada) {
        this.descripcion = descripcion;
        this.tipoEntrada = tipoEntrada;
    }

    public String getdescripcion() {
        return descripcion;
    }

    public TipoEntrada getTipoEntrada() {

        return tipoEntrada;
    }
}
