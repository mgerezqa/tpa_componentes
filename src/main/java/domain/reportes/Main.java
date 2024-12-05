package domain.reportes;

import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.reportes.ReporteFallas;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioHeladeras;
import repositorios.repositoriosBDD.RepositorioReportes;
import utils.reportador.Reportador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        RepositorioReportes repositorioReportes = new RepositorioReportes();
        // Crear un Reportador
        Reportador reportador = new Reportador(repositorioReportes);

        RepositorioHeladeras repositorioHeladeras = new RepositorioHeladeras();
        RepositorioColaboradores repositorioColaboradores = new RepositorioColaboradores();

        List<Heladera> heladeras = repositorioHeladeras.obtenerTodasLasHeladeras();
        System.out.println("Heladeras: " + heladeras.size()); // Para verificar que las heladeras se están obteniendo correctamente
        List<ColaboradorFisico> colaboradoresFisicos = repositorioColaboradores.obtenerColaboradoresFisicos();
        System.out.println("Colaboradores: " + colaboradoresFisicos.size()); // Para verificar que las heladeras se están obteniendo correctamente
        // Crear reporte de fallas
        ReporteFallas reporteFallas = new ReporteFallas();
        reporteFallas.setHeladeras(heladeras);
        reporteFallas.setReportador(reportador);

        ReporteViandasColaborador reporteViandasColaborador = new ReporteViandasColaborador();
        reporteViandasColaborador.setColaboradores(colaboradoresFisicos);
        reporteViandasColaborador.setReportador(reportador);

        ReporteViandasHeladera reporteViandasHeladera = new ReporteViandasHeladera();
        reporteViandasHeladera.setHeladeras(heladeras);
        reporteViandasHeladera.setReportador(reportador);

        // Iniciar reportes semanales (cada 7 segundos para pruebas)

        reportador.iniciarReporteSemanal(reporteFallas);
        reportador.iniciarReporteSemanal(reporteViandasColaborador);
        reportador.iniciarReporteSemanal(reporteViandasHeladera);

        // Mantener el programa corriendo para observar la generación de reportes
        try {
            Thread.sleep(30000); // Mantén el programa activo durante 30 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Detener el scheduler después de la prueba
        reportador.detenerReportesSemanales();
    }
}