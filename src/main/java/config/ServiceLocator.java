package config;


import controladores.ControladorColaboradorFisico;
import controladores.ControladorHeladeras;
import repositorios.Repositorio;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioHeladeras;
import repositorios.repositoriosBDD.RepositorioUsuarios;

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
            }
        }

        return (T) instances.get(componentName);
    }
}