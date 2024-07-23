package domain.reportes;

import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;
import utils.reportador.Reportador;

import java.time.LocalDateTime;
import java.util.Map;

public class ReporteViandasHeladera extends Reporte{
    @Getter @Setter
    private Heladera heladera;
    @Getter @Setter
    private LocalDateTime fecha;
    @Getter @Setter
    private String id;
    @Setter
    private Reportador reportador;

    public ReporteViandasHeladera() {
        this.heladera = heladera;
        this.fecha = fecha;
        this.id = id;
        this.reportador = reportador;
    }
    @Override
    public void reportar(){
        Map<String, Integer> reporteViandas = reportador.generarReporteViandasPorHeladera(heladera);
        System.out.println("\nReporte de cantidad de viandas retiradas/colocadas por heladera:");
        reporteViandas.forEach((heladera, cantidad) -> System.out.println(heladera + ": " + cantidad));
    }

}
