package domain.heladera.Heladera;
import domain.geografia.Ubicacion;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import domain.incidentes.IncidenteFactory;
import domain.incidentes.Incidente;
import domain.temperatura.Temperatura;
import domain.usuarios.Tecnico;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Setter @Getter // ojo con el setter, creo q no va
    private LocalDate fechaInicioFuncionamiento;
    @Setter @Getter
    private EstadoHeladera estadoHeladera;
    @Getter
    private ModeloDeHeladera modelo;
    @Setter @Getter
    private SensorMovimiento sensorMovimiento;
    @Setter @Getter
    private SensorTemperatura sensorTemperatura;
    @Setter @Getter
    private List<String> historialDeEstados;
    public Temperatura ultimaTemperaturaRegistrada;
    @Setter @Getter
    public List<Incidente> incidentes;

    @Getter @Setter
    private List<SolicitudApertura> solicitudesPendientes;

    // ============================================================ //
    // < CONSTRUCTOR > //
    // ============================================================ //

    public Heladera(ModeloDeHeladera modelo, String nombreIdentificador, Ubicacion ubicacion){

        this.ubicacion = ubicacion;
        this.capacidadActual = 0;
        this.modelo = modelo;
        this.nombreIdentificador = nombreIdentificador;
        this.darDeAltaHeladera();
        this.solicitudesPendientes = new ArrayList<>();

    }

    // ============================================================ //
    // MéTODOS //
    // ============================================================ //

    // Da de alta la heladera.
    public void darDeAltaHeladera(){
        this.historialDeEstados = new ArrayList<>();
        this.ultimaTemperaturaRegistrada = new Temperatura(modelo.getTemperaturaMinima(), LocalDateTime.now());
        this.incidentes = new ArrayList<>();
        this.cambiarEstadoAActiva();
    }

    // Booleano retorna si esta activa o no.
    public Boolean estaActivaHeladera(){
        return this.getEstadoHeladera() == EstadoHeladera.ACTIVA;
    }

    // Retorna el estado actual.
    public EstadoHeladera estadoActualHeladera(){
        return this.getEstadoHeladera();
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

    // Trackeo de estados.
    public void trackearEstado(EstadoHeladera estadoActual){
        historialDeEstados.add("\n" + estadoActual + " - " + LocalDateTime.now());
    }

    // Setear la temperatura actual.
    public void setUltimaTemperaturaRegistrada(Float temp){
        this.ultimaTemperaturaRegistrada.setTemperatura(temp);
        this.ultimaTemperaturaRegistrada.setFechaYhora(LocalDateTime.now());
    }

    // Get temperatura actual.
    public Float getUltimaTemperaturaRegistrada(){
        return this.ultimaTemperaturaRegistrada.getTemperatura();
    }

    // Ver si la temperatura esta en rango.
    public boolean temperaturaFueraDeRango(){
        return this.getUltimaTemperaturaRegistrada() < modelo.getTemperaturaMinima() || this.getUltimaTemperaturaRegistrada() > modelo.getTemperaturaMaxima();
    }

    // Agregar incidente a la lista de incidentes de la heladera.
    public void agregarIncidente(Incidente incidente){
        incidentes.add(incidente);
    }

    // ============================================================ //
    // Fallas, sensores y alertas //
    // ============================================================ //

    // Falla de temperatura
    public void fallaTemperatura() {
        IncidenteFactory.crearAlerta(this, "falla_temperatura");
    }
    // Falla de conexion: se encarga el "VerificadorTemperatura"
    // Falla de fraude  : se encarga el "SensorMovimiento"

    public void registrarSolicitud(SolicitudApertura solicitudApertura) {
        solicitudesPendientes.add(solicitudApertura);
    }

    public void registrarApertura(SolicitudApertura solicitudApertura) {
        solicitudApertura.completarSolicitud(LocalDateTime.now());
        solicitudesPendientes.remove(solicitudApertura);
    }
}

