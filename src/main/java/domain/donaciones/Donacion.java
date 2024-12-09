package domain.donaciones;

import domain.usuarios.Colaborador;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class Donacion {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaboradorQueLaDono;

    @Column(name = "fecha_donacion", nullable = false,columnDefinition = "DATE")
    protected LocalDate fechaDeDonacion;

    @Column(name = "puntosOtorgados")
    protected Integer puntosOtorgados;

    @Column(name = "completado")
    private Boolean completado;

    public Donacion(LocalDate fechaDeDonacion, Colaborador colaboradorQueLaDono) {
        this.fechaDeDonacion = fechaDeDonacion;
        this.colaboradorQueLaDono = colaboradorQueLaDono;
        this.completado = false;
    }
    public Donacion(Colaborador colaboradorQueLaDono) {
        this.colaboradorQueLaDono = colaboradorQueLaDono;
        this.fechaDeDonacion = LocalDate.now();
        this.completado= false;
    }

    public Donacion() {

    }
    public void completar(){
        this.completado = true;
    }
    public abstract String getTipo();

}
