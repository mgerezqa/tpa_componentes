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
public class Rol implements RouteRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @OneToMany
    @JoinColumn(name = "id_permiso")
    private List<Permiso> permisos;
    public Rol(String nombre) {
        this.nombre = nombre;
        this.permisos = new ArrayList<>();
    }
}
