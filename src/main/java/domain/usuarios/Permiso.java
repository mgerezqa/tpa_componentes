package domain.usuarios;

import lombok.Getter;

import javax.naming.Name;
import javax.persistence.*;

@Getter
@Entity
@Table(name = "permiso")
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "descripción")
    private String descripcion;
}
