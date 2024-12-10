package utils.reportador;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import domain.reportes.Reporte;
import domain.reportes.ReporteFallas;
import domain.reportes.ReporteViandasHeladera;
import domain.usuarios.ColaboradorFisico;
import repositorios.repositoriosBDD.RepositorioReportes;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reportador {


    public Reportador(){
    }

    public void generarPDFReporte(Reporte reporte, String nombreArchivo, List<Map<String, String>> datos, List<String> encabezados, String titulo) {
        Path reportesDir = Paths.get("reportes");

        try {
            if (!Files.exists(reportesDir)) {
                Files.createDirectories(reportesDir);
            }

            Document document = new Document();
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString().replace("-", "");
            String rutaArchivo = Paths.get(reportesDir.toString(), nombreArchivo + uuidString + ".pdf").toString();

            try (FileOutputStream archivoOutput = new FileOutputStream(rutaArchivo)) {
                PdfWriter.getInstance(document, archivoOutput);
                document.open();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                document.add(new Paragraph("Reporte generado el: " + LocalDateTime.now().format(formatter)));

                // Agregar título del reporte
                Paragraph tituloReporte = new Paragraph(titulo);
                tituloReporte.setAlignment(Element.ALIGN_CENTER);
                document.add(tituloReporte);
                document.add(Paragraph.getInstance("\n"));

                PdfPTable table = new PdfPTable(encabezados.size());
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(10f);

                for (String encabezado : encabezados) {
                    PdfPCell cell = new PdfPCell(new Phrase(encabezado));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }

                for (Map<String, String> fila : datos) {
                    for (String encabezado : encabezados) {
                        table.addCell(fila.getOrDefault(encabezado, ""));
                    }
                }

                document.add(table);
                document.close();


                reporte.setPdf(rutaArchivo);
            } catch (DocumentException | IOException e) {
                Logger.getLogger(Reportador.class.getName()).log(Level.SEVERE, "Error al generar el PDF: " + e.getMessage(), e);
            }
        } catch (IOException e) {
            Logger.getLogger(Reportador.class.getName()).log(Level.SEVERE, "Error al crear el directorio de reportes: " + e.getMessage(), e);
        }
    }

    public void generarReporte(Reporte reporte) {
            reporte.reportar();
    }

    public List<Map<String, String>> generarReporteFallasPorHeladera(List<Heladera> heladeras) {
        List<Map<String, String>> reporteFallas = new ArrayList<>();
        for (Heladera heladera : heladeras) {
            Map<String, String> fila = new HashMap<>();
            fila.put("Nombre Heladera", heladera.getNombreIdentificador());
            fila.put("Cantidad de fallas", String.valueOf(heladera.getIncidentes().size()));
            reporteFallas.add(fila);
        }
        return reporteFallas;
    }

    public List<Map<String, String>> generarReporteViandasPorHeladera(Heladera heladera) {
        List<Map<String, String>> reporteViandas = new ArrayList<>();
        Map<String, String> fila = new HashMap<>();
        fila.put("Nombre Heladera", heladera.getNombreIdentificador());
        fila.put("Cantidad de viandas donadas", heladera.getCapacidadActual().toString());
        reporteViandas.add(fila);
        return reporteViandas;
    }

    public List<Map<String, String>> generarReporteViandasPorColaborador(ColaboradorFisico colaborador){
        List<Map<String, String>> reporteViandasColaboradas = new ArrayList<>();
        Map<String, String> fila = new HashMap<>();
        fila.put("Nombre Colaborador", colaborador.getNombre() + " " + colaborador.getApellido());
        fila.put("Cantidad de viandas donadas", String.valueOf(colaborador.getViandasDonadas().size()));
        reporteViandasColaboradas.add(fila);
        return reporteViandasColaboradas;
    }
}
