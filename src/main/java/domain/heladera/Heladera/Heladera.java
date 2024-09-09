package domain.heladera.Heladera;
import domain.geografia.Ubicacion;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import domain.incidentes.IncidenteFactory;
import domain.incidentes.Incidente;
import domain.suscripciones.EventManager;
import domain.temperatura.Temperatura;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "heladeras")
public class Heladera {

    @Id
    @GeneratedValue
    private String id;

    @Transient
    private EventManager eventManager;

    @OneToOne
    @JoinColumn(name = "ubicacion_id", referencedColumnName = "id", nullable = false)
    private Ubicacion ubicacion;

    @Column(name = "nombre", nullable = false)
    private String nombreIdentificador;

    @Column(name = "capacidadMax")
    private Integer capacidadMax; // (se mide en numero de viandas)

    @Column(name = "capacidadActual")
    private Integer capacidadActual;

    @Column(name = "fechaInicioFunc", columnDefinition = "DATE")
    private LocalDate fechaInicioFuncionamiento;

    @Enumerated(EnumType.STRING)
    private EstadoHeladera estadoHeladera;

    @ManyToOne
    @JoinColumn(name = "modeloDeHeladera_id", referencedColumnName = "id", nullable = false)
    private ModeloDeHeladera modelo;

    @Transient
    private SensorMovimiento sensorMovimiento;

    @Transient
    private SensorTemperatura sensorTemperatura;

    @Transient
    private List<String> historialDeEstados;

    @Embedded
    public Temperatura ultimaTemperaturaRegistrada;

    @OneToMany
    @JoinColumn(name = "incidente_id", referencedColumnName = "id")
    public List<Incidente> incidentes;

    @Transient
    private List<SolicitudApertura> solicitudesPendientes;

    // ============================================================ //
    // < CONSTRUCTOR > //
    public Heladera(ModeloDeHeladera modelo, String nombreIdentificador, Ubicacion ubicacion){
        this.ubicacion = ubicacion;
        this.capacidadActual = 0;
        this.modelo = modelo;
        this.nombreIdentificador = nombreIdentificador;
        this.darDeAltaHeladera();
        this.solicitudesPendientes = new ArrayList<>();

        this.eventManager = new EventManager();
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
        eventManager.notifyObservers();
    }

    // Trackeo de estados.
    public void trackearEstado(EstadoHeladera estadoActual){
        historialDeEstados.add("\n" + estadoActual + " - " + LocalDateTime.now());
    }

    // Setear la temperatura actual.
    public void setUltimaTemperaturaRegistrada(Float temp, LocalDateTime fecha){
        this.ultimaTemperaturaRegistrada.setTemperatura(temp);
        this.ultimaTemperaturaRegistrada.setFechaYhora(fecha);
    }

    // Get temperatura actual.
    public Float getUltimaTemperaturaRegistrada(){
        return this.ultimaTemperaturaRegistrada.getTemperatura();
    }

    // Ver si la temperatura esta en rango.
    public boolean temperaturaFueraDeRango(){
        return this.getUltimaTemperaturaRegistrada() < modelo.getTemperaturaMinima() ||
                this.getUltimaTemperaturaRegistrada() > modelo.getTemperaturaMaxima();
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

    // ============================================================ //
    // Gestion de viandas (SOLO PARA TEST)
    // LLAMAR AL EVENTMANAGER CUANDO INGRESEN O RETIREN VIANDAS
    // ============================================================ //


    public  void ingresarVianda(){
        if(this.capacidadActual < this.capacidadMax){
            this.capacidadActual += 1;
            eventManager.notifyObservers();
        }
    }

    public void retirarVianda(){
        if(this.capacidadActual > 0){
            this.capacidadActual -= 1;
            eventManager.notifyObservers();

        }
    }


}

