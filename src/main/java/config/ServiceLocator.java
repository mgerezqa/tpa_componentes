package config;


import controladores.*;
import controladores.ControladorBeneficiarios;
import controladores.ControladorColaboradorFisico;
import controladores.ControladorColaboradorJuridico;
import controladores.ControladorHeladeras;
import repositorios.Repositorio;
import repositorios.repositoriosBDD.*;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioHeladeras;
import repositorios.repositoriosBDD.RepositorioUsuarios;
import repositorios.repositoriosBDD.RepositorioVulnerables;

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
                ControladorColaboradorFisico instance = new ControladorColaboradorFisico(instanceOf(RepositorioColaboradores.class),instanceOf(RepositorioUsuarios.class),instanceOf(RepositorioRoles.class));
                instances.put(componentName, instance);
            }
            else if (componentName.equals(RepositorioColaboradores.class.getName())) {
                RepositorioColaboradores instance = new RepositorioColaboradores();
                instances.put(componentName, instance);
            }

            else if(componentName.equals(RepositorioUsuarios.class.getName())){
                RepositorioUsuarios instance = new RepositorioUsuarios();
                instances.put(componentName,instance);
            }

            else if(componentName.equals(Repositorio.class.getName())){
                Repositorio instance = new Repositorio();
                instances.put(componentName,instance);
            }

            else if(componentName.equals(ControladorColaboradorJuridico.class.getName())) {
                ControladorColaboradorJuridico instance = new ControladorColaboradorJuridico(instanceOf(RepositorioColaboradores.class),instanceOf(RepositorioUsuarios.class),instanceOf(RepositorioRoles.class));
                instances.put(componentName, instance);
            }else if(componentName.equals(ControladorTecnicos.class.getName())){
                ControladorTecnicos instance = new ControladorTecnicos(instanceOf(RepositorioTecnicos.class));
                instances.put(componentName, instance);
            }else if(componentName.equals(RepositorioTecnicos.class.getName())){
                RepositorioTecnicos instance = new RepositorioTecnicos();
                instances.put(componentName,instance);
            }else if(componentName.equals(ControladorSuscripciones.class.getName())){
                ControladorSuscripciones instance = new ControladorSuscripciones(instanceOf(RepositorioSuscripciones.class),instanceOf(RepositorioColaboradores.class),instanceOf(RepositorioHeladeras.class));
                instances.put(componentName, instance);
            }else if(componentName.equals(RepositorioSuscripciones.class.getName())){
                RepositorioSuscripciones instance = new RepositorioSuscripciones();
                instances.put(componentName,instance);
            }else if(componentName.equals(ControladorTarjetas.class.getName())){
                ControladorTarjetas instance = new ControladorTarjetas(instanceOf(RepositorioTarjetas.class),instanceOf(RepositorioVulnerables.class),instanceOf(RepositorioColaboradores.class));
                instances.put(componentName, instance);
            }else if(componentName.equals(RepositorioTarjetas.class.getName())){
                RepositorioTarjetas instance = new RepositorioTarjetas();
                instances.put(componentName,instance);
            }

            else if (componentName.equals(RepositorioVulnerables.class.getName())){
                RepositorioVulnerables instance = new RepositorioVulnerables();
                instances.put(componentName, instance);
            }

            else if(componentName.equals(ControladorBeneficiarios.class.getName())){
                ControladorBeneficiarios instance = new ControladorBeneficiarios(instanceOf(RepositorioVulnerables.class));
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ControladorUsuario.class.getName())){
                ControladorUsuario instance = new ControladorUsuario(instanceOf(RepositorioUsuarios.class),instanceOf(RepositorioRoles.class),instanceOf(RepositorioColaboradores.class),instanceOf(RepositorioTecnicos.class));
                instances.put(componentName, instance);
            }
            else if(componentName.equals(RepositorioRoles.class.getName())){
                RepositorioRoles instance = new RepositorioRoles();
                instances.put(componentName, instance);
            }
        }
        return (T) instances.get(componentName);
    }
}