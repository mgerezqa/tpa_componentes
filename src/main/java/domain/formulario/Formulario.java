package domain.formulario;

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

    public Campo getCampo(String nombre){
        return this.campos.get(nombre);
    }

    public void cargarValor(String label, String valor){

        if(!this.campos.containsKey(label)){
            System.out.println("No se encuentra el campo");
        } else {
            this.campos.get(label).setValor(valor);
        }
    }

    public String obtenerValor(String label){
        return this.getCampo(label).getValor();
    }

    public void mostrarLabels(){
        String mostrar = "Estos son los nombres de campos cargados: \n";
        for(Map.Entry<String, Campo> entry : this.campos.entrySet()){
            mostrar += entry.getKey()+ "\n";
        }
        System.out.println(mostrar);
    }
    public void mostrarCampos(){
        String mostrar = "Estos son los campos cargados: \n";
        for(Map.Entry<String, Campo> entry : this.campos.entrySet()){
            mostrar += entry.getKey() + ": " + entry.getValue().getValor() + "\n";
        }
        System.out.println(mostrar);
    }

    public Integer cantCampos(){
        return campos.size();
    }

}
