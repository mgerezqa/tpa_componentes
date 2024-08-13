package domain.usuariosNuevo;

import domain.contacto.MedioDeContacto;
import domain.contribucionNuevo.TipoContribucion;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class ColaboradorJuridico extends Colaborador{
    private String razonSocial;
    private TipoRazonSocial tipoRazonSocial;
    private Rubro tipoDeRubro;
    private String direccion;

    public ColaboradorJuridico() {
    }


    public List<String> mostrarInformacionDeContacto(){
        return this.mediosDeContacto.stream().map(MedioDeContacto::informacionDeMedioDeContacto).collect(Collectors.toList());
    }

    @Override
    public List<TipoContribucion> contribucionesDisponibles() {
        return Arrays.asList(TipoContribucion.DINERO, TipoContribucion.MANTENIMIENTO);
    }

    @Override
    public String toString() {
        return "ColaboradorJuridico{" +
                "razonSocial='" + razonSocial + '\'' +
                ", tipoRazonSocial=" + tipoRazonSocial +
                ", tipoDeRubro=" + tipoDeRubro +
                ", direccion='" + direccion + '\'' +
                ", formulario=" + formularioPresente() +
                ", mediosDeContacto=" + mostrarInformacionDeContacto() +
                ", puntosAcumulados=" + puntosAcumulados +
                ", contribucionesQueRealizara=" + contribucionesQueRealizara +
                '}';
    }

}
