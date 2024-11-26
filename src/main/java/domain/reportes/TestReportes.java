package domain.reportes;

import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.repositoriosBDD.RepositorioHeladeras;
import repositorios.repositoriosBDD.RepositorioModeloHeladeras;
import repositorios.repositoriosBDD.RepositorioReportes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestReportes implements WithSimplePersistenceUnit {
    public RepositorioHeladeras repositorioHeladeras;
    public RepositorioReportes repositorioReportes;
    public RepositorioModeloHeladeras repositorioModeloHeladeras;

    public static void main(String[] args) {
        TestReportes instance = new TestReportes();
        instance.repositorioHeladeras = new RepositorioHeladeras();
        instance.repositorioReportes = new RepositorioReportes();
        instance.repositorioModeloHeladeras = new RepositorioModeloHeladeras();

        instance.testBDD();
    }

    public void testBDD() {
        withTransaction(()-> {
            persistenciaReporte();
        });
    }
    public void persistenciaReporte() {
        Heladera heladera = new Heladera();
        Heladera heladera1 = new Heladera();
        heladera1.setNombreIdentificador("Heladera Caballito");
        heladera.setNombreIdentificador("Heladera Almagro");
        ModeloDeHeladera modelo1 = new ModeloDeHeladera("K008", 2.0f, 8.0f);
        ModeloDeHeladera modelo2 = new ModeloDeHeladera("K009", 1.0f, 4.0f);
        repositorioModeloHeladeras.guardar(modelo1);
        repositorioModeloHeladeras.guardar(modelo2);
        heladera.setModelo(modelo1);
        heladera1.setModelo(modelo2);
        repositorioHeladeras.guardar(heladera);
        repositorioHeladeras.guardar(heladera1);

        ReporteFallas reporteFallas = new ReporteFallas();
        List<Heladera> heladeras = new ArrayList<>();
        heladeras.add(heladera);
        heladeras.add(heladera1);
        reporteFallas.setHeladeras(heladeras);
        reporteFallas.setFechaGeneracion(LocalDateTime.now());
        repositorioReportes.guardar(reporteFallas);

    }
}
