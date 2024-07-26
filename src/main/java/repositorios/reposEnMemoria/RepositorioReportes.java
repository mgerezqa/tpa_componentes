package repositorios.reposEnMemoria;

import domain.reportes.Reporte;
import repositorios.interfaces.IRepositorioReportes;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioReportes implements IRepositorioReportes {
    private List<Reporte> reportes = new ArrayList<>();

    @Override
    public void guardarReporte(Reporte reporte){
        reportes.add(reporte);
    }

    @Override
    public List<Reporte> obtenerReportes(){
        return new ArrayList<>(reportes);
    }

    @Override
    public void eliminarReportesAntiguos(){
        LocalDateTime ahora = LocalDateTime.now();
        reportes = reportes.stream().filter(reporte -> ChronoUnit.DAYS.between(reporte.getFechaGeneracion(), ahora) <= 7).collect(Collectors.toList());
    }
}
