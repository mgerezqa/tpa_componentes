package utils.reportador;

import domain.heladera.Heladera.Heladera;
import domain.reportes.Reporte;
import domain.usuarios.ColaboradorFisico;
import repositorios.repositoriosBDD.RepositorioReportes;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Reportador {

    private final ScheduledExecutorService scheduler;
    private RepositorioReportes repositorioReportes;

    public Reportador(RepositorioReportes repositorioReportes){
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.repositorioReportes = repositorioReportes;
    }

    public void iniciarReporteSemanal(Reporte reporte){
        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(
                reporte::reportar, //Para cada reporte correspondiente a la interfaz, ejecuta el método reportar
                0, //Esto significa que inicia inmediatamente
                7, //Esto indica la separación entre el inicio y la re-ejecución
                TimeUnit.DAYS);//Esto indica que la unidad de tiempo debe ser en DÍAS, lo que le da sentido al 0 y al 7
    }
    public void detenerReportesSemanales(){
        scheduler.shutdown();
    }

    public void guardarReporte(Reporte reporte){
        repositorioReportes.guardarReporte(reporte);
    }

    public Map<String, Integer> generarReporteFallasPorHeladera(Heladera heladera) {
        Map<String, Integer> reporteFallas = new HashMap<>();
        reporteFallas.put(heladera.getNombreIdentificador(), heladera.getIncidentes().size());
        return reporteFallas;
    }

    // Método para generar el reporte de cantidad de viandas retiradas/colocadas por heladera
    public Map<String, Integer> generarReporteViandasPorHeladera(Heladera heladera) {
        Map<String, Integer> reporteViandas = new HashMap<>();
        reporteViandas.put(heladera.getNombreIdentificador(), heladera.getCapacidadActual());
        return reporteViandas;
    }

    public Map<String, Integer> generarReporteViandasPorColaborador(ColaboradorFisico colaborador){
        Map<String, Integer> reporteViandasColaboradas = new HashMap<>();
        reporteViandasColaboradas.put(colaborador.getId(), colaborador.getViandasDonadas().size());
        return reporteViandasColaboradas;
    }
}
