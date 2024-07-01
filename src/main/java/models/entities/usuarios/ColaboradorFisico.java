package models.entities.usuarios;

import models.entities.contacto.MedioDeContacto;
import models.entities.formulario.Campo;
import models.entities.formulario.TipoCampo;
import lombok.Getter;
import lombok.Setter;
import models.entities.geografia.AreaDeCobertura;
import models.entities.geografia.Ciudad;
import models.entities.geografia.Ubicacion;
import models.entities.heladera.Heladera.Heladera;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter
public class ColaboradorFisico extends Colaborador {

    private String nombre;
    private String apellido;
    private Set <AreaDeCobertura> zonaQueFrecuenta;


    public ColaboradorFisico(String nombre, String apellido, MedioDeContacto medioDeContacto){
        this.nombre = nombre;
        this.apellido = apellido;
        this.unMedioDeContacto = medioDeContacto;
        this.activo = true;
        this.mediosDeContacto = new HashSet<>();
        this.zonaQueFrecuenta = new HashSet<>();
        this.agregarMedioDeContacto(medioDeContacto);
        this.completarFormulario();
    }

    public List <Ciudad> listaDeCiudadesPorZonaDeCobertura(AreaDeCobertura area){
        return area.getCiudades();

    }

    public void agregarZonaDeCobertura(AreaDeCobertura area){
        zonaQueFrecuenta.add(area);
    }

    public void quitarZonaDeCobertura(AreaDeCobertura area){
        zonaQueFrecuenta.remove(area);
    }

    public void agregarCiudadEnZonaDeCobertura(AreaDeCobertura area, Ciudad ciudad){
        if(zonaQueFrecuenta.contains(area)){
            area.agregarUbicacion(ciudad);
        }
    }

    public void suscribirseAHeladera(Heladera heladera){
        //verificar que la heladera se encuentre en el area de cobertura , sino lanzar excepción


    }

    @Override
    public void completarFormulario() {
        super.completarFormulario();

        formulario.agregarCampo("Nombre",new Campo(TipoCampo.CAMPO_NOMBRE));
        formulario.agregarCampo("Apellido",new Campo(TipoCampo.CAMPO_NOMBRE));
        formulario.agregarCampo("Medio de contacto",new Campo(TipoCampo.CAMPO_MULTIPLE));

        formulario.cargarValor("Nombre", this.getNombre());
        formulario.cargarValor("Apellido",this.getApellido());

        for (MedioDeContacto contacto : this.mediosDeContacto) {
            formulario.cargarValor("Medio de contacto", contacto.obtenerDescripcion());
        }
    }


}
