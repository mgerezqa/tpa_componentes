package domain.formularioNuevo;

import domain.formularioNuevo.campo.FactoryCampoFormulario;
import domain.formularioNuevo.campo.iCampo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder @Getter
public class RegistroFormulario implements iDatosDeRegistro{
    private TipoEntrada tipoEntrada;
    private TipoCampo tipoCampo;
    private iCampo campo;
    private String descripcion;

    public static RegistroFormulario crear(TipoEntrada unTipoEntrada, TipoCampo unTipoCampo, String unaDesc){
        return builder()
                .tipoEntrada(unTipoEntrada)
                .tipoCampo(unTipoCampo)
                .campo(FactoryCampoFormulario.crear(unTipoEntrada))
                .descripcion(unaDesc)
                .build();
    }

    public void registrarRespuesta(String respuesta){
        this.campo.agregarRespuesta(respuesta);
    }

    @Override
    public TipoCampo obtenerTipoCampo() {
        return this.tipoCampo;
    }

    @Override
    public String obtenerRespuesta() {
        return campo.obtenerRespuesta();
    }

    @Override
    public String obtenerRespuesta(Integer indice) {
        return campo.obtenerRespuesta(indice);
    }

    @Override
    public List<String> obtenerRespuestas() {
        return campo.obtenerRespuestas();
    }
}
