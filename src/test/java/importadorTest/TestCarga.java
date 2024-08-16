package importadorTest;

import com.opencsv.exceptions.CsvValidationException;
import domain.contribucionNuevo.Contribucion;
import domain.usuariosNuevo.ColaboradorFisico;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.importadorCsv.Importador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// requiere SMTPDummy: https://www.cylog.org/files/smtpd110.zip
public class TestCarga {
    List<ColaboradorFisico> listaColaboradoresFisicos;

    @BeforeEach
    public void init(){

    }

    @Test
    public void testCarga() throws CsvValidationException, IOException {
        Importador importador = new Importador();
        listaColaboradoresFisicos = new ArrayList<>();
        List<Contribucion> contribucionesImportadas = importador.importarColaboraciones("src/main/resources/migracion.csv", listaColaboradoresFisicos);

        Assertions.assertEquals(6, listaColaboradoresFisicos.size());
        Assertions.assertEquals(10, contribucionesImportadas.size());

        System.out.println("Lista colaboradores improvisada: ");
        System.out.println(listaColaboradoresFisicos.stream().map(ColaboradorFisico::toString).collect(Collectors.joining("\n")));
        System.out.println("Donaciones Migradas: ");
        System.out.println(contribucionesImportadas.stream().map(Contribucion::getTipoContribucion).collect(Collectors.toList()));
    }
}
