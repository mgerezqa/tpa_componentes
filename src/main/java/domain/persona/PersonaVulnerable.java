package domain.persona;

import domain.geografia.Ubicacion;
import domain.tarjeta.Tarjeta;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public PersonaVulnerable(String nombre, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.menoresACargo = new ArrayList<>();
    }

    public int cantidadDeMenoresACargo(){
        return this.getMenoresACargo().size();
    }
    public void agregarMenorACargo(Persona persona){
        menoresACargo.add(persona);
    }
}
