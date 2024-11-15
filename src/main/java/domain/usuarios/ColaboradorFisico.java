package domain.usuarios;

import domain.donaciones.Vianda;
import domain.geografia.area.AreaDeCobertura;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "colaboradores_fisicos")
public class ColaboradorFisico extends Colaborador {

    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "nacimiento",columnDefinition = "DATE")
    private LocalDate nacimiento;

    @OneToMany(mappedBy = "colaboradorQueLaDono", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Vianda> viandasDonadas;

    @Transient
    private boolean notificacionRecibida;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "area_id")
    private AreaDeCobertura zona;
    // ============================================================ //
    // Constructor //
    // ============================================================ //
    public ColaboradorFisico(String nombre, String apellido){
        this.notificacionRecibida = false;
        this.nombre = nombre;
        this.apellido = apellido;
        this.activo = true;
        this.mediosDeContacto = new HashSet<>();
        this.viandasDonadas = new ArrayList<>();
    }

    public void agregarVianda(Vianda vianda){
        this.getViandasDonadas().add(vianda);
    }



}
