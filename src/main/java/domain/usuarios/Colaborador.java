package domain.usuarios;

import domain.contacto.MedioDeContacto;
import domain.donaciones.Contribucion;
import domain.donaciones.TipoContribucion;
import domain.formulario.Formulario;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Colaborador {
    protected Formulario formulario;
    protected List<MedioDeContacto> mediosDeContacto;
    private Boolean activo;
    protected Integer puntosAcumulados;
    protected List<TipoContribucion> contribucionesQueRealizara;

    public Colaborador(){
        this.mediosDeContacto = new ArrayList<>();
        this.contribucionesQueRealizara = new ArrayList<>();
        this.puntosAcumulados = 0;
    }

    /*Punto de arranque*/

    public void darDeAlta(){ this.activo = true; }
    public void darDeBaja(){ this.activo = false; }
    public Boolean estaActivo(){ return this.activo; }
    public Boolean formularioPresente(){ return this.formulario != null; }

    public abstract List<TipoContribucion> contribucionesDisponibles();


    public void agregarContacto(MedioDeContacto unContacto){
        this.mediosDeContacto.add(unContacto);
    }
    public void removerContacto(String descripcionContacto){
        mediosDeContacto.removeIf(contacto -> contacto.obtenerDescripcion().equals(descripcionContacto));
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

    public void sumarPuntos(int puntos) {
        this.puntosAcumulados += puntos;
    }
    public void restarPuntos(int puntos) {
        this.puntosAcumulados -= puntos;
    }
}
