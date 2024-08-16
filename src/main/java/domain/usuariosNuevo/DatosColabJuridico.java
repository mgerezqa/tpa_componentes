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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter
public class DatosColabJuridico {
    private String razonSocial;
    private TipoRazonSocial tipoRazonSocial;
    private Rubro tipoDeRubro;
    private List<MedioDeContacto> contactos;
    private Set<TipoContribucion> contribuciones;
    private String direccion;

    public DatosColabJuridico(){
        this.contactos = new ArrayList<>();
        this.contribuciones = new LinkedHashSet<>();
    }

    public DatosColabJuridico(String razonSocial, TipoRazonSocial tipoRazonSocial, Rubro tipoDeRubro, List<MedioDeContacto> contactos, Set<TipoContribucion> contribuciones, String direccion) {
        this.razonSocial = razonSocial;
        this.tipoRazonSocial = tipoRazonSocial;
        this.tipoDeRubro = tipoDeRubro;
        this.contactos = contactos;
        this.contribuciones = contribuciones;
        this.direccion = direccion;
    }

    public void agregarContacto(MedioDeContacto contacto){
        this.contactos.add(contacto);
    }
    public void agregarFormasContribucion(TipoContribucion unTipo){
        this.contribuciones.add(unTipo);
    }

    public static DatosColabJuridico generar(FormularioNuevo unForm){
        DatosColabJuridico aux = new DatosColabJuridico();
        if(unForm.getTipoFormulario().equals(TipoFormulario.COLABORADOR_JURIDICO)){
            for(iDatosDeRegistro datos : unForm.getRegistros()){
                if(datos.fueRespondido()){
                    switch (datos.obtenerTipoCampo()){
                        case CAMPO_RAZON_SOCIAL -> aux.setRazonSocial(datos.obtenerRespuesta());
                        case CAMPO_TIPO_JURIDICO -> aux.setTipoRazonSocial(TipoRazonSocial.obtenerEnum(datos.obtenerRespuesta()));
                        case CAMPO_RUBRO -> aux.setTipoDeRubro(Rubro.obtenerEnum(datos.obtenerRespuesta()));
                        case CAMPO_EMAIL -> datos.obtenerRespuestas().forEach(respuesta -> aux.agregarContacto(new Email(respuesta)));
                        case CAMPO_WHATSAPP -> datos.obtenerRespuestas().forEach(respuesta -> aux.agregarContacto(new Whatsapp(respuesta)));
                        case CAMPO_TELEGRAM -> datos.obtenerRespuestas().forEach(respuesta -> aux.agregarContacto(new Telegram(respuesta)));
                        case CAMPO_FORMA_CONTRIBUCION -> datos.obtenerRespuestas().forEach(respuesta ->aux.agregarFormasContribucion(TipoContribucion.obtenerEnum(respuesta)));
                        case CAMPO_DIRECCION -> aux.setDireccion(datos.obtenerRespuesta());
                    }
                }
            }
            return aux;
        } else
            throw new RuntimeException("Formulario Invalido!");
    }
}
