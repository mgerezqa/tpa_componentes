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
    private Heladera heladeraDondeReside;
    private Colaborador responsable;
    private Boolean entregado;
    private Integer calorias;
    private Integer gramos;

    public Vianda(String descripcion, LocalDate fechaDonacion, Heladera heladeraDondeReside, Colaborador responsable, Integer calorias, Integer peso) {
        this.descripcion = descripcion;
        this.fechaDonacion = fechaDonacion;
        this.heladeraDondeReside = heladeraDondeReside;
        this.responsable = responsable;
        this.entregado = false;
        this.calorias = calorias;
        this.gramos = peso;
    }

    public Boolean estaEntregada() { return this.entregado; }
    public void entregarVianda() { this.entregado = true; }

    /*
    el ingreso de vianda a heladera, como no me encargo de modelar la heladera, solo dejo asentado como deberia ir
    segun lo vi en el repositorio, haces la logica de si la heladera tiene espacio DENTRO de la vianda, cuando a
    mi parecer, la heladera deberia avisarme si tiene espacio o no, osea que la logica es por parte de la heladera

    aca dejo como seria mi planteo:

    public void ingresarAHeladera(Heladera unaHeladera){
        unaHeladera.ingresarVianda(this, 20);
        // ingresarVianda recibe una vianda y una cantidad, dentro de ese metodo se encarga de ver si tiene
        // espacio para guardarlo (el metodo podria ser de tipo boolean o void), si tiene espacio actualiza
        // sus atributos (incrementa elementos dentro de la heladera o reduce su capacidad, actualiza el peso
        // total del contenido de la heladera si la vianda tiene seteado un peso, etc);
        // si no tiene espacio, no hace nada/retorna falso/lanza escepcion, escojan lo que les paresca mejor
    }
     */
}
