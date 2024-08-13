package domain.usuariosNuevo;


import domain.contacto.MedioDeContacto;
import domain.contribucionNuevo.TipoContribucion;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Getter @Setter
public class ColaboradorFisico extends Colaborador {

    private String nombre;
    private String apellido;
    private TipoDocumento tipoDocumento;
    private Integer numeroDocumento;
    private LocalDate fechaNac;
    private String direccion;

    public ColaboradorFisico(){

    }


    public List<String> mostrarInformacionDeContacto(){
        return this.mediosDeContacto.stream().map(MedioDeContacto::informacionDeMedioDeContacto).collect(Collectors.toList());
    }


    @Override
    public List<TipoContribucion> contribucionesDisponibles() {
        return Arrays.asList(TipoContribucion.DINERO, TipoContribucion.VIANDA, TipoContribucion.DISTRIBUCION, TipoContribucion.REGISTRO_P_V);
    }

    public boolean identificarPorDocumento(TipoDocumento tipoDoc, Integer nroDoc) {
        return getNumeroDocumento().equals(nroDoc) && getTipoDocumento().equals(tipoDoc);
    }

    @Override
    public String toString() {
        return "ColaboradorFisico{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", numeroDocumento=" + numeroDocumento +
                ", tipoDocumento=" + tipoDocumento +
                ", fechaNac=" + fechaNac +
                ", direccion='" + direccion + '\'' +
                ", formulario=" + formularioPresente() +
                ", mediosDeContacto=" + mostrarInformacionDeContacto() +
                ", puntosAcumulados=" + puntosAcumulados +
                ", contribucionesQueRealizara=" + contribucionesQueRealizara +
                '}';
    }
}
