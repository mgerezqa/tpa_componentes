package domain.formularioNuevo;

import domain.formularioNuevo.campo.iCampo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FormularioNuevo {
    private TipoFormulario tipoFormulario;
    private List<RegistroFormulario> registros;

    public FormularioNuevo(TipoFormulario unTipoForm) {
        this.tipoFormulario = unTipoForm;
        this.registros = new ArrayList<>();
    }

    public void agregarCampo(TipoEntrada tipoEntrada, TipoCampo tipoCampo, String descripcion){
        this.registros.add(RegistroFormulario.crear(tipoEntrada, tipoCampo, descripcion));
    }
    public void ingresarRespuesta(TipoCampo tipoCampo, String contenido){
        obtenerCampo(tipoCampo).agregarRespuesta(contenido);
    }
    public iCampo obtenerCampo(TipoCampo tipoCampo){
        RegistroFormulario aux = registros.stream()
                .filter(registro -> registro.getTipoCampo().equals(tipoCampo))
                .findFirst().orElse(null);
        if(aux == null)
            throw new RuntimeException("Campo invalido");
        else
            return aux.getCampo();
    }
}
