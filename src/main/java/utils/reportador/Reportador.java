package utils.reportador;

import domain.heladera.Heladera.Heladera;
import domain.reportes.Reporte;
import domain.reportes.ReporteFallas;
import domain.reportes.ReporteViandasHeladera;
import domain.usuarios.ColaboradorFisico;
import repositorios.repositoriosBDD.RepositorioReportes;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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

    public void generarPDFReporte(String nombreArchivo, Map<String, Integer> datos) {
        Path reportesDir = Paths.get("reportes");

        // Crear el directorio "reportes" si no existe
        try {
            if (!Files.exists(reportesDir)) {
                Files.createDirectories(reportesDir);
            }

            Document document = new Document();
            // Definir la ruta completa del archivo
            String rutaArchivo = Paths.get(reportesDir.toString(), nombreArchivo).toString();

            // Utilizar try-with-resources para asegurar que se cierre el FileOutputStream
            try (FileOutputStream archivoOutput = new FileOutputStream(rutaArchivo)) {
                PdfWriter.getInstance(document, archivoOutput);
                document.open();
                document.add(new Paragraph("Reporte generado el: " + LocalDateTime.now()));

                // Añadir los datos al reporte
                for (Map.Entry<String, Integer> entrada : datos.entrySet()) {
                    document.add(new Paragraph(entrada.getKey() + ": " + entrada.getValue()));
                }
                document.close(); // Cerrar el documento
            } catch (DocumentException | IOException e) {
                // Manejo de excepciones de manera más robusta
                System.err.println("Error al generar el PDF: " + e.getMessage());
                e.printStackTrace(); // O usa un logger
            }
        } catch (IOException e) {
            // Si hay un error al crear el directorio
            System.err.println("Error al crear el directorio de reportes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void iniciarReporteSemanal(Reporte reporte) {
        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(() -> {
            reporte.reportar();
            guardarReporte(reporte);
        }, 0, 7, TimeUnit.SECONDS);
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
