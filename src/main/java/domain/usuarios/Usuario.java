package domain.usuarios;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombreUsuario")
    private String nombreUsuario;

    @Column(name = "contrasenia")
    private String contrasenia;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "usuario_rol", //Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles = new ArrayList<>();

    public Usuario(String nombreUsuario, String contrasenia) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.roles = new ArrayList<>();
    }
    public static Usuario of(String nombreUsuario, String contrasenia){
        return Usuario.builder()
                .roles(new ArrayList<>())
                .nombreUsuario(nombreUsuario)
                .contrasenia(contrasenia)
                .build();
    }
    public void agregarRol(Rol rol) {
        this.roles.add(rol);
    }

    public boolean tieneRol(RoleENUM rol) {
        return this.roles.stream()
            .map(Rol::getNombre)
            .anyMatch(nombre -> nombre.equals(rol));
    }

    public boolean tieneAlgunRol(Set<RoleENUM> rolesRequeridos) {
        return this.roles.stream()
            .map(Rol::getNombre)
            .anyMatch(rolesRequeridos::contains);
    }

    public void quitarRol(Rol rol) {
        this.roles.remove(rol);
    }

}
