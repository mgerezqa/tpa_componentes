package domain.reportes;

import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;
import utils.reportador.Reportador;

import java.time.LocalDateTime;
import java.util.Map;

public class ReporteFallas extends Reporte{

    @Getter @Setter
    private Heladera heladera;

    @Getter @Setter
    private LocalDateTime fecha;

    @Getter @Setter
    private String id;

    @Setter
    private Reportador reportador;

    public ReporteFallas(){
        this.heladera = heladera;
        this.fecha = fecha;
        this.id = id;
        this.reportador = reportador;
    }
    @Override
    public void reportar(){
        Map<String, Integer> reporteFallas = reportador.generarReporteFallasPorHeladera(heladera);
        System.out.println("Reporte de cantidad de fallas por heladera: ");
        reporteFallas.forEach((heladera, cantidad) -> System.out.println(heladera + ": " + cantidad));
    }




}
