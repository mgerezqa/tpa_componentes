package domain.persona;

import domain.formulario.documentos.Documento;
import domain.geografia.Ubicacion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Table (name = "persona_vulnerable")
@NoArgsConstructor
public class PersonaVulnerable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "nombre")
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
    @JoinColumn(name = "id_persona_vulnerable")
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
