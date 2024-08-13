package domain.usuariosNuevo;

import domain.contacto.MedioDeContacto;
import domain.contribucionNuevo.Contribucion;
import domain.contribucionNuevo.TipoContribucion;
import domain.formularioNuevo.FormularioNuevo;
import domain.heladera.Heladera.Heladera;
import domain.tarjeta.EventosTarjetasColab;
import domain.tarjeta.TarjetaColaborador;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter @Setter
public abstract class Colaborador {
    protected Integer id; //sin uso por el momento
    protected FormularioNuevo formulario;
    protected List<MedioDeContacto> mediosDeContacto;
    private Boolean activo;
    protected Integer puntosAcumulados;
    protected List<TipoContribucion> contribucionesQueRealizara;
    protected TarjetaColaborador tarjetaColaborador;
    //protected LogRecord logRecord;

    @Setter private Integer idColab;

    public Colaborador(){
        this.mediosDeContacto = new ArrayList<>();
        this.contribucionesQueRealizara = new ArrayList<>();
        this.puntosAcumulados = 0;
        this.idColab = generarId();
        //this.logRecord = new LogRecord(new FileLogging("Colaborador_"+idColab));
    }

    /*Punto de arranque*/

    private Integer generarId(){ return new Random().nextInt(1000);    }

    public void darDeAlta(){ this.activo = true; }
    public void darDeBaja(){ this.activo = false; }
    public Boolean estaActivo(){ return this.activo; }
    public Boolean formularioPresente(){ return this.formulario != null; }

    public abstract List<TipoContribucion> contribucionesDisponibles();

    public void sumarPuntos(int puntos) {
        this.puntosAcumulados += puntos;
    }
    public void restarPuntos(int puntos) {
        this.puntosAcumulados -= puntos;
    }

    public void agregarMedioDeContacto(MedioDeContacto medio) {
        this.mediosDeContacto.add(medio);
    }

    public void removerContacto(MedioDeContacto contacto){
        mediosDeContacto.remove(contacto);
    }
    public void agregarFormaContribucionQueRealizara(TipoContribucion unTipoContribucion){
        if (contribucionesDisponibles().contains(unTipoContribucion))
            contribucionesQueRealizara.add(unTipoContribucion);
        else
            throw new RuntimeException("El Colaborador no puede realizar ese tipo de contribucion");
    }

    public Boolean puedeContribuir(Contribucion unaContribucion){
        return puedeContribuir(unaContribucion.getTipoContribucion());
    }
    public Boolean puedeContribuir(TipoContribucion unTipo){
        return this.contribucionesQueRealizara.contains(unTipo);
    }

    public void generarTarjetaColaborador(){
        this.tarjetaColaborador = TarjetaColaborador.generar(this);
        //logRecord.log(Event.of("Se genero una tarjeta: "+tarjetaContribucion.getCodigoTarjeta()));
        tarjetaColaborador.registrarCreacionTarjeta();
    }
    public void enviarTarjeta(){
        // TODO
        //logRecord.log(Event.of("Se envio la tarjeta "));
        tarjetaColaborador.registrarEnvioTarjeta();
    }
    public void abrirHeladera(Heladera unaHeladera){
        if(this.tarjetaColaborador != null){
            //TODO: hace algo
            tarjetaColaborador.usarTarjeta(EventosTarjetasColab.USO_HELADERA, unaHeladera);
        } else {
            throw new RuntimeException("No tiene tarjeta generada");
        }
    }
}
