package domain.heladera;
import domain.geografia.Ubicacion;
import domain.heladera.EstadosHeladera.EstadoHeladera;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import lombok.Getter;
import lombok.Setter;
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

    @Getter
    private LocalDateTime fechaInicioFuncionamiento;
    @Getter @Setter
    private EstadoHeladera estadoHeladera;

    @Getter @Setter
    private Float temperaturaMax;       // se puede setear la temperaturaMax
    @Getter @Setter
    private Float temperaturaMin;       // se puede setear la temperaturaMin

    @Getter @Setter
    private SensorMovimiento sensorMovimiento;
    @Getter @Setter
    private SensorTemperatura sensorTemperatura;

    // ============================================================ //
    // < CONSTRUCTOR > //
    public Heladera(Ubicacion ubicacion, String nombreIdentificador, Integer capacidadMax,
                    LocalDateTime fechaInicioFuncionamiento, Float temperaturaMax, Float temperaturaMin,
                    SensorMovimiento sensorMovimiento, SensorTemperatura sensorTemperatura){

        this.nombreIdentificador = nombreIdentificador;
        this.ubicacion = ubicacion;
        this.capacidadActual = 0;
        this.capacidadMax = capacidadMax;
        this.fechaInicioFuncionamiento = fechaInicioFuncionamiento;
        this.estadoHeladera = EstadoHeladera.ACTIVA;
        this.sensorTemperatura = sensorTemperatura;
        this.sensorMovimiento = sensorMovimiento;

        // ================================================ //
        this.temperaturaMin = temperaturaMin;
        this.temperaturaMax = temperaturaMax;
    }
    // ============================================================ //

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

    // Para cambiar de estados.
    public void cambiarEstadoAActiva(){
        if(!(this.estadoActualHeladera() == EstadoHeladera.FUERA_DE_SERVICIO)){
            this.estadoHeladera = EstadoHeladera.ACTIVA;
        }
    }

    public void cambiarEstadoAInactiva(){
        if(!(this.estadoActualHeladera() == EstadoHeladera.FUERA_DE_SERVICIO)){
            this.estadoHeladera = EstadoHeladera.INACTIVA;
        }
    }

    public void cambiarEstadoAFueraDeServicio(){
        this.estadoHeladera = EstadoHeladera.FUERA_DE_SERVICIO;
    }

    // Sensores.

    public Float temperaturaActual(){
        return this.sensorTemperatura.sensarTemperaturaActual();
    }

    // TODO
    public void heladeraSufreDesperfecto(){
            this.cambiarEstadoAInactiva();
    }

    // TODO
    public void alertarAlSistema(){
        return;
    }

}

