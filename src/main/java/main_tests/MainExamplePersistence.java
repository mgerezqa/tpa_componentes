package main_tests;

import domain.contacto.Email;
import domain.contacto.Telegram;
import domain.contacto.Whatsapp;
import domain.formulario.documentos.Cuil;
import domain.formulario.documentos.Documento;
import domain.formulario.documentos.TipoDocumento;
import domain.geografia.Calle;
import domain.geografia.Provincia;
import domain.geografia.Ubicacion;
import domain.geografia.area.AreaDeCobertura;
import domain.geografia.area.TamanioArea;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.heladera.Sensores.SensorTemperatura;
import domain.incidentes.Alerta;
import domain.incidentes.FallaTecnica;
import domain.incidentes.Incidente;
import domain.incidentes.IncidenteFactory;
import domain.usuarios.Tecnico;
import domain.usuarios.Usuario;
import domain.visitas.Visita;
import dtos.FallaTecnicaDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.repositoriosBDD.*;

import java.time.LocalDateTime;

public class MainExamplePersistence implements WithSimplePersistenceUnit {

    public RepositorioTecnicos repositorioTecnicos;
    public RepositorioMediosDeContacto repositorioMediosDeContacto;
    public RepositorioIncidentes repositorioIncidentes;
    public RepositorioHeladeras repositorioHeladeras;
    public RepositorioUbicaciones repositorioUbicaciones;
    public RepositorioUsuarios repositorioUsuarios;
    public RepositorioVisitasTecnicas repositorioVisitasTecnicas;


    public static void main(String[] args){
        MainExamplePersistence instance = new MainExamplePersistence();
        instance.repositorioTecnicos = new RepositorioTecnicos();
        instance.repositorioMediosDeContacto = new RepositorioMediosDeContacto();
        instance.repositorioHeladeras = new RepositorioHeladeras();
        instance.repositorioIncidentes = new RepositorioIncidentes();
        instance.repositorioUbicaciones = new RepositorioUbicaciones();
        instance.repositorioUsuarios = new RepositorioUsuarios();
        instance.repositorioVisitasTecnicas = new RepositorioVisitasTecnicas();

        instance.testBDD();

    }

    public void testBDD(){
        withTransaction(()-> {

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



        });
    }
}
