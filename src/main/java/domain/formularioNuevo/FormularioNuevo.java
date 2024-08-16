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

    public void agregarRegistro(TipoEntrada tipoEntrada, String tipoCampo, String descripcion){
        this.registros.add(RegistroFormulario.crear(tipoEntrada, tipoCampo, descripcion));
    }
    public void ingresarRespuesta(String tipoCampo, String contenido){
        obtenerCampo(tipoCampo).agregarRespuesta(contenido);
    }
    public String obtenerRespuesta(String tipoCampo){
        return obtenerCampo(tipoCampo).obtenerRespuesta();
    }
    public List<String> obtenerRespuestas(String tipoCampo){
        return obtenerCampo(tipoCampo).obtenerRespuestas();
    }

    public iCampo obtenerCampo(String tipoCampo){
        RegistroFormulario aux = registros.stream()
                .filter(registro -> registro.getTipoCampo().equals(tipoCampo))
                .findFirst().orElse(null);
        if(aux == null)
            throw new RuntimeException("Campo invalido");
        else
            return aux.getCampo();
    }

    public Integer cantidadRegistros(){
        return registros.size();
    }
}
