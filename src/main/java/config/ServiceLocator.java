package config;


import controladores.*;
import controladores.ControladorBeneficiarios;
import controladores.ControladorColaboradorFisico;
import controladores.ControladorColaboradorJuridico;
import controladores.ControladorHeladeras;
import domain.Config;
import domain.puntos.CalculadoraPuntos;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import repositorios.Repositorio;
import repositorios.repositoriosBDD.*;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioHeladeras;
import repositorios.repositoriosBDD.RepositorioUsuarios;
import repositorios.repositoriosBDD.RepositorioVulnerables;
import utils.Broker.ClientCredentials;
import utils.Broker.IServiceBroker;
import utils.Broker.ServiceBroker;
import utils.Broker.receptors.ReceptorApertura;
import utils.Broker.receptors.ReceptorAutorizacion;
import utils.Broker.receptors.ReceptorMov;
import utils.Broker.receptors.ReceptorTemp;
import utils.cargaMasiva.ImportadorCSV;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static Map<String, Object> instances = new HashMap<>();

    public static void close() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    private static EntityManagerFactory entityManagerFactory; // Fábrica de EntityManager

    static {
        // Crear la fábrica de EntityManager una sola vez
        entityManagerFactory = Persistence.createEntityManagerFactory("simple-persistence-unit");
    }
    @SuppressWarnings("unchecked")
    public static <T> T instanceOf(Class<T> componentClass) {
        String componentName = componentClass.getName();

        if (!instances.containsKey(componentName)) {
            if(componentName.equals(ControladorHeladeras.class.getName())) {
                ControladorHeladeras instance = new ControladorHeladeras(instanceOf(RepositorioHeladeras.class));
                instances.put(componentName, instance);
            }
            else if (componentName.equals(EntityManager.class.getName())) {
                EntityManager instance = entityManagerFactory.createEntityManager();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(RepositorioHeladeras.class.getName())) {
                RepositorioHeladeras instance = new RepositorioHeladeras();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ControladorColaboradorFisico.class.getName())) {
                ControladorColaboradorFisico instance = new ControladorColaboradorFisico(instanceOf(RepositorioColaboradores.class),instanceOf(RepositorioUsuarios.class),instanceOf(RepositorioRoles.class),instanceOf(Repositorio.class),instanceOf(RepositorioRegistrosVulnerables.class),instanceOf(RepositorioDistribuciones.class),instanceOf(RepositorioViandas.class));
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
                ControladorColaboradorJuridico instance = new ControladorColaboradorJuridico(instanceOf(RepositorioColaboradores.class),instanceOf(RepositorioUsuarios.class),instanceOf(RepositorioRoles.class),instanceOf(RepositorioModeloHeladeras.class),instanceOf(RepositorioProvincias.class),instanceOf(RepositorioLocalidades.class),instanceOf(RepositorioBarrios.class),instanceOf(Repositorio.class),instanceOf(CalculadoraPuntos.class),instanceOf(RepositorioMantenciones.class));
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
            else if(componentName.equals(ControladorDonacionDinero.class.getName())){
                ControladorDonacionDinero instance = new ControladorDonacionDinero(instanceOf(RepositorioDonacionesDinero.class), instanceOf(RepositorioColaboradores.class));
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ControladorViandas.class.getName())){
                ControladorViandas instance = new ControladorViandas(instanceOf(RepositorioViandas.class), instanceOf(RepositorioColaboradores.class), instanceOf(RepositorioHeladeras.class));
                instances.put(componentName, instance);
            }
            else if(componentName.equals(RepositorioViandas.class.getName())){
                RepositorioViandas instance = new RepositorioViandas();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(RepositorioDonacionesDinero.class.getName())){
                RepositorioDonacionesDinero instance = new RepositorioDonacionesDinero();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ControladorCargaMasiva.class.getName())){
                ControladorCargaMasiva instance = new ControladorCargaMasiva(instanceOf(ImportadorCSV.class));
                instances.put(componentName, instance);
            }
            else if(componentName.equals(RepositorioIncidentes.class.getName())){
                RepositorioIncidentes instance = new RepositorioIncidentes();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ImportadorCSV.class.getName())){
                ImportadorCSV instance = new ImportadorCSV(instanceOf(RepositorioColaboradores.class));
                instances.put(componentName, instance);
            }
            else if(componentName.equals(RepositorioModeloHeladeras.class.getName())){
                RepositorioModeloHeladeras instance = new RepositorioModeloHeladeras();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(RepositorioProvincias.class.getName())){
                RepositorioProvincias instance = new RepositorioProvincias();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(RepositorioLocalidades.class.getName())){
                RepositorioLocalidades instance = new RepositorioLocalidades();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(RepositorioBarrios.class.getName())){
                RepositorioBarrios instance = new RepositorioBarrios();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ServiceBroker.class.getName())){
                String topic1 = "dds2024/heladera/movimiento";
                String topic2 = "dds2024/heladera/temperatura";
                String topic3 = "dds2024/heladera/autorizacion";
                String topic4 = "dds2024/heladera/apertura";
                Config config = Config.getInstance();
                String broker = config.getProperty("broker.url");
                IMqttMessageListener receptor1 = new ReceptorMov(instanceOf(Repositorio.class));
                IMqttMessageListener receptor2 = new ReceptorTemp(instanceOf(Repositorio.class));
                IMqttMessageListener receptor3 = new ReceptorAutorizacion(instanceOf(Repositorio.class));
                IMqttMessageListener receptor4 = new ReceptorApertura(instanceOf(Repositorio.class));


                ClientCredentials credentials = new ClientCredentials(config.getProperty("broker.clientID"),config.getProperty("broker.clientUsername"),config.getProperty("broker.clientPassword"));
                ServiceBroker instance = ServiceBroker.getInstance(broker,credentials);
                instance.init();
                instance.suscribe(topic1,receptor1);
                instance.suscribe(topic2,receptor2);
                instance.suscribe(topic3,receptor3);
                instance.suscribe(topic4,receptor4);

                instances.put(componentName, instance);
            }
            else if(componentName.equals(CalculadoraPuntos.class.getName())){
                CalculadoraPuntos instance = CalculadoraPuntos.obtenerInstancia();
                instances.put(componentName, instance);
            }else if(componentName.equals(RepositorioRegistrosVulnerables.class.getName())){
                RepositorioRegistrosVulnerables instance = new RepositorioRegistrosVulnerables();
                instances.put(componentName, instance);
            }else if(componentName.equals(RepositorioDistribuciones.class.getName())){
                RepositorioDistribuciones instance = new RepositorioDistribuciones();
                instances.put(componentName, instance);
            }else if(componentName.equals(RepositorioViandas.class.getName())){
                RepositorioViandas instance = new RepositorioViandas();
                instances.put(componentName, instance);
            }else if(componentName.equals(RepositorioMantenciones.class.getName())){
                RepositorioMantenciones instance = new RepositorioMantenciones();
                instances.put(componentName, instance);
            }
        }
        return (T) instances.get(componentName);
    }
}