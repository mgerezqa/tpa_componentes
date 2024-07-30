package main_tests.cron_asignador_de_tecnicos;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.formulario.documentos.Cuil;
import domain.formulario.documentos.Documento;
import domain.formulario.documentos.TipoDocumento;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.geografia.area.AreaDeCobertura;
import domain.geografia.area.TamanioArea;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.incidentes.Alerta;
import domain.incidentes.Incidente;
import domain.incidentes.IncidenteFactory;
import domain.mensajeria.EmailSender;
import domain.usuarios.Tecnico;
import utils.asignadorTecnicos.AsignadorDeTecnico;
import utils.notificador.Notificador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AsignadorDeTecnicosSetUp {

    public List<Tecnico> setUpTecnicos() {

        List<Tecnico> tecnicos = new ArrayList<>();

        Tecnico tecnicoA;
        Tecnico tecnicoB;

        Documento documentoA = new Documento(TipoDocumento.DNI,"27890523");
        Cuil cuilA = new Cuil("20",(documentoA.getNumeroDeDocumento()),"6");

        // Estadio Pedro Bidegain
        Ubicacion ubicacionA = new Ubicacion((-34.650832f), (-58.440309f), new Calle("Avenida ABC", "3526"));

        AreaDeCobertura areaA = new AreaDeCobertura(ubicacionA,TamanioArea.MEDIANA);
        MedioDeContacto emailA = new Email("bryanbattagliese@gmail.com");

        tecnicoA = new Tecnico("Roberto", "Martinez", documentoA, cuilA);
        tecnicoA.setAreaDeCobertura(areaA);
        tecnicoA.agregarMedioDeContacto(emailA);

        // ============================================================

        Documento documentoB = new Documento(TipoDocumento.DNI,"41855527");
        Cuil cuilB = new Cuil("20",(documentoA.getNumeroDeDocumento()),"1");

        // Parque las heras
        Ubicacion ubicacionB = new Ubicacion((-34.582835f), (-58.408439f), new Calle("Avenida DEFE", "1030"));

        AreaDeCobertura areaB = new AreaDeCobertura(ubicacionA,TamanioArea.MEDIANA);
        MedioDeContacto emailB = new Email("bryanbattagliese@gmail.com");

        tecnicoB = new Tecnico("Eduardo", "Lopez", documentoA, cuilA);
        tecnicoB.setAreaDeCobertura(areaB);
        tecnicoB.agregarMedioDeContacto(emailB);

        // ============================================================

        tecnicos.add(tecnicoA);
        tecnicos.add(tecnicoB);

        return tecnicos;
    }

    public List<Incidente> setUpIncidentes(){
        List<Incidente> incidentes = new ArrayList<>();

        Ubicacion ubiMedrano = new Ubicacion(-34.598546f, -58.420237f,new Calle("Av MEDRANO", "980"));
        Heladera heladeraMedrano = new Heladera(new ModeloDeHeladera("xt25"), "HeladeraMedrano",ubiMedrano);

        Ubicacion ubiCampus = new Ubicacion(-34.659866f, -58.468442f, new Calle("Mozart", "2300"));
        Heladera heladeraCampus = new Heladera(new ModeloDeHeladera("xt12"), "HeladeraCampus",ubiCampus);

        Alerta alertaTemperaturaMED = IncidenteFactory.crearAlerta(heladeraMedrano,"falla_temperatura");
        alertaTemperaturaMED.setId("01");

        Alerta alertaTemperaturaCAM = IncidenteFactory.crearAlerta(heladeraCampus,"falla_temperatura");
        alertaTemperaturaCAM.setId("02");

        incidentes.add(alertaTemperaturaMED);
        incidentes.add(alertaTemperaturaCAM);

        return incidentes;
    }

    public void ejecucion(String filePath) {

        List<Tecnico> tecnicos = new ArrayList<>();
        List<Incidente> incidentes = setUpIncidentes();
        AsignadorDeTecnico asignador = new AsignadorDeTecnico();
        EmailSender emailSender = EmailSender.getInstance();
        Notificador notificador = new Notificador();
        asignador.setTecnicos(tecnicos);
        asignador.setNotificador(notificador);

        Tecnico tecnicoA;
        Tecnico tecnicoB;

        Documento documentoA = new Documento(TipoDocumento.DNI,"27890523");
        Cuil cuilA = new Cuil("20",(documentoA.getNumeroDeDocumento()),"6");

        // Estadio Pedro Bidegain
        Ubicacion ubicacionA = new Ubicacion((-34.650832f), (-58.440309f), new Calle("Avenida ABC", "3526"));

        AreaDeCobertura areaA = new AreaDeCobertura(ubicacionA,TamanioArea.MEDIANA);
        MedioDeContacto emailA = new Email("bryanbattagliese@gmail.com");

        tecnicoA = new Tecnico("Roberto", "Martinez", documentoA, cuilA);
        tecnicoA.setAreaDeCobertura(areaA);
        tecnicoA.agregarMedioDeContacto(emailA);
        notificador.habilitarNotificacion(tecnicoA, emailA);

        // ============================================================

        Documento documentoB = new Documento(TipoDocumento.DNI,"41855527");
        Cuil cuilB = new Cuil("20",(documentoA.getNumeroDeDocumento()),"1");

        // Parque las heras
        Ubicacion ubicacionB = new Ubicacion((-34.582835f), (-58.408439f), new Calle("Avenida DEFE", "1030"));

        AreaDeCobertura areaB = new AreaDeCobertura(ubicacionA,TamanioArea.MEDIANA);
        MedioDeContacto emailB = new Email("bryanbattagliese@gmail.com");

        tecnicoB = new Tecnico("Eduardo", "Lopez", documentoA, cuilA);
        tecnicoB.setAreaDeCobertura(areaB);
        tecnicoB.agregarMedioDeContacto(emailB);
        notificador.habilitarNotificacion(tecnicoB, emailB);

        // ============================================================

        tecnicos.add(tecnicoA);
        tecnicos.add(tecnicoB);

        boolean hayIncidentesNuevos = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Fecha y hora actual: " + LocalDateTime.now());
            writer.newLine();
            writer.newLine();

            for (Incidente incidente : incidentes) {
                if (incidente.getTecnicoAsignado() == null) {
                    hayIncidentesNuevos = true;
                    asignador.asignarTecnicoA(incidente);
                    writer.write("Incidente: " + incidente.getId() + " asignado a técnico: " + incidente.getTecnicoAsignado().getNombre());
                    writer.newLine();
                }
            }

            if (!hayIncidentesNuevos) {
                writer.write("No hay incidentes nuevos.");
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
