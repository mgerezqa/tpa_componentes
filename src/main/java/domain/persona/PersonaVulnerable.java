package domain.persona;

import domain.geografia.Ubicacion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
public class PersonaVulnerable {
    private String nombre;
    private LocalDate fechaNacimiento;
    private LocalDate fechaRegitrado;
    private Ubicacion domicilio;
    private Documento documento;
    private List<Persona> menoresACargo;

    public PersonaVulnerable(String nombre, LocalDate fechaNacimiento, LocalDate fechaRegitrado) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegitrado = fechaRegitrado;
    }

    public Integer cantidadDeMenoresACargo(){
        return this.getMenoresACargo().size();
    }
    public void agregarMenorACargo(PersonaVulnerable persona){
        menoresACargo.add(persona);
    }
}
