package domain.persona;

import domain.formulario.documentos.Documento;
import domain.geografia.Ubicacion;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table (name = "personas_vulnerables")
public class PersonaVulnerable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "fecha_nacimiento",columnDefinition = "DATE")
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_registro", columnDefinition = "DATE")
    private LocalDate fechaRegitrado;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "domicilio_id")
    private List<Ubicacion> domicilios;

    @Column(name = "cantidadMenoresACargo")
    private Integer cantidadMenoresACargo;

    @Embedded
    private Documento documento;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona_vulnerable")
    private List<Persona> menoresACargo;

    public PersonaVulnerable(String nombre, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.menoresACargo = new ArrayList<>();
        this.fechaRegitrado = LocalDate.now();
        this.activo = true;
        this.cantidadMenoresACargo = 0;
    }
    public static PersonaVulnerable create(String nombre,String apellido, LocalDate fechaNacimiento){
        return PersonaVulnerable.builder()
                .nombre(nombre)
                .apellido(apellido)
                .fechaNacimiento(fechaNacimiento)
                .menoresACargo(new ArrayList<>())
                .fechaRegitrado(LocalDate.now())
                .activo(true)
                .cantidadMenoresACargo(0)
                .build();

    }

    public int cantidadDeMenoresACargo(){
        return this.getMenoresACargo().size();
    }

    public void agregarMenorACargo(Persona persona){
        menoresACargo.add(persona);
        this.cantidadMenoresACargo = cantidadMenoresACargo + 1;
    }
}
