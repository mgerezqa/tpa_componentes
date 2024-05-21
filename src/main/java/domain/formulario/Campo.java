package domain.formulario;

import lombok.Getter;

@Getter
public class Campo {
    private String descripcion;
    private TipoEntrada tipoEntrada;

    public Campo(String descripcion, TipoEntrada tipoEntrada) {
        this.descripcion = descripcion;
        this.tipoEntrada = tipoEntrada;
    }

}
