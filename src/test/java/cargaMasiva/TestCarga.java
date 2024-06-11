package cargaMasiva;

import com.opencsv.exceptions.CsvValidationException;
import domain.carga_masiva.ImportacionColaboraciones;
import domain.contacto.Email;
import domain.contacto.Telefono;
import domain.donaciones.Contribucion;
import domain.donaciones.TipoContribucion;
import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.formulario.TipoCampoFormulario;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.TipoDocumento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestCarga {
    List<ColaboradorFisico> listaColaboradoresFisicos = new ArrayList<>();

    @BeforeEach
    public void init(){
        Formulario formColabFisico = new Formulario();
        // estos 6 campos son necesarios para registrar un colaborador fisico
        formColabFisico.agregarCampo(new Campo(TipoCampoFormulario.NOMBRE, "Ingrese su nombre"));
        formColabFisico.agregarCampo(new Campo(TipoCampoFormulario.APELLIDO, "Ingrese su apellido"));
        formColabFisico.agregarCampo(new Campo(TipoCampoFormulario.TIPO_DOCUMENTO, "Seleccione su tipo de documento"));
        formColabFisico.agregarCampo(new Campo(TipoCampoFormulario.NRO_DOCUMENTO, "Ingrese su numero de documento"));
        formColabFisico.agregarCampo(new Campo(TipoCampoFormulario.CONTACTO, "Ingrese almenos 1 forma de contacto"));
        formColabFisico.agregarCampo(new Campo(TipoCampoFormulario.FORMA_CONTRIBUCION, "Seleccione su forma de contribucion"));
        // campos opcionales
        formColabFisico.agregarCampo(new Campo(TipoCampoFormulario.FECHA_NACIMIENTO, "Ingrese su fecha de nacimiento"));
        formColabFisico.agregarCampo(new Campo(TipoCampoFormulario.DIRECCION, "Ingrese su direccion"));

        formColabFisico.responderCampo(TipoCampoFormulario.NOMBRE, "pepe");
        formColabFisico.responderCampo(TipoCampoFormulario.APELLIDO, "argento");
        formColabFisico.responderCampo(TipoCampoFormulario.TIPO_DOCUMENTO, TipoDocumento.DNI);
        formColabFisico.responderCampo(TipoCampoFormulario.NRO_DOCUMENTO, 123456789);
        formColabFisico.responderCampo(TipoCampoFormulario.CONTACTO, new Telefono(54,11,1234567));
        formColabFisico.responderCampo(TipoCampoFormulario.CONTACTO, new Email("mailPersonal@fisico.com"));
        formColabFisico.responderCampo(TipoCampoFormulario.FORMA_CONTRIBUCION, TipoContribucion.DINERO);
        formColabFisico.responderCampo(TipoCampoFormulario.FORMA_CONTRIBUCION, TipoContribucion.VIANDA);
        formColabFisico.responderCampo(TipoCampoFormulario.FORMA_CONTRIBUCION, TipoContribucion.TARJETA);
        formColabFisico.responderCampo(TipoCampoFormulario.DIRECCION, "calle falsa 123");
        formColabFisico.responderCampo(TipoCampoFormulario.FECHA_NACIMIENTO, LocalDate.parse("2000-10-20"));

        listaColaboradoresFisicos.add(new ColaboradorFisico(formColabFisico));
        listaColaboradoresFisicos.add(new ColaboradorFisico(formColabFisico));
        listaColaboradoresFisicos.add(new ColaboradorFisico(formColabFisico));
    }

    @Test
    public void testCarga() throws CsvValidationException, IOException {
        ImportacionColaboraciones importador = new ImportacionColaboraciones();
        List<ColaboradorFisico> colaboradoresImportados = new ArrayList<>();
        List<Contribucion> contribucionesImportadas = importador.importarColaboracion("src/main/resources/test.csv", listaColaboradoresFisicos, colaboradoresImportados);
        System.out.println("Lista colaboradores improvisada: ");
        System.out.println(listaColaboradoresFisicos);
        System.out.println("Donaciones Migradas: ");
        System.out.println(contribucionesImportadas);
        System.out.println("Lista colaboradores agregados: ");
        System.out.println(colaboradoresImportados);
    }
}
