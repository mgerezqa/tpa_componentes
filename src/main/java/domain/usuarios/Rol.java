package domain.usuarios;

import io.javalin.security.RouteRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "rol")
public class Rol{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleENUM nombre;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "rol_permiso",
            joinColumns = @JoinColumn(name = "rol_id"),
            inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    private List<Permiso> permisos;
    public Rol(RoleENUM nombre) {
        this.nombre = nombre;
        this.permisos = new ArrayList<>();
    }

    public boolean tienePermiso(Permiso permiso) {
        return this.permisos.contains(permiso);
    }

    public void agregarPermiso(Permiso permiso) {
        if (!this.permisos.contains(permiso)) {
            this.permisos.add(permiso);
        }
    }

    public void quitarPermiso(Permiso permiso) {
        this.permisos.remove(permiso);
    }

}
