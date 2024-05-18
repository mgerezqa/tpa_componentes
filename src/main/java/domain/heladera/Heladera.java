package domain.heladera;
import domain.geografia.Ubicacion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Heladera {

    @Setter
    private Ubicacion ubicacion;
    @Setter
    private String nombreIdentificador;
    @Getter
    private Integer capacidadMax; // (se mide en numero de viandas)
    @Setter @Getter
    private Integer capacidadActual;

    private LocalDate fechaInicioFuncionamiento;
    private EstadoHeladera estadoHeladera;

    private SensorTemperatura sensorTemperatura;
    private SensorMovimiento sensorMovimiento;

    private Float temperaturaMax;
    private Float temperaturaMin;

    // ============================================================ //

    // < CONSTRUCTOR > //
    public Heladera(Ubicacion ubicacion, String nombreIdentificador, Integer capacidadMax, LocalDate fechaInicioFuncionamiento, Float tMax, Float tMin){
        this.ubicacion = ubicacion;
        this.nombreIdentificador = nombreIdentificador;
        this.capacidadActual = 0;
        this.capacidadMax = capacidadMax;
        this.fechaInicioFuncionamiento = fechaInicioFuncionamiento;
        this.estadoHeladera = new HeladeraActiva();

        // ================================================ //
        this.temperaturaMax = tMax;
        this.temperaturaMin = tMin;
    }

    // ============================================================ //

    // < ALTA > //
    // Para dar de alta una heladera, solamente se crea una instancia de la misma, la cual tendrá
    // su estadoHeladera como un new HeladeraActiva();
    // new Heladera(geografia, nombreId, capacidad, fechaInicio, new HeladeraActiva(), tMax, tMin);

    // < BAJA > //
    // Para dar de baja una heladera, se cambia el estado a "HeladeraFueraDeServicio"

    // < MODIFICACIÓN > //
    // Al tener los atributos, que pueden ser modificados, con "setters",
    // estos pueden ser MODIFICADOS.

    public void cambiarEstadoA(EstadoHeladera estado){
        this.estadoHeladera = estado;
    }

    // ============================================================ //

    public boolean heladeraEstaActiva(){
        return estadoHeladera instanceof HeladeraActiva;
    }

    // ============================================================ //

    public void setearSensorTemperatura(){
        this.sensorTemperatura.setTemperaturaMax(temperaturaMax);
        this.sensorTemperatura.setTemperaturaMin(temperaturaMin);
    }

    // TODO
    public void enviarAlerta(){
        return;
    }

    // ============================================================ //


    // Por ahora solo se necesita conocer el estado de la heladera: activa o inactiva.
    // La idea que pense es que al modelarlo con un patron state, en el futuro se pueden establecer comportamientos.
    // La idea del estado "Inactiva", es para cuando sufran desperfectos por ejemplo. Ya que al estar "fueraDeServicio"
    // estas no podran volver a ser utilizadas.

    // TEST FUNCIONAMIENTO RAMA //


}

