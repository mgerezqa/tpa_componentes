package domain.formulario;

import domain.usuarios.ColaboradorFisico;

import java.util.*;

public  class Formulario {
    private List<Campo> campos = new ArrayList<>();
    private ColaboradorFisico colaboradorFisico;
    private Map<Campo,String> respuestas = new HashMap<>();
    // private List<Colaborador> colaboradores = new ArrayList<>(); Por ahora no


    public void agregar(Campo campo) {
        campos.add(campo);
    }

    public Formulario() {
        agregar(new Campo("Nombre", TipoEntrada.ENTRADA_TEXTO));
        agregar(new Campo("Apellido",TipoEntrada.ENTRADA_TEXTO));
        agregar(new Campo("Contacto",TipoEntrada.ENTRADA_TEXTO));
    }

    public void guardarRespuesta(ColaboradorFisico colaboradorFisico){
        Scanner scanner = new Scanner(System.in);

        for(Campo campo : campos) {
            System.out.println("Ingrese " + campo.getdescripcion() + ":");
            String respuesta = scanner.nextLine();
            respuestas.put(campo, respuesta);
        }
   }

    public void leer(ColaboradorFisico colaboradorFisico){ //no estoy leyendo los campos del colaborador
        for (Map.Entry<Campo, String> entry : respuestas.entrySet()) {
            System.out.println("Campo: " + entry.getKey().getdescripcion() + " - Respuesta: " + entry.getValue());
        }
    }

    public void mostrarCampos() {
        for (Campo campo : campos) {
            System.out.println("Campo: " + campo.getdescripcion() + " - Tipo: " + campo.getTipoEntrada());
        }
    }


}

