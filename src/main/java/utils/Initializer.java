package utils;

import config.ServiceLocator;
import domain.contacto.Email;
import domain.contacto.Telegram;
import domain.contacto.Whatsapp;
import domain.donaciones.MantenerHeladera;
import domain.donaciones.RegistroDePersonaVulnerable;
import domain.formulario.documentos.Cuil;
import domain.formulario.documentos.Documento;
import domain.formulario.documentos.TipoDocumento;
import domain.geografia.*;
import domain.geografia.area.AreaDeCobertura;
import domain.geografia.area.TamanioArea;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.persona.PersonaVulnerable;
import domain.tarjeta.TarjetaColaborador;
import domain.tarjeta.TarjetaVulnerable;
import domain.usuarios.*;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;
import repositorios.repositoriosBDD.*;
import utils.Broker.ServiceBroker;
import utils.notificador.Notificador;

import java.time.LocalDate;
import java.util.*;

public class Initializer implements WithSimplePersistenceUnit {
    private RepositorioRoles repositorioRoles;
    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioColaboradores repositorioColaboradores;
    private RepositorioTecnicos repositorioTecnicos;
    private Repositorio repositorio;
    private List<ModeloDeHeladera> modelosHeladerasPorDefecto = new ArrayList<>();
    private RepositorioMantenciones repositorioDonaciones;

    public Initializer(RepositorioMantenciones repositorioDonaciones, RepositorioRoles repositorioRoles, RepositorioUsuarios repositorioUsuarios, RepositorioColaboradores repositorioColaboradores, RepositorioTecnicos repositorioTecnicos,Repositorio repositorio) {
        this.repositorioDonaciones = repositorioDonaciones;
        this.repositorioRoles = repositorioRoles;
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioColaboradores = repositorioColaboradores;
        this.repositorioTecnicos = repositorioTecnicos;
        this.repositorio = repositorio;
    }

    public void init() {
        withTransaction(()->{
            this.instaciarLosRoles();
            this.instaciarLosDistintosUsuariosRoles();
            this.instanciarDistintosModelosDeHeladeras();
            this.instanciarHeladeras();
            //Test especifico para simular sensor de temperatura de las heladeras activas, y generé eventualmente una falla
            //this.instanciarTestParaBroker();
        });
    }

