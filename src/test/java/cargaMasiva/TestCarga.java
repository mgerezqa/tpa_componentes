package cargaMasiva;

import com.opencsv.exceptions.CsvValidationException;
import domain.carga_masiva.MigracionColaboraciones;
import domain.contacto.Email;
import domain.contacto.Telefono;
import domain.donaciones.TipoContribucion;
import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.TipoDocumento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestCarga {
    ColaboradorFisico unColab;
    ColaboradorFisico unColab2;

    List<ColaboradorFisico> unaLista = new ArrayList<>();

    @BeforeEach
    public void init(){
        Formulario formColabFisico = new Formulario();
        // estos 6 campos son necesarios para registrar un colaborador fisico
        formColabFisico.agregarCampo(new Campo("nombre"));
        formColabFisico.agregarCampo(new Campo("apellido"));
        formColabFisico.agregarCampo(new Campo("tipo de documento"));
        formColabFisico.agregarCampo(new Campo("numero de documento"));
        formColabFisico.agregarCampo(new Campo("contacto"));
        formColabFisico.agregarCampo(new Campo("forma de contribucion"));
        // campos opcionales (si comentas los campos y respuestas aun funciona)
        formColabFisico.agregarCampo(new Campo("fecha de nacimiento"));
        formColabFisico.agregarCampo(new Campo("direccion"));

        formColabFisico.responderCampo("nombre", "pepe");
        formColabFisico.responderCampo("apellido", "argento");
        formColabFisico.responderCampo("tipo de documento", TipoDocumento.DNI);
        formColabFisico.responderCampo("numero de documento", 123456789);
        formColabFisico.responderCampo("contacto", new Telefono(54,11,1234567));
        formColabFisico.responderCampo("contacto", new Email("mailPersonal@fisico.com"));
        formColabFisico.responderCampo("forma de contribucion", TipoContribucion.DINERO);
        formColabFisico.responderCampo("forma de contribucion", TipoContribucion.VIANDA);
        formColabFisico.responderCampo("forma de contribucion", TipoContribucion.TARJETA);
        formColabFisico.responderCampo("direccion", "calle falsa 123");
        formColabFisico.responderCampo("fecha de nacimiento", LocalDate.parse("2000-10-20"));

        unColab = new ColaboradorFisico(formColabFisico);
        unColab2 = new ColaboradorFisico(formColabFisico);
    }

    @Test
    public void testCarga() throws CsvValidationException, IOException {
        unaLista.add(unColab);
        unaLista.add(unColab2);
        MigracionColaboraciones migrador = new MigracionColaboraciones();
        //FileReader csv = new FileReader("src/main/resources/test.csv");
        System.out.println("Lista colaboradores improvisada: ");
        System.out.println(unaLista);
        System.out.println("Donaciones Migradas: ");
        System.out.println(migrador.migrarColaboracion("src/main/resources/test.csv",unaLista));
        System.out.println("Lista colaboradores actualizada: ");
        System.out.println(unaLista);
    }
}
