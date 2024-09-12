package main_tests;

import domain.contacto.Email;
import domain.contacto.Telegram;
import domain.contacto.Whatsapp;
import domain.donaciones.*;
import domain.formulario.documentos.Cuil;
import domain.formulario.documentos.Documento;
import domain.formulario.documentos.TipoDocumento;
import domain.geografia.*;
import domain.geografia.area.AreaDeCobertura;
import domain.geografia.area.TamanioArea;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.heladera.Sensores.SensorTemperatura;
import domain.incidentes.Alerta;
import domain.incidentes.FallaTecnica;
import domain.incidentes.Incidente;
import domain.incidentes.IncidenteFactory;
import domain.persona.PersonaVulnerable;
import domain.suscripciones.Suscripcion;
import domain.suscripciones.SuscripcionPorCantidadDeViandasDisponibles;
import domain.suscripciones.TipoDeSuscripcion;
import domain.tarjeta.Tarjeta;
import domain.usuarios.*;
import domain.visitas.Visita;
import dtos.FallaTecnicaDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.repositoriosBDD.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MainExamplePersistence implements WithSimplePersistenceUnit {

    public RepositorioTecnicos repositorioTecnicos;
    public RepositorioMediosDeContacto repositorioMediosDeContacto;
    public RepositorioIncidentes repositorioIncidentes;
    public RepositorioHeladeras repositorioHeladeras;
    public RepositorioUbicaciones repositorioUbicaciones;
    public RepositorioUsuarios repositorioUsuarios;
    public RepositorioVisitasTecnicas repositorioVisitasTecnicas;
    public RepositorioColaboradores repositorioColaboradores;
    public RepositorioDonacionesDinero repositorioDonacionesDinero;
    public RepositorioViandas repositorioViandas;
    public RepositorioDistribuciones repositorioDistribuciones;
    public RepositorioMantenciones repositorioMantenciones;
    public RepositorioVulnerables repositorioVulnerables;
    public RepositorioTarjetas repositorioTarjetas;
    public RepositorioRegistros repositorioRegistros;
    public RepositorioSuscripciones repositorioSuscripciones;


    public static void main(String[] args){
        MainExamplePersistence instance = new MainExamplePersistence();
        instance.repositorioTecnicos = new RepositorioTecnicos();
        instance.repositorioMediosDeContacto = new RepositorioMediosDeContacto();
        instance.repositorioHeladeras = new RepositorioHeladeras();
        instance.repositorioIncidentes = new RepositorioIncidentes();
        instance.repositorioUbicaciones = new RepositorioUbicaciones();
        instance.repositorioUsuarios = new RepositorioUsuarios();
        instance.repositorioVisitasTecnicas = new RepositorioVisitasTecnicas();
        instance.repositorioColaboradores = new RepositorioColaboradores();
        instance.repositorioDonacionesDinero = new RepositorioDonacionesDinero();
        instance.repositorioViandas = new RepositorioViandas();
        instance.repositorioDistribuciones = new RepositorioDistribuciones();
        instance.repositorioMantenciones = new RepositorioMantenciones();
        instance.repositorioVulnerables = new RepositorioVulnerables();
        instance.repositorioTarjetas = new RepositorioTarjetas();
        instance.repositorioRegistros = new RepositorioRegistros();
        instance.repositorioSuscripciones = new RepositorioSuscripciones();

        instance.testBDD();

    }

    public void testBDD(){
        withTransaction(()-> {
            persistenceUnite1();
            persistenceUnite2();
        });
    }

    public void persistenceUnite1(){
        // TECNICOS:

        Documento docTecnico1 = new Documento(TipoDocumento.DNI, "12325678");
        Cuil cuilTecnico1 = new Cuil("20", "12145678", "4");

        Documento docTecnico2 = new Documento(TipoDocumento.DNI, "87654221");
        Cuil cuilTecnico2 = new Cuil("20", "87354321", "7");

        Provincia provincia1 = new Provincia("Buenos Aires");
        Ubicacion ubicacion1 = new Ubicacion(-34.6044723f, -58.3816322f, new Calle("rivadavia", "14345")); // Coordenadas de Buenos Aires
        ubicacion1.setProvincia(provincia1);
        AreaDeCobertura area1 = new AreaDeCobertura(ubicacion1, TamanioArea.GRANDE);

        Provincia provincia2 = new Provincia("Córdoba");
        Ubicacion ubicacion2 = new Ubicacion(-31.42083f, -64.142376f, new Calle("santos", "12248")); // Coordenadas de Córdoba
        ubicacion2.setProvincia(provincia2);
        AreaDeCobertura area2 = new AreaDeCobertura(ubicacion2, TamanioArea.MEDIANA);

        Whatsapp whatsapp1 = new Whatsapp("+5491123256789");
        Email email1 = new Email("tecnico1@example.com");
        Telegram telegram1 = new Telegram("tecnico1_telegram");

        Whatsapp whatsapp2 = new Whatsapp("+5491162432109");
        Email email2 = new Email("tecnico2@example.com");
        Telegram telegram2 = new Telegram("tecnico2_telegram");

        Tecnico tecnico1 = new Tecnico("Bryan", "Fernandez", docTecnico1, cuilTecnico1);
        tecnico1.agregarMedioDeContacto(whatsapp1);
        tecnico1.agregarMedioDeContacto(email1);
        tecnico1.agregarMedioDeContacto(telegram1);
        tecnico1.setAreaDeCobertura(area1);
        repositorioTecnicos.guardar(tecnico1);


        Tecnico tecnico2 = new Tecnico("Roberto", "Perez", docTecnico2, cuilTecnico2);
        tecnico2.agregarMedioDeContacto(whatsapp2);
        tecnico2.agregarMedioDeContacto(email2);
        tecnico2.agregarMedioDeContacto(telegram2);
        tecnico2.setAreaDeCobertura(area2);
        repositorioTecnicos.guardar(tecnico2);

        // HELADERAS:

        Ubicacion ubicacionA = new Ubicacion(11234f, 6456f, new Calle("Av. Medrano", "6742"));
        ubicacionA.setProvincia(provincia1);
        Ubicacion ubicacionB = new Ubicacion(6512f, 5736f, new Calle("Av. Rivadavia", "13106"));
        ubicacionA.setProvincia(provincia1);
        Ubicacion ubicacionC = new Ubicacion(9153f, 1258f, new Calle("Juarez", "352"));
        ubicacionA.setProvincia(provincia2);

        ModeloDeHeladera modelo = new ModeloDeHeladera("XSS-283");
        modelo.setTemperaturaMaxima(22f);
        modelo.setTemperaturaMinima(-12f);

        Heladera heladeraA = new Heladera(modelo, "medrano", ubicacionA);
        Heladera heladeraB = new Heladera(modelo, "rivadavia", ubicacionB);
        Heladera heladeraC = new Heladera(modelo, "juarez", ubicacionC);

        SensorTemperatura sensorTemperaturaA = new SensorTemperatura(heladeraA);
        heladeraA.setSensorTemperatura(sensorTemperaturaA);
        repositorioHeladeras.guardar(heladeraA);
        SensorTemperatura sensorTemperaturaB = new SensorTemperatura(heladeraB);
        heladeraB.setSensorTemperatura(sensorTemperaturaB);
        repositorioHeladeras.guardar(heladeraB);
        SensorTemperatura sensorTemperaturaC = new SensorTemperatura(heladeraC);
        heladeraC.setSensorTemperatura(sensorTemperaturaC);
        repositorioHeladeras.guardar(heladeraC);

        // INCIDENTES:

        Usuario userA = new Usuario();
        userA.setNombreUsuario("bryan19");
        userA.setContrasenia("dfgaisu-Dgauiw1234");

        Incidente incidenteA = IncidenteFactory.crearAlerta(heladeraA, "falla_temperatura");
        incidenteA.setTecnicoAsignado(tecnico1);
        repositorioIncidentes.guardar(incidenteA);

        Incidente incidenteB = IncidenteFactory.crearAlerta(heladeraC, "falla_fraude");
        incidenteB.setTecnicoAsignado(tecnico2);
        repositorioIncidentes.guardar(incidenteB);

        FallaTecnicaDTO dto = new FallaTecnicaDTO();
        dto.setDescripcion("cuando fui a la heladera me encontre con esta falla . . .");
        dto.setFoto("https//:foto.png");

        Incidente incidenteC = IncidenteFactory.crearFallaTecnica(dto, heladeraB, userA);
        incidenteC.setTecnicoAsignado(tecnico2);
        repositorioIncidentes.guardar(incidenteC);

        // VISITA TECNICA

        Visita visita = new Visita();
        visita.setTecnico(tecnico1);
        visita.setFechaVisita(LocalDateTime.now());
        visita.setHeladera(heladeraA);
        visita.setFoto("https//:imagen.png");
        visita.setComentario("solucione el tema");
        repositorioVisitasTecnicas.guardar(visita);
    }

    public void persistenceUnite2(){

        //COLABORADORES
        Localidad localidad1 = new Localidad();
        localidad1.setLocalidad("Almafuerte");
        Barrio barrio1 = new Barrio();
        barrio1.setBarrio("Barrio Las Heras");
        Provincia provincia1 = new Provincia("Cordoba");
        Ubicacion ubicacion1 = new Ubicacion(-67.6044723f, -43.3816322f, new Calle("av. carlos paz", "4567"));
        ubicacion1.setProvincia(provincia1);
        ubicacion1.setLocalidad(localidad1);
        ubicacion1.setBarrio(barrio1);
        AreaDeCobertura area1 = new AreaDeCobertura(ubicacion1, TamanioArea.GRANDE);
        area1.setProvincia(provincia1);

        Whatsapp whatsapp1 = new Whatsapp("+5491159384624");
        Email email1 = new Email("colaborador1@example.com");
        Telegram telegram1 = new Telegram("colab1-telegram");

        ColaboradorFisico colaborador1 = new ColaboradorFisico("Valentin", "Griggio");
        colaborador1.agregarMedioDeContacto(whatsapp1);
        colaborador1.agregarMedioDeContacto(email1);
        colaborador1.agregarMedioDeContacto(telegram1);
        colaborador1.setZona(area1);
        repositorioColaboradores.guardar(colaborador1);

        Whatsapp whatsapp2 = new Whatsapp("+5491165419940");
        Email email2 = new Email("empresaX@example.com");
        Telegram telegram2 = new Telegram("empresa-telegram");

        ColaboradorJuridico colaborador2 = new ColaboradorJuridico("Electrodomesticos SA", TipoRazonSocial.EMPRESA, Rubro.COMERCIO);
        colaborador2.agregarMedioDeContacto(whatsapp2);
        colaborador2.agregarMedioDeContacto(email2);
        colaborador2.agregarMedioDeContacto(telegram2);
        repositorioColaboradores.guardar(colaborador2);

        //DONACIONES

        Dinero donacionDeDinero = new Dinero(500000, FrecuenciaDeDonacion.FRECUENCIA_MENSUAL, LocalDate.now(), colaborador2);
        repositorioDonacionesDinero.guardar(donacionDeDinero);

        ModeloDeHeladera modelo1 = new ModeloDeHeladera("XSZ-283");
        modelo1.setTemperaturaMaxima(22f);
        modelo1.setTemperaturaMinima(-12f);

        Heladera heladerita = new Heladera(modelo1, "ardilla", ubicacion1 );
        repositorioHeladeras.guardar(heladerita);

        Vianda vianda = new Vianda(LocalDate.now(), LocalDate.now(), colaborador1);
        vianda.setHeladeraActual(heladerita);
        repositorioViandas.guardar(vianda);

        colaborador1.agregarVianda(vianda);

        Barrio barrio2 = new Barrio("Iris");
        Provincia provincia2 = new Provincia("Tucuman");
        Ubicacion ubicacion2 = new Ubicacion(-190.6044723f, -400.3816322f, new Calle("La Rioja", "6941"));
        ubicacion2.setProvincia(provincia2);
        ubicacion2.setBarrio(barrio2);
        AreaDeCobertura area2 = new AreaDeCobertura(ubicacion2, TamanioArea.PEQUENA);
        area2.setProvincia(provincia2);
        colaborador1.setZona(area2);
        area2.agregarBarrio(barrio2);

        Heladera heladerita2 = new Heladera(modelo1, "huron", ubicacion2 );
        repositorioHeladeras.guardar(heladerita2);

        Distribuir distribucionDeViandas = new Distribuir(heladerita, heladerita2, 6, LocalDate.now(), colaborador1);
        distribucionDeViandas.setMotivo(Motivo.FALTA_DE_VIANDAS);
        repositorioDistribuciones.guardar(distribucionDeViandas);

        MantenerHeladera mantencion = new MantenerHeladera(heladerita2, LocalDate.now(), colaborador2);
        repositorioMantenciones.guardar(mantencion);

        //TARJETAS, PERSONAS VULNERABLES
        PersonaVulnerable joaquin = new PersonaVulnerable("Joaquin", LocalDate.of(1987, 2, 6));
        joaquin.setDocumento(new Documento(TipoDocumento.DNI, "27876543"));
        repositorioVulnerables.guardar(joaquin);

        Tarjeta tarjeta = new Tarjeta();

        RegistroDePersonaVulnerable registro = new RegistroDePersonaVulnerable(colaborador1, tarjeta, joaquin, LocalDate.now());
        repositorioRegistros.guardar(registro);

        //SUSCRIPCIONES

        SuscripcionPorCantidadDeViandasDisponibles suscripcionPorViandas = new SuscripcionPorCantidadDeViandasDisponibles();
        Suscripcion suscripcion = new Suscripcion(heladerita2, colaborador1, suscripcionPorViandas);
        repositorioSuscripciones.guardar(suscripcion);
        suscripcion.getEventManager().notifyObservers();

















    }
}
