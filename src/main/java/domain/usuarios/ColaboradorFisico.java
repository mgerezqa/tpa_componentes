package domain.usuarios;

import domain.donaciones.Vianda;
import domain.geografia.area.AreaDeCobertura;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "colaboradoresFisicos")
public class ColaboradorFisico extends Colaborador {

    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @OneToMany
    @JoinColumn(name = "vianda_id", referencedColumnName = "id")
    private List<Vianda> viandasDonadas;

    @Transient
    private boolean notificacionRecibida;

    @Embedded
    private AreaDeCobertura zona;

    public ColaboradorFisico(String nombre, String apellido){
        this.notificacionRecibida = false;
        this.nombre = nombre;
        this.apellido = apellido;
        this.activo = true;
        this.mediosDeContacto = new HashSet<>();
    }



}
