package domain.usuarios;

import io.javalin.security.RouteRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @OneToMany
    @JoinColumn(name = "id_permiso")
    private List<Permiso> permisos;
    public Rol(RoleENUM nombre) {
        this.nombre = nombre;
        this.permisos = new ArrayList<>();
    }
}
