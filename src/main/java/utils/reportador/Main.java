package utils.reportador;
import domain.donaciones.Vianda;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.FallaTecnica;
import domain.incidentes.Incidente;
import domain.usuarios.ColaboradorFisico;
import repositorios.repositoriosBDD.RepositorioReportes;

import java.util.Map;
import java.util.Arrays;
import java.util.List;
import domain.incidentes.Alerta;
import domain.incidentes.IncidenteFactory;


public class Main {
    public static void main(String[] args) throws Exception {
        // Instanciar el repositorio de reportes
        RepositorioReportes repositorioReportes = new RepositorioReportes();

        // Instanciar el reportador
        Reportador reportador = new Reportador(repositorioReportes);

        // Crear algunas heladeras con incidentes
        Heladera heladera1 = new Heladera();
        heladera1.setCapacidadMax(200);
        heladera1.setNombreIdentificador("Heladera medrano");
        Incidente incidente1 = new FallaTecnica(heladera1);
        Incidente incidente2 = new FallaTecnica(heladera1);
        heladera1.setIncidentes(Arrays.asList(incidente1, incidente2)); // 2 fallas

        Heladera heladera2 = new Heladera();
        heladera2.setCapacidadMax(150);
        heladera2.setNombreIdentificador("Heladera campus");
        Incidente incidente3 = new FallaTecnica(heladera2);
        heladera2.setIncidentes(Arrays.asList(incidente3)); // 1 falla

        // Crear algunos colaboradores con viandas donadas
        ColaboradorFisico colaborador1 = new ColaboradorFisico();
        colaborador1.setNombre("Juan");
        colaborador1.setApellido("Perez");
        Vianda vianda1 = new Vianda();
        Vianda vianda2 = new Vianda();
        colaborador1.setViandasDonadas(Arrays.asList(vianda1, vianda2)); // 2 viandas
        vianda1.ingresarViandaAHeladera(heladera1);
        vianda2.ingresarViandaAHeladera(heladera1);

        ColaboradorFisico colaborador2 = new ColaboradorFisico();
        colaborador2.setNombre("Ana");
        colaborador2.setApellido("Garcia");
        Vianda vianda3 = new Vianda();
        colaborador2.setViandasDonadas(Arrays.asList(vianda3)); // 1 vianda
        vianda3.ingresarViandaAHeladera(heladera2);

        // Generar reportes detallados
        List<Map<String, String>> datosFallasPorHeladera = reportador.generarReporteFallasPorHeladera(heladera1);
        datosFallasPorHeladera.addAll(reportador.generarReporteFallasPorHeladera(heladera2));
        List<String> encabezadosFallas = Arrays.asList("Nombre Heladera", "Descripcion", "Tipo de falla", "Fecha y Hora");
        reportador.generarPDFReporte("reporte_fallas_por_heladera.pdf", datosFallasPorHeladera, encabezadosFallas, "Reporte de Fallas por Heladera");

        List<Map<String, String>> datosViandasPorHeladera1 = reportador.generarReporteViandasPorHeladera(heladera1);
        List<Map<String, String>> datosViandasPorHeladera2 = reportador.generarReporteViandasPorHeladera(heladera2);
        List<String> encabezadosViandasHeladera = Arrays.asList("Nombre Heladera", "Cantidad de viandas donadas");
        datosViandasPorHeladera1.addAll(datosViandasPorHeladera2);
        reportador.generarPDFReporte("reporte_viandas_por_heladera.pdf", datosViandasPorHeladera1, encabezadosViandasHeladera, "Reporte de Viandas por Heladera");

        List<Map<String, String>> datosViandasPorColaborador1 = reportador.generarReporteViandasPorColaborador(colaborador1);
        List<Map<String, String>> datosViandasPorColaborador2 = reportador.generarReporteViandasPorColaborador(colaborador2);
        List<String> encabezadosViandasColaborador = Arrays.asList("Nombre Colaborador", "Cantidad de viandas donadas");
        datosViandasPorColaborador1.addAll(datosViandasPorColaborador2);
        reportador.generarPDFReporte("reporte_viandas_por_colaborador.pdf", datosViandasPorColaborador1, encabezadosViandasColaborador, "Reporte de Viandas por Colaborador");

        // Verificación de la salida
        System.out.println("Reportes generados en la carpeta 'reportes'");
    }
}
