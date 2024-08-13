package utils.importadorCsv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

/* requiere dependencia (ya incluida en pom)
    <dependency>
        <groupId>com.opencsv</groupId>
        <artifactId>opencsv</artifactId>
        <version>5.9</version>
    </dependency>
*/

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorCSV {

    public List<String[]> obtenerListaDeLecturas(String ubicacionCSV) throws CsvValidationException, IOException {
        List<String[]> listaRespuesta = new ArrayList<>();
        CSVReader lectorCSV = new CSVReader(new FileReader(ubicacionCSV));
        String[] lecturaFila;
        while((lecturaFila = lectorCSV.readNext()) != null){
            listaRespuesta.add(lecturaFila);
        }
        lectorCSV.close();
        return listaRespuesta;
    }
}
