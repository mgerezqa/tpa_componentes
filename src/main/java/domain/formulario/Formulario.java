package domain.formulario;

import domain.usuarios.Colaborador;

import java.util.HashMap;
import java.util.Map;

public class Formulario {
    private Map<String, Campo> campos;
    public Formulario(){
        this.campos = new HashMap<>();
    }

    public void agregarCampo(String label, Campo campo){
        this.campos.put(label, campo);
    }

    public void agregarCampo(Enum<?> label, Campo campo) {
        this.campos.put(label.name(), campo);
    }

    public Campo getCampo(String nombre){
        return this.campos.get(nombre);
    }

    public Map<String, Campo> getCampos() {
        return this.campos;
    }

    public void ingresarRespuesta(String label, String valor){
        if(!this.campos.containsKey(label)){
            System.out.println("No se encuentra el campo");
        } else {
            this.campos.get(label).setValor(valor);
        }
    }

    public void ingresarRespuesta(Enum<?> label, String valor){
        if(!this.campos.containsKey(label.name())){
            System.out.println("No se encuentra el campo");
        } else {
            this.campos.get(label.name()).setValor(valor);
        }
    }


    public boolean buscarCampo(String label){
        return this.campos.containsKey(label);
    }

    public boolean buscarCampo(Enum<?> label) {
        return this.campos.containsKey(label.name());
    }

    public void modificarRespuesta(String label, String valor){
        this.ingresarRespuesta(label, valor);
    }
    public void modificarRespuesta(Enum<?> label, String valor){
        this.ingresarRespuesta(label.name(), valor);
    }

    public String obtenerRespuesta(String label){
        return this.getCampo(label).getValor();
    }

    public String obtenerRespuesta(Enum<?> label) {
        return this.getCampo(label.name()).getValor();
    }


    public Integer cantCampos(){
        return campos.size();
    }

}
