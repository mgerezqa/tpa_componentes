package domain.formulario;

import lombok.Getter;

@Getter
public class Campo {
    private String descripcion;
    private String tipoCampo;

    public <E extends Enum<E>> Campo(E tipoCampo, String descripcion) {
        this.tipoCampo = tipoCampo.name();
        this.descripcion = descripcion;
    }

}
