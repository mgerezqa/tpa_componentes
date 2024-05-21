package domain.heladera;
import domain.geografia.Ubicacion;
import domain.heladera.EstadosHeladera.EstadoHeladera;
import domain.heladera.EstadosHeladera.HeladeraActiva;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class Heladera {

    @Setter @Getter
    private String nombreIdentificador;
    @Setter @Getter
    private Ubicacion ubicacion;
    @Getter
    private Integer capacidadMax; // (se mide en numero de viandas)
    @Setter @Getter
    private Integer capacidadActual;

    @Getter
    private LocalDate fechaInicioFuncionamiento;
    @Getter
    private EstadoHeladera estadoHeladera;

    private SensorTemperatura sensorTemperatura;
    private SensorMovimiento sensorMovimiento;

    @Setter @Getter
    private Float temperaturaMin;
    @Setter @Getter
    private Float temperaturaMax;

    // ============================================================ //
    // < CONSTRUCTOR > //
    public Heladera(String nombreIdentificador, Ubicacion ubicacion,
                    Integer capacidadMax, LocalDate fechaInicioFuncionamiento,
                    Float tMax, Float tMin){

        this.nombreIdentificador = nombreIdentificador;
        this.ubicacion = ubicacion;
        this.capacidadActual = 0;
        this.capacidadMax = capacidadMax;
        this.fechaInicioFuncionamiento = fechaInicioFuncionamiento;
        this.estadoHeladera = new HeladeraActiva();

        // ================================================ //
        this.temperaturaMin = tMin;
        this.temperaturaMax = tMax;
    }

    // ============================================================ //

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

    // ============================================================ //

    // ============================================================ //

    public boolean heladeraEstaActiva(){
        return estadoHeladera instanceof HeladeraActiva;
    }

    public void cambiarEstadoA(EstadoHeladera estado){
        this.estadoHeladera = estado;
    }

    // ============================================================ //

    public void setearTemperaturas(){
        this.sensorTemperatura.setTemperaturaMax(temperaturaMax);
        this.sensorTemperatura.setTemperaturaMin(temperaturaMin);
    }

    // TODO
    public float temperaturaActual(){
        return 0; // "enviar temperatura al sistema"
    }

    // TODO
    public void sensorDetectaMovimiento(){
        return; // "alertar al sistema"
    }

    public void enviarAlerta() {
    }

    // ============================================================ //

    // Por ahora solo se necesita conocer el estado de la heladera: activa o inactiva.
    // La idea que pense es que al modelarlo con un patron state, en el futuro se pueden establecer comportamientos.
    // La idea del estado "Inactiva", es para cuando sufran desperfectos por ejemplo. Ya que al estar "fueraDeServicio"
    // estas no podran volver a ser utilizadas.

    // Sensores: (incompleto por ahora)
    // Modelamos los sensores? O solo guardamos registros?

}

