package repositorios.interfaces;

import domain.reportes.Reporte;

import java.util.List;

public interface IRepositorioReportes {
    void guardarReporte(Reporte reporte);
    List<Reporte> obtenerReportes();
    void eliminarReportesAntiguos();
}