    private void instanciarTestParaBroker() {
        ServiceBroker serviceBroker = ServiceLocator.instanceOf(ServiceBroker.class);
        String topic1 = "dds2024/heladera/temperatura";
        Timer timer = new Timer();
        long delay = 0;
        long intervalPeriod = 10000;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                List<Heladera> heladeras = ServiceLocator.instanceOf(RepositorioHeladeras.class).obtenerTodasLasHeladerasActivas();
                Random random = new Random();
                for (Heladera heladera : heladeras) {
                    float min = -20f;
                    float max = 10f;
                    float temperature = min + (max - min) * random.nextFloat();
                    String msg = String.format(Locale.US, "{'id':%d,'temp':%.2f}", heladera.getId(), temperature);
                    serviceBroker.publishMessage(topic1, msg);
                }
            }
        };
        
        timer.scheduleAtFixedRate(task, delay, intervalPeriod);
    }

    private void instanciarHeladeras() {
        Calle calle1 = new Calle("Medrano","951");
        Barrio barrio1 = new Barrio("Almagro");
        Localidad localidad1 = new Localidad("Buenos Aires");
        Provincia provincia1 = new Provincia("Ciudad Autónoma de Buenos Aires");
        Float latitud1 = -34.59887f;
        Float longitud1 = -58.42015f;
        Ubicacion ubicacion1 = new Ubicacion(latitud1,longitud1,calle1,provincia1,localidad1,barrio1);
        ModeloDeHeladera modeloA = modelosHeladerasPorDefecto.get(0);
        String nombreHeladera1 = "Heladera Medrano UTN";
        Integer capacidadMax1 = 500;
        Heladera heladera1 = new Heladera(modeloA,nombreHeladera1,ubicacion1,capacidadMax1);
        heladera1.agregarCantViandas(200);

        Calle calle2 = new Calle("Virrey Cevallos","699");
        Barrio barrio2 = new Barrio("Monserrat");
        Localidad localidad2 = new Localidad("Buenos Aires");
        Provincia provincia2 = new Provincia("Ciudad Autónoma de Buenos Aires");
        Float latitud2 = -34.616733394911506f;
        Float longitud2 = -58.38906347757075f;
        Ubicacion ubicacion2 = new Ubicacion(latitud2,longitud2,calle2,provincia2,localidad2,barrio2);
        ModeloDeHeladera modeloB = modelosHeladerasPorDefecto.get(1);
        String nombreHeladera2 = "Heladera Cevallos";
        Integer capacidadMax2 = 500;
        Heladera heladera2 = new Heladera(modeloB,nombreHeladera2,ubicacion2,capacidadMax2);
        heladera2.agregarCantViandas(200);

        Calle calle3 = new Calle("San José","302");
        Barrio barrio3 = new Barrio("Monserrat");
        Localidad localidad3 = new Localidad("Buenos Aires");
        Provincia provincia3 = new Provincia("Ciudad Autónoma de Buenos Aires");
        Float latitud3 = -34.61230085978499f;
        Float longitud3 = -58.38616669183467f;
        Ubicacion ubicacion3 = new Ubicacion(latitud3,longitud3,calle3,provincia3,localidad3,barrio3);
        ModeloDeHeladera modeloC = modelosHeladerasPorDefecto.get(2);
        String nombreHeladera3 = "Heladera San jose";
        Integer capacidadMax3 = 500;
        Heladera heladera3 = new Heladera(modeloC,nombreHeladera3,ubicacion3,capacidadMax3);
        heladera3.agregarCantViandas(100);

        Calle calle4 = new Calle("Piedras","1102");
        Barrio barrio4 = new Barrio("San Telmo");
        Localidad localidad4 = new Localidad("Buenos Aires");
        Provincia provincia4 = new Provincia("Ciudad Autónoma de Buenos Aires");
        Float latitud4 = -34.620914065209135f;
        Float longitud4 = -58.37723493581508f;
        Ubicacion ubicacion4 = new Ubicacion(latitud4,longitud4,calle4,provincia4,localidad4,barrio4);
        ModeloDeHeladera modeloD = modelosHeladerasPorDefecto.get(3);
        String nombreHeladera4 = "Heladera Piedras";
        Integer capacidadMax4 = 600;
        Heladera heladera4 = new Heladera(modeloD,nombreHeladera4,ubicacion4,capacidadMax4);
        heladera4.agregarCantViandas(300);

        repositorio.guardar(heladera1);
        repositorio.guardar(heladera2);
        repositorio.guardar(heladera3);
        repositorio.guardar(heladera4);

        Colaborador colaboradorFisico = repositorioColaboradores.obtenerPorId(2L);
        Colaborador colaboradorJuridico = repositorioColaboradores.obtenerPorId(1L);
        MantenerHeladera mantenerHeladera = new MantenerHeladera(heladera1,colaboradorJuridico);
        mantenerHeladera.setMesesPuntarizados(5);
        mantenerHeladera.setPuntosOtorgados(5);

        MantenerHeladera mantenerHeladera2 = new MantenerHeladera(heladera2,colaboradorJuridico);
        mantenerHeladera2.setMesesPuntarizados(3);
        mantenerHeladera2.setPuntosOtorgados(3);

        colaboradorJuridico.sumarPuntos(5);
        colaboradorJuridico.sumarPuntos(3);

        mantenerHeladera.completar();
        mantenerHeladera2.completar();
        repositorio.guardar(mantenerHeladera);
        repositorio.guardar(mantenerHeladera2);

        TarjetaVulnerable tarjetaVulnerable = new TarjetaVulnerable();
        PersonaVulnerable personaVulnerable = PersonaVulnerable.create("kevin","durant",LocalDate.of(2001,5,24));
        tarjetaVulnerable.setVulnerable(personaVulnerable);
        RegistroDePersonaVulnerable registroDePersonaVulnerable = new RegistroDePersonaVulnerable(colaboradorFisico,tarjetaVulnerable,personaVulnerable);
        registroDePersonaVulnerable.setCantidad(2);
        registroDePersonaVulnerable.setPuntosOtorgados(10);
        colaboradorFisico.sumarPuntos(10);

        registroDePersonaVulnerable.completar();
        repositorio.guardar(registroDePersonaVulnerable);
    }

    private void instanciarDistintosModelosDeHeladeras() {
        ModeloDeHeladera modelo1 = new ModeloDeHeladera("MODELO A",-12f,22f);
        ModeloDeHeladera modelo2 = new ModeloDeHeladera("MODELO B",-2f,44f);
        ModeloDeHeladera modelo3 = new ModeloDeHeladera("MODELO C",2f,55f);
        ModeloDeHeladera modelo4 = new ModeloDeHeladera("MODELO D",10f,66f);

        repositorio.guardar(modelo1);
        repositorio.guardar(modelo2);
        repositorio.guardar(modelo3);
        repositorio.guardar(modelo4);

        //Esto lo hago para poder setear despues heladeras con esta misma instancia de los modelos, y evitar llamar a la base de datos para obtener la refenrencia
        modelosHeladerasPorDefecto.add(modelo1);
        modelosHeladerasPorDefecto.add(modelo2);
        modelosHeladerasPorDefecto.add(modelo3);
        modelosHeladerasPorDefecto.add(modelo4);
    }

    private void instaciarLosRoles(){
        Rol admin = new Rol(RoleENUM.ADMIN);
        Rol juridico = new Rol(RoleENUM.JURIDICO);
        Rol fisico = new Rol(RoleENUM.FISICO);
        Rol tecnico = new Rol(RoleENUM.TECNICO);

        repositorioRoles.guardar(admin);
        repositorioRoles.guardar(juridico);
        repositorioRoles.guardar(fisico);
        repositorioRoles.guardar(tecnico);
    }

    private void instaciarLosDistintosUsuariosRoles() {
        Usuario admin = new Usuario("admin", "admin");
        Usuario usuarioJuridico = new Usuario("juridico", "juridico");
        Usuario usuarioFisico = new Usuario("fisico", "fisico");
        Usuario usuarioTecnico = new Usuario("tecnico", "tecnico");
        Rol adminRol = repositorioRoles.buscarRolPorNombre(RoleENUM.ADMIN);
        Rol juridicoRol = repositorioRoles.buscarRolPorNombre(RoleENUM.JURIDICO);
        Rol fisicoRol = repositorioRoles.buscarRolPorNombre(RoleENUM.FISICO);
        Rol tecnicoRol = repositorioRoles.buscarRolPorNombre(RoleENUM.TECNICO);
        admin.agregarRol(adminRol);
        usuarioJuridico.agregarRol(juridicoRol);
        usuarioFisico.agregarRol(fisicoRol);
        usuarioTecnico.agregarRol(tecnicoRol);

        //Tecnico
        Documento documentoTecnico = new Documento(TipoDocumento.DNI, "12325678");
        Cuil cuilTecnico = new Cuil("20", "12145678", "4");
        Tecnico tecnico = new Tecnico("nombreTecnico", "apellidoTecnico", documentoTecnico, cuilTecnico);
        Provincia provinciaTecnico = new Provincia("Buenos Aires");
        Ubicacion ubicacionTecnico = new Ubicacion(-34.59935470126518f, -58.41951091983598f, new Calle("Medrano", "700")); // Coordenadas de Buenos Aires
        ubicacionTecnico.setProvincia(provinciaTecnico);
        AreaDeCobertura areaTecnico = new AreaDeCobertura(ubicacionTecnico, TamanioArea.GRANDE);


        Whatsapp whatsappTecnico = new Whatsapp("+5491123256789");
        Email emailTecnico = new Email("nahuellucana1999@gmail.com");
        Telegram telegramTecnico = new Telegram("tecnico1_telegram");
        telegramTecnico.setNumero("+5491123256789");
        tecnico.agregarMedioDeContacto(whatsappTecnico);
        tecnico.agregarMedioDeContacto(emailTecnico);
        tecnico.agregarMedioDeContacto(telegramTecnico);
        tecnico.setAreaDeCobertura(areaTecnico);
        tecnico.setUsuario(usuarioTecnico);
        Notificador notificador = new Notificador();
        notificador.habilitarNotificacion(tecnico, emailTecnico);

        //Fisico
        Localidad localidadFisico = new Localidad("Almafuerte");
        Barrio barrioFisico = new Barrio("Barrio Las Heras");
        Provincia provinciaFisico = new Provincia("Cordoba");
        Ubicacion ubicacionFisico = new Ubicacion(-67.6044723f, -43.3816322f, new Calle("av. carlos paz", "4567"));
        ubicacionFisico.setProvincia(provinciaFisico);
        ubicacionFisico.setLocalidad(localidadFisico);
        ubicacionFisico.setBarrio(barrioFisico);
        AreaDeCobertura areaFisico = new AreaDeCobertura(ubicacionFisico, TamanioArea.GRANDE);
        areaFisico.setProvincia(provinciaFisico);

        Whatsapp whatsappFisico = new Whatsapp("+5491159384624");
        Email emailFisico = new Email("colaborador1@example.com");
        Telegram telegramFisico = new Telegram("colab1-telegram");

        ColaboradorFisico colaboradorFisico = new ColaboradorFisico("Valentin", "Griggio");
        colaboradorFisico.agregarMedioDeContacto(whatsappFisico);
        colaboradorFisico.agregarMedioDeContacto(emailFisico);
        colaboradorFisico.agregarMedioDeContacto(telegramFisico);
        colaboradorFisico.setZona(areaFisico);
        colaboradorFisico.setUsuario(usuarioFisico);

        TarjetaColaborador tarjetaColaborador = TarjetaColaborador.of(colaboradorFisico);

        //Juridico
        Whatsapp whatsappJuridico = new Whatsapp("+5491165419940");
        Email emailJuridico = new Email("empresaX@example.com");
        Telegram telegramJuridico = new Telegram("empresa-telegram");

        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico("Electrodomesticos SA", TipoRazonSocial.EMPRESA, Rubro.COMERCIO);
        colaboradorJuridico.agregarMedioDeContacto(whatsappJuridico);
        colaboradorJuridico.agregarMedioDeContacto(emailJuridico);
        colaboradorJuridico.agregarMedioDeContacto(telegramJuridico);
        colaboradorJuridico.setUsuario(usuarioJuridico);

        Documento doc1 = new Documento(TipoDocumento.DNI,"40789234");
        Documento doc2 = new Documento(TipoDocumento.DNI,"32784211");

        colaboradorJuridico.setDocumento(doc1);
        colaboradorFisico.setDocumento(doc2);

        repositorioColaboradores.guardar(colaboradorJuridico);
        repositorioColaboradores.guardar(colaboradorFisico);
        repositorioTecnicos.guardar(tecnico);
        repositorioUsuarios.guardar(admin);
        repositorio.guardar(tarjetaColaborador);
    }
}