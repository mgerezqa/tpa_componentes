package utils.cargaMasiva;

import com.opencsv.exceptions.CsvValidationException;
import config.ServiceLocator;
import domain.donaciones.Donacion;
import domain.usuarios.ColaboradorFisico;
import repositorios.repositoriosBDD.RepositorioColaboradores;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainExample {

    public static void main(String[] args) {
        //String archivoCSV = "src/main/resources/csv.csv";
        //List<ColaboradorFisico> colaboradores = new ArrayList<>();
        //ImportadorCSV importador = ServiceLocator.instanceOf(ImportadorCSV.class);
//
        //try {
        //    importador.importarDonaciones(archivoCSV, colaboradores);
        //    System.out.println("Donaciones importadas: " + donaciones.size());
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
    }

}

