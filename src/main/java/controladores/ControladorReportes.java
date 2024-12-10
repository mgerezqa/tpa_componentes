package controladores;

import config.ServiceLocator;
import domain.heladera.Heladera.Heladera;
import domain.reportes.Reporte;
import domain.reportes.ReporteFallas;
import domain.reportes.ReporteViandasColaborador;
import domain.reportes.ReporteViandasHeladera;
import domain.usuarios.ColaboradorFisico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioHeladeras;
import repositorios.repositoriosBDD.RepositorioReportes;
import utils.ICrudViewsHandler;
import utils.reportador.Reportador;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorReportes implements ICrudViewsHandler, WithSimplePersistenceUnit {
    private RepositorioReportes repositorioReportes;
    private RepositorioHeladeras repositorioHeladeras;
    private RepositorioColaboradores repositorioColaboradores;
    private Reportador reportador;

    public ControladorReportes(RepositorioReportes repositorioReportes, RepositorioHeladeras repositorioHeladeras, RepositorioColaboradores repositorioColaboradores, Reportador reportador) {
        this.repositorioReportes = repositorioReportes;
        this.repositorioHeladeras = repositorioHeladeras;
        this.repositorioColaboradores = repositorioColaboradores;
        this.reportador = reportador;
    }

    @Override
    public void index(Context context) {
        List<Reporte> reportes = this.repositorioReportes.obtenerTodos();

        Map<String, Object> model = new HashMap<>();
        model.put("reportes", reportes);

        context.render("home/reportes/reportes.hbs", model);
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {
    }

    @Override
    public void remove(Context context) {
    }

    public void creacionAutomatica(){
        entityManager().clear();
        List<Heladera> heladeras = repositorioHeladeras.obtenerTodasLasHeladeras();
        List<ColaboradorFisico> colaboradoresFisicos = repositorioColaboradores.obtenerColaboradoresFisicos();

        ReporteFallas reporteFallas = new ReporteFallas();
        reporteFallas.setHeladeras(heladeras);
        reporteFallas.setReportador(reportador);
        reporteFallas.setFechaGeneracion(LocalDateTime.now());

        ReporteViandasColaborador reporteViandasColaborador = new ReporteViandasColaborador();
        reporteViandasColaborador.setColaboradores(colaboradoresFisicos);
        reporteViandasColaborador.setReportador(reportador);
        reporteViandasColaborador.setFechaGeneracion(LocalDateTime.now());


        ReporteViandasHeladera reporteViandasHeladera = new ReporteViandasHeladera();
        reporteViandasHeladera.setHeladeras(heladeras);
        reporteViandasHeladera.setReportador(reportador);
        reporteViandasHeladera.setFechaGeneracion(LocalDateTime.now());

        reportador.generarReporte(reporteFallas);
        reportador.generarReporte(reporteViandasColaborador);
        reportador.generarReporte(reporteViandasHeladera);

        repositorioReportes.withTransaction(()-> {
            repositorioReportes.guardar(reporteFallas);
            repositorioReportes.guardar(reporteViandasColaborador);
            repositorioReportes.guardar(reporteViandasHeladera);
        });
    }

    public void download(Context ctx) {
        String filename = ctx.pathParam("filename");
        Path filePath = Paths.get("reportes", filename);

        if (Files.exists(filePath)) {
            ctx.res().setContentType("application/pdf");
            ctx.res().setHeader("Content-Disposition", "attachment; filename=");
            try {
                Files.copy(filePath, ctx.res().getOutputStream());
                ctx.res().getOutputStream().flush();
            } catch (Exception e) {
                ctx.status(500).result("Error while downloading the file: " + e.getMessage());
            }
        } else {
            ctx.status(404).result("File not found");
        }
    }

}
