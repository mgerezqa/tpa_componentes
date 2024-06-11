package domain.formulario;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public  class Formulario {

    private List<Campo> campos;
    private List<UnaRespuesta<?>> respuestas;


    public Formulario() {
        this.campos = new ArrayList<>();
        this.respuestas = new ArrayList<>();
    }
    private Formulario(List<Campo> campos){
        this.campos = new ArrayList<>();
        this.respuestas = new ArrayList<>();
        campos.forEach(this::agregarCampo);
    }

    public void agregarCampo(Campo unCampo){
        campos.add(unCampo);
    }

    // dada un enum de un tipo de campo y un contenido, agrega una respuesta a la lista de respuestas
    // si no encuentra campo con ese tipo, genera una excepcion
    public <T, E extends Enum<E>> void responderCampo(E tipoCampo, T contenido){
        if(obtenerCampo(tipoCampo) == null){
            throw new RuntimeException("No se encontro el campo");
        } else {
            respuestas.add(new UnaRespuesta<>(tipoCampo, contenido));
        }
    }

    // retorna el campo que coincida con la descripcion, si no encuentra retorna null
    public <E extends Enum<E>> Campo obtenerCampo(E tipoCampo){
        return this.campos.stream().filter(campo -> campo.getTipoCampo().equals(tipoCampo.name())).findFirst().orElse(null);
    }

    private <E extends Enum<E>> List<UnaRespuesta<?>> obtenerRespuestas(E tipoCampo){
        return this.respuestas.stream().filter(respuesta -> respuesta.identificarPorTipo(tipoCampo)).collect(Collectors.toList());
    }

    // retorna una lista de las descripciones de cada campo
    public List<String> getDescripciones(){
        return this.campos.stream().map(Campo::getDescripcion).collect(Collectors.toList());
    }

    public List<String> getTiposRespuesta(){
        return this.respuestas.stream().map(UnaRespuesta::getTipoCampo).collect(Collectors.toList());
    }

    // estaCompleto retorna true si cada campo tiene almenos 1 respuesta
    // retorna false si algun campo no tiene respuesta o si no hay campos
    public Boolean estaCompleto(){
        return !campos.isEmpty() &&
                getRespuestas()
                .stream()
                .map(UnaRespuesta::getTipoCampo)
                .collect(Collectors.toCollection(HashSet::new))
                .containsAll(campos.stream().map(Campo::getTipoCampo).collect(Collectors.toList()));
    }
}
