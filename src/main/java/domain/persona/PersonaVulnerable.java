package domain.persona;

import domain.formulario.documentos.Documento;
import domain.geografia.Ubicacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Table (name = "persona_vulnerable")
public class PersonaVulnerable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(name = "nombre", columnDefinition = "VARCHAR(30)")
    private String nombre;
    @Column(name = "fecha_nacimiento",columnDefinition = "DATE")
    private LocalDate fechaNacimiento;
    @Column(name = "fecha_registro", columnDefinition = "DATE")
    private LocalDate fechaRegitrado;
    @Embedded
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
