package domain.formulario;

import domain.contacto.MedioDeContacto;
import domain.usuarios.ColaboradorFisico;

import java.util.*;

public  class Formulario {

    private List<Campo> campos = new ArrayList<>();
    //    private Map<Campo,Object> respuestas = new HashMap<>();
    private List<Respuesta> respuestas = new ArrayList<>();

    public Formulario() {

    }

    public void guardar(Respuesta valorRespuesta) {
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




