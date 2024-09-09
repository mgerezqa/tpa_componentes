package domain.reportes;

import domain.usuarios.ColaboradorFisico;
import lombok.Getter;
import lombok.Setter;
import utils.reportador.Reportador;

import domain.usuarios.Colaborador;

import java.time.LocalDateTime;
import java.util.Map;

public class ReporteViandasColaborador extends Reporte {

    @Getter
    @Setter
    private ColaboradorFisico colaborador;
    @Getter
    @Setter
    private LocalDateTime fecha;
    @Getter
    @Setter
    private String id;
    @Setter
    private Reportador reportador;

    public ReporteViandasColaborador() {
        this.colaborador = colaborador;
        this.id = id;
        this.fecha = fecha;
        this.reportador = reportador;
    }

    @Override
    public void reportar() {
        Map<Long, Integer> reporteViandasPorColaborador = reportador.generarReporteViandasPorColaborador(colaborador);
        System.out.println("Reporte de cantidad de viandas donadas por colaborador: ");
        reporteViandasPorColaborador.forEach((colaborador, cantidad) -> System.out.println(colaborador + ": " + cantidad));
    }





}
