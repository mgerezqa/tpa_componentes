package domain.heladera.Heladera;
import domain.geografia.Ubicacion;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class Heladera {

    @Setter @Getter
    private Ubicacion ubicacion;
    @Setter @Getter
    private String nombreIdentificador;
    @Setter @Getter
    private Integer capacidadMax; // (se mide en numero de viandas)
    @Setter @Getter
    private Integer capacidadActual;

    @Getter @Setter
    private LocalDate fechaInicioFuncionamiento;
    @Getter @Setter
    private EstadoHeladera estadoHeladera;

    @Getter
    private ModeloDeHeladera modelo;

    @Getter @Setter
    private SensorMovimiento sensorMovimiento;
    @Getter @Setter
    private SensorTemperatura sensorTemperatura;

    @Getter @Setter
    private List<String> historialDeEstados;

    // ============================================================ //
    // < CONSTRUCTOR > //
    public Heladera( ModeloDeHeladera modelo, String nombreIdentificador, Ubicacion ubicacion,
                     SensorMovimiento sensorMovimiento, SensorTemperatura sensorTemperatura){

        this.darDeAltaHeladera();
        this.nombreIdentificador = nombreIdentificador;
        this.ubicacion = ubicacion;
        this.capacidadActual = 0;
        this.sensorTemperatura = sensorTemperatura;
        this.sensorMovimiento = sensorMovimiento;
        this.modelo = modelo;

    }
    // ============================================================ //

    public void darDeAltaHeladera(){
        this.historialDeEstados = new ArrayList<>();
        this.cambiarEstadoAActiva();
    }

    // < ALTA > //
    // Para dar de alta una heladera, solamente se crea una instancia de la misma, la cual tendrá
    // su estadoHeladera como un new HeladeraActiva();
    // new Heladera(ubicacion, nombreId, capacidad, fechaInicio, new HeladeraActiva(), tMax, tMin);

    // < BAJA > //
    // Para dar de baja una heladera, se cambia el estado a "HeladeraFueraDeServicio"

    // < MODIFICACIÓN > //
    // Al tener los atributos, que pueden ser modificados, con "setters",
    // estos pueden ser MODIFICADOS.

    // ============================================================ //

    // Por ahora solo se necesita conocer el estado de la heladera: activa o inactiva.
    // La idea que pense es que al modelarlo con un patron state, en el futuro se pueden establecer comportamientos.
    // La idea del estado "Inactiva", es para cuando sufran desperfectos por ejemplo. Ya que al estar "fueraDeServicio"
    // estas no podran volver a ser utilizadas.
    // ============================================================ //

    // MéTODOS //

    // Retorna el estado actual.
    public EstadoHeladera estadoActualHeladera(){
        return this.getEstadoHeladera();
    }

    // Booleano retorna si esta activa o no.
    public Boolean estaActivaHeladera(){
        return this.getEstadoHeladera() == EstadoHeladera.ACTIVA;
    }

    public int mesesActiva(){
        int months = (int) ChronoUnit.MONTHS.between(fechaInicioFuncionamiento, LocalDate.now());
        return months;
    }

    // Para cambiar de estados.
    public void cambiarEstadoAActiva(){
        if(!(this.estadoActualHeladera() == EstadoHeladera.FUERA_DE_SERVICIO)){
            this.estadoHeladera = EstadoHeladera.ACTIVA;
            trackearEstado(this.getEstadoHeladera());
        }
    }

    public void cambiarEstadoAInactiva(){
        if(!(this.estadoActualHeladera() == EstadoHeladera.FUERA_DE_SERVICIO)){
            this.estadoHeladera = EstadoHeladera.INACTIVA;
            trackearEstado(this.getEstadoHeladera());
        }
    }

    public void cambiarEstadoAFueraDeServicio(){
        this.estadoHeladera = EstadoHeladera.FUERA_DE_SERVICIO;
        trackearEstado(this.getEstadoHeladera());
    }

    public void trackearEstado(EstadoHeladera estadoActual){
        historialDeEstados.add("\n" + estadoActual + " - " + LocalDateTime.now());
    }

    // Fallas ----------------------------------------------------------------------
    // Sensores.

    // Temperatura
    @Setter @Getter
    public Float temperaturaActual;

    // Falla de temperatura
    public void problemaDeTemperatura(){
        if((temperaturaActual > sensorTemperatura.getTemperaturaMax()) ||
                (temperaturaActual < sensorTemperatura.getTemperaturaMin())){
            this.cambiarEstadoAInactiva();
        }
    }

    // Falla de conexion
    

    // Movimiento
    public void alertaDetectada() {
        this.cambiarEstadoAInactiva();
    }

    // ---------------------------------------------------------------------------

}

