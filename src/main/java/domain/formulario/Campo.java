package domain.formulario;

import lombok.Getter;

@Getter
public class Campo {
    private String descripcion;

    public Campo(String descripcion) {
        this.descripcion = descripcion;
    }

}
