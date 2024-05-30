package domain.formulario;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public  class Formulario {

    private List<Campo> campos;
    //private Map<Campo, UnaRespuesta> contenido = new HashMap<>();
    private List<UnaRespuesta<?>> respuestas;


    public Formulario() {
        this.campos = new ArrayList<>();
        this.respuestas = new ArrayList<>();
    }
    public Formulario(List<Campo> campos){
        this.campos = new ArrayList<>();
        this.respuestas = new ArrayList<>();
        campos.stream().forEach(this::agregarCampo);
    }

    public void agregarCampo(Campo unCampo){
        campos.add(unCampo);
    }

    // dada una descripcion de un campo y un contenido, agrega una respuesta a la lista de respuestas
    // si no encuentra campo con esa descripcion, genera una excepcion
    public <T> void responderCampo(String descripcionCampo, T contenido){
        Campo aux = obtenerCampo(descripcionCampo);
        if(aux == null){
            throw new RuntimeException("No se encontro el campo");
        } else {
            respuestas.add(new UnaRespuesta<>(aux, contenido));
        }
    }
    public <T> void responderCampo(Campo campo, T contenido){
        respuestas.add(new UnaRespuesta<>(campo, contenido));
    }

    // retorna el campo que coincida con la descripcion, si no encuentra retorna null
    public Campo obtenerCampo(String descripcion){
        return campos.stream().filter(campo -> campo.getDescripcion().equals(descripcion)).findFirst().orElse(null);
    }

    // por logica te deberia devolver con T como el tipo de respuesta que contiene, pero
    // no se bien como forzar eso, asi que devuelve Object, se debe castear la respuesta para usarla
    // ejemplo de uso en los colaboradores
    public <T> T obtenerRespuesta(String descripcion){
        Campo aux = obtenerCampo(descripcion);
        if(aux == null)
            throw new RuntimeException("Campo no encontrado");
        else{
            return (T) respuestas.stream().filter(resp -> resp.getCampo().equals(aux)).findFirst().get().getRespuesta();
        }
    }

    // retorna una lista de las descripciones de cada campo
    public List<String> getDescripciones(){
        return campos.stream().map(Campo::getDescripcion).collect(Collectors.toList());
    }

    // estaCompleto retorna true si cada campo tiene almenos 1 respuesta
    // retorna false si algun campo no tiene respuesta o si no hay campos
    public Boolean estaCompleto(){
        return !campos.isEmpty() &&
                getRespuestas()
                .stream()
                .map(UnaRespuesta::getCampo)
                .collect(Collectors.toCollection(HashSet::new))
                .containsAll(campos);
    }

    /*
    public void guardar (Respuesta valorRespuesta) {
        respuestas.add(valorRespuesta);
    }

    public void leer() {
        for (Respuesta respuesta : respuestas) {
            System.out.println("Campo: " + respuesta.getCampo().getdescripcion() + " - Valor: " + respuesta.getValorRespuesta());
        }
    }

    public void agregar(Campo campo) {
        campos.add(campo);
    }

    public void mostrarCampos() {
        for (Campo campo : campos) {
            System.out.println("Campo: " + campo.getdescripcion() + " - Tipo: " + campo.getTipoEntrada());
        }

    }

    */
}


//
//    public Formulario() {
//        agregar(new Campo("Nombre", TipoEntrada.ENTRADA_TEXTO));
//        agregar(new Campo("Apellido",TipoEntrada.ENTRADA_TEXTO));
//        agregar(new Campo("Contacto",TipoEntrada.ENTRADA_TEXTO));
//    }
//
//    public void guardarRespuesta(ColaboradorFisico colaboradorFisico){
//        Scanner scanner = new Scanner(System.in);
//
//        for(Campo campo : campos) {
//            System.out.println("Ingrese " + campo.getdescripcion() + ":");
//            String respuesta = scanner.nextLine();
//            respuestas.put(campo, respuesta);
//        }
//   }
//
//    public void leer(ColaboradorFisico colaboradorFisico){ //no estoy leyendo los campos del colaborador
//        for (Map.Entry<Campo, String> entry : respuestas.entrySet()) {
//            System.out.println("Campo: " + entry.getKey().getdescripcion() + " - Respuesta: " + entry.getValue());
//        }
//    }
//
//

    //-----------------------------Validaciones---------------------------------//
//    public void agregarMedioContacto(MedioDeContacto nuevoMedio) {
//        // Verificar el tipo del nuevo medio de contacto y contar cuántos hay en la lista actual
//        int cantidadActual = contarMediosPorTipo(nuevoMedio.getClass());
//
//        // Permitir agregar el nuevo medio de contacto solo si no hay otro del mismo tipo
//        if (cantidadActual == 0) {
//            medioContacto.add(nuevoMedio);
//            System.out.println("Medio de contacto agregado correctamente: " + nuevoMedio.obtenerDescripcion());
//        } else {
//            System.out.println("No se puede agregar el medio de contacto. Ya existe otro medio de tipo: " + nuevoMedio.getClass().getSimpleName());
//        }
//    }
//
//    // Método  para contar la cantidad de medios de un tipo específico en la lista
//    private int contarMediosPorTipo(Class<?> tipoMedio) {
//        int contador = 0;
//        for (MedioDeContacto medio : medioContacto) {
//            if (tipoMedio.isInstance(medio)) {
//                contador++;
//            }
//        }
//        return contador;
//    }




