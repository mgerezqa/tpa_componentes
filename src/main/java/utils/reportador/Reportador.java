package utils.reportador;

import domain.heladera.Heladera.Heladera;
import domain.reportes.Reporte;
import domain.usuarios.ColaboradorFisico;
import repositorios.repositoriosBDD.RepositorioReportes;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Paths;
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
    //create method "generarReportePDF" whhere you create a PIDF file with the information of the report
    public void generarReportePDF(Reporte reporte) {
        String pdfDirectory = "path/to/pdf/directory"; // Set the directory path
        String pdfFileName = "reporte_" + reporte.getId() + ".pdf";
        String pdfFilePath = Paths.get(pdfDirectory, pdfFileName).toString();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
            document.open();
            document.add(new Paragraph("Reporte de Incidentes"));
            document.add(new Paragraph("ID del Reporte: " + reporte.getId()));
            document.add(new Paragraph("Fecha: " + reporte.getFechaGeneracion()));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
    public void iniciarReporteSemanal(Reporte reporte) {
        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(() -> {
            reporte.reportar();
            generarReportePDF(reporte);
            guardarReporte(reporte);
        }, 0, 7, TimeUnit.DAYS);
    }

    public void detenerReportesSemanales(){
        scheduler.shutdown();
    }

    public void guardarReporte(Reporte reporte){
        repositorioReportes.guardar(reporte);
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
        reporteViandasColaboradas.put(String.valueOf(colaborador.getId()), colaborador.getViandasDonadas().size());
        return reporteViandasColaboradas;
    }
}
