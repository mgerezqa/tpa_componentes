package domain.usuariosNuevo;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telegram;
import domain.contacto.Whatsapp;
import domain.contribucionNuevo.TipoContribucion;
import domain.formularioNuevo.FormularioNuevo;
import domain.formularioNuevo.TipoFormulario;
import domain.formularioNuevo.iDatosDeRegistro;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class DatosColabFisico {
    private String nombre;
    private String apellido;
    private Integer numeroDocumento;
    private TipoDocumento tipoDocumento;
    private List<MedioDeContacto> contactos;
    private List<TipoContribucion> contribuciones;
    private LocalDate fechaNac;
    private String direccion;

    private DatosColabFisico(){
        this.contactos = new ArrayList<>();
        this.contribuciones = new ArrayList<>();
    }
    public DatosColabFisico(String nombre, String apellido, Integer numeroDocumento, TipoDocumento tipoDocumento, List<MedioDeContacto> contactos, List<TipoContribucion> contribuciones, LocalDate fechaNac, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDocumento = numeroDocumento;
        this.tipoDocumento = tipoDocumento;
        this.contactos = contactos;
        this.contribuciones = contribuciones;
        this.fechaNac = fechaNac;
        this.direccion = direccion;
    }

    public void agregarContacto(MedioDeContacto contacto){
        this.contactos.add(contacto);
    }
    public void agregarFormasContribucion(TipoContribucion unTipo){
        this.contribuciones.add(unTipo);
    }

    public static DatosColabFisico generar(FormularioNuevo unForm){
        DatosColabFisico aux = new DatosColabFisico();
        if(unForm.getTipoFormulario().equals(TipoFormulario.COLABORADOR_FISICO)){
            for(iDatosDeRegistro datos : unForm.getRegistros()){
                switch (datos.obtenerTipoCampo()){
                    case CAMPO_NOMBRE -> aux.setNombre(datos.obtenerRespuesta());
                    case CAMPO_APELLIDO -> aux.setApellido(datos.obtenerRespuesta());
                    case CAMPO_TIPO_DOCUMENTO -> aux.setTipoDocumento(TipoDocumento.obtenerEnum(datos.obtenerRespuesta()));
                    case CAMPO_NRO_DOCUMENTO -> aux.setNumeroDocumento(Integer.parseInt(datos.obtenerRespuesta()));
                    case CAMPO_EMAIL -> datos.obtenerRespuestas().forEach(respuesta -> aux.agregarContacto(new Email(respuesta)));
                    case CAMPO_WHATSAPP -> datos.obtenerRespuestas().forEach(respuesta -> aux.agregarContacto(new Whatsapp(respuesta)));
                    case CAMPO_TELEGRAM -> datos.obtenerRespuestas().forEach(respuesta -> aux.agregarContacto(new Telegram(respuesta)));
                    case CAMPO_FORMA_CONTRIBUCION -> datos.obtenerRespuestas().forEach(respuesta ->aux.agregarFormasContribucion(TipoContribucion.obtenerEnum(respuesta)));
                    case CAMPO_FECHA_NACIMIENTO -> aux.setFechaNac(LocalDate.parse(datos.obtenerRespuesta()));
                    case CAMPO_DIRECCION -> aux.setDireccion(datos.obtenerRespuesta());
                }
            }
            return aux;
        } else
            throw new RuntimeException("Formulario Invalido!");
    }

}
