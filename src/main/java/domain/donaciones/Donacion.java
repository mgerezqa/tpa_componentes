package domain.donaciones;

import domain.usuarios.Colaborador;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Donacion {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaboradorQueLaDono;

    @Column(name = "fecha_donacion", nullable = false,columnDefinition = "DATE")
    protected LocalDate fechaDeDonacion;

    public Donacion(LocalDate fechaDeDonacion, Colaborador colaboradorQueLaDono) {
        this.fechaDeDonacion = fechaDeDonacion;
        this.colaboradorQueLaDono = colaboradorQueLaDono;
    }

    public Donacion() {

    }
    public abstract String getTipo();
}
