package domain.persona;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Persona {
    private String nombre;
    private Integer edad;
    public Persona(String nombre, Integer edad){
        this.nombre = nombre;
        this.edad = edad;
    }
}
