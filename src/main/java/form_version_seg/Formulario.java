package form_version_seg;

import java.util.*;

public  class Formulario {
    private List<Campo> campos = new ArrayList<>();
    private Colaborador colaborador;
    private Map<Campo,String> respuestas = new HashMap<>();
    // private List<Colaborador> colaboradores = new ArrayList<>(); Por ahora no


    public void agregar(Campo campo) {
        campos.add(campo);
    }

    public Formulario(Colaborador colaborador) {
        this.colaborador = colaborador;
        agregar(new Campo("Nombre",TipoEntrada.ENTRADA_TEXTO));
        agregar(new Campo("Apellido",TipoEntrada.ENTRADA_TEXTO));
        agregar(new Campo("Contacto",TipoEntrada.ENTRADA_TEXTO));
    }

    public void guardarRespuesta(Colaborador colaborador){
        Scanner scanner = new Scanner(System.in);

        for(Campo campo : campos) {
            System.out.println("Ingrese " + campo.getdescripcion() + ":");
            String respuesta = scanner.nextLine();
            respuestas.put(campo, respuesta);
            colaborador.setInformacion(campo.getdescripcion(), respuesta);
        }
   }

    public void leer(Colaborador colaborador){
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

