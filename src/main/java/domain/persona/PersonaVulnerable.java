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
    @Column(name = "nombre_personaVulnerable")
    private String nombre;
    @Column(name = "fecha_nacimiento",columnDefinition = "DATE")
    private LocalDate fechaNacimiento;
    @Column(name = "fecha_registro", columnDefinition = "DATE")
    private LocalDate fechaRegitrado;
    @Embedded
    private Ubicacion domicilio;
    @Embedded
    private Documento documento;
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_vulnerable_id")
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
