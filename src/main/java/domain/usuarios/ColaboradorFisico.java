package domain.usuarios;

import domain.carga_masiva.ContribucionCSV;
import domain.contacto.MedioDeContacto;
import domain.donaciones.TipoContribucion;
import domain.formulario.Formulario;
import domain.formulario.TipoCampoFormulario;
import domain.formulario.UnaRespuesta;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Getter @Setter
public class ColaboradorFisico extends Colaborador {

    private String nombre;
    private String apellido;
    private Integer numeroDocumento;
    private TipoDocumento tipoDocumento;
    private LocalDate fechaNac;
    private String direccion;

    public ColaboradorFisico(Formulario unFormulario){
        this.cargarFormulario(unFormulario);
        this.formulario = unFormulario;
        this.darDeAlta();
    }
    // este constructor se usa cuando no se encuentra al colaborador al hacer la migracion
    public ColaboradorFisico(ContribucionCSV unaLectura) {
        this.nombre = unaLectura.getNombre();
        this.apellido = unaLectura.getApellido();
        this.tipoDocumento = unaLectura.getTipoDoc();
        this.numeroDocumento = unaLectura.getNroDoc();
        this.contribucionesQueRealizara.add(unaLectura.getTipoColab());
        // aca se debe pedir que una ves logueado ingrese el formulario
        this.darDeBaja();
    }

    // ingresa un formulario, verifica si el formulario cumple la condicion para ser cargado,
    // si cumple, se itera las respuestas y se van cargando los valores al colaborador,
    // si no cumple, tira excepcion
    private void cargarFormulario(Formulario unFormulario){
        if(!formularioCompleto(unFormulario))
            throw new RuntimeException("Formulario incompleto/invalido!");
        unFormulario.getRespuestas().forEach(this::cargarRespuesta);
    }

    // checkea si los campos minimamente necesarios para el colaborador estan en el formulario
    // NO chequea si los datos son correctos, deberia estar chequeado de antemano
    private Boolean formularioCompleto(Formulario unForm){
        List<TipoCampoFormulario> camposNecesarios = Arrays.asList(
                TipoCampoFormulario.NOMBRE,
                TipoCampoFormulario.APELLIDO,
                TipoCampoFormulario.CONTACTO,
                TipoCampoFormulario.TIPO_DOCUMENTO,
                TipoCampoFormulario.NRO_DOCUMENTO,
                TipoCampoFormulario.FORMA_CONTRIBUCION);
        return tiposRespuestasForm(unForm).containsAll(camposNecesarios);
    }

    // toma una respuesta, y segun el campo asociado se setea su contenido en el atributo apropiado
    // si el campo no corresponde a ningun atributo del colaborador, no hace nada
    private void cargarRespuesta(UnaRespuesta<?> unaResp){
        switch (TipoCampoFormulario.obtenerEnum(unaResp.getTipoCampo())){
            case NOMBRE:
                this.nombre = (String)unaResp.getRespuesta();
                break;
            case APELLIDO:
                this.apellido = (String) unaResp.getRespuesta();
                break;
            case TIPO_DOCUMENTO:
                this.tipoDocumento = (TipoDocumento) unaResp.getRespuesta();
                break;
            case NRO_DOCUMENTO:
                this.numeroDocumento = (Integer) unaResp.getRespuesta();
                break;
            case CONTACTO:
                this.agregarContacto((MedioDeContacto) unaResp.getRespuesta());
                break;
            case FECHA_NACIMIENTO:
                this.fechaNac = (LocalDate) unaResp.getRespuesta();
                break;
            case DIRECCION:
                this.direccion = (String) unaResp.getRespuesta();
                break;
            case FORMA_CONTRIBUCION:
                this.agregarFormaContribucionQueRealizara((TipoContribucion) unaResp.getRespuesta());
                break;
            default:
        }
    }

    public List<String> mostrarInformacionDeContacto(){
        return this.mediosDeContacto.stream().map(MedioDeContacto::obtenerDescripcion).collect(Collectors.toList());
    }


    @Override
    public List<TipoContribucion> contribucionesDisponibles() {
        return Arrays.asList(TipoContribucion.DINERO, TipoContribucion.VIANDA, TipoContribucion.DISTRIBUCION, TipoContribucion.TARJETA);
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
