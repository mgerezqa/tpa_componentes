package domain.vianda;

import domain.heladera.Heladera;
import domain.usuarios.Colaborador;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Getter
@Setter
public class Vianda {
    @NonNull private String descripcion;
    @NonNull private LocalDate fechaDonacion;
    @NonNull private Heladera heladeraDondeReside;
    private Colaborador responsable;
    private Boolean entregado;
    private Integer calorias;
    private Integer peso;

    public Vianda(String descripcion, LocalDate fechaDonacion, Heladera heladeraDondeReside, Colaborador responsable, Boolean entregado, Integer calorias, Integer peso) {
        this.descripcion = descripcion;
        this.fechaDonacion = fechaDonacion;
        this.heladeraDondeReside = heladeraDondeReside;
        this.responsable = responsable;
        this.entregado = entregado;
        this.calorias = calorias;
        this.peso = peso;
    }
}
