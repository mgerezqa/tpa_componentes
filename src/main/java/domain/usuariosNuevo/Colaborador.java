package domain.usuariosNuevo;

import domain.contacto.MedioDeContacto;
import domain.contribucionNuevo.Contribucion;
import domain.contribucionNuevo.TipoContribucion;
import domain.formularioNuevo.FormularioNuevo;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public abstract class Colaborador {
    protected Integer id; //sin uso por el momento
    protected FormularioNuevo formulario;
    protected List<MedioDeContacto> mediosDeContacto;
    private Boolean activo;
    protected Integer puntosAcumulados;
    protected Set<TipoContribucion> contribucionesQueRealizara;
    //protected LogRecord logRecord;

    @Setter private Integer idColab;

    public Colaborador(){
        this.mediosDeContacto = new ArrayList<>();
        this.contribucionesQueRealizara = new LinkedHashSet<>();
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
    public void registrarContribuciones(TipoContribucion unTipoContribucion){
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

}
