package config;


import controladores.*;
import repositorios.Repositorio;
import repositorios.repositoriosBDD.*;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static Map<String, Object> instances = new HashMap<>();


    @SuppressWarnings("unchecked")
    public static <T> T instanceOf(Class<T> componentClass) {
        String componentName = componentClass.getName();

        if (!instances.containsKey(componentName)) {
            if(componentName.equals(ControladorHeladeras.class.getName())) {
                ControladorHeladeras instance = new ControladorHeladeras(instanceOf(RepositorioHeladeras.class));
                instances.put(componentName, instance);
            }
            else if (componentName.equals(RepositorioHeladeras.class.getName())) {
                RepositorioHeladeras instance = new RepositorioHeladeras();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ControladorColaboradorFisico.class.getName())) {
                ControladorColaboradorFisico instance = new ControladorColaboradorFisico(instanceOf(RepositorioColaboradores.class),instanceOf(RepositorioUsuarios.class));
                instances.put(componentName, instance);
            }
            else if (componentName.equals(RepositorioColaboradores.class.getName())) {
                RepositorioColaboradores instance = new RepositorioColaboradores();
                instances.put(componentName, instance);
            }else if(componentName.equals(RepositorioUsuarios.class.getName())){
                RepositorioUsuarios instance = new RepositorioUsuarios();
                instances.put(componentName,instance);
            }else if(componentName.equals(Repositorio.class.getName())){
                Repositorio instance = new Repositorio();
                instances.put(componentName,instance);
            }else if(componentName.equals(ControladorColaboradorJuridico.class.getName())) {
                ControladorColaboradorJuridico instance = new ControladorColaboradorJuridico(instanceOf(RepositorioColaboradores.class),instanceOf(RepositorioUsuarios.class));
                instances.put(componentName, instance);
            }else if(componentName.equals(ControladorTecnicos.class.getName())){
                ControladorTecnicos instance = new ControladorTecnicos(instanceOf(RepositorioTecnicos.class));
                instances.put(componentName, instance);
            }else if(componentName.equals(RepositorioTecnicos.class.getName())){
                RepositorioTecnicos instance = new RepositorioTecnicos();
                instances.put(componentName,instance);
            }else if(componentName.equals(ControladorSuscripciones.class.getName())){
                ControladorSuscripciones instance = new ControladorSuscripciones(instanceOf(RepositorioSuscripciones.class));
                instances.put(componentName, instance);
            }else if(componentName.equals(RepositorioSuscripciones.class.getName())){
                RepositorioSuscripciones instance = new RepositorioSuscripciones();
                instances.put(componentName,instance);
            }
        }

        return (T) instances.get(componentName);
    }
}