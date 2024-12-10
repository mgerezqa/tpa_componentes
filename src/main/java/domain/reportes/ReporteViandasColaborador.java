package domain.reportes;

import domain.usuarios.ColaboradorFisico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.reportador.Reportador;

import domain.usuarios.Colaborador;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("reporte_viandas_por_colaborador")
public class ReporteViandasColaborador extends Reporte {
    @Transient
    Map<String, Integer> reporteViandasPorColaborador = new HashMap<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reporte_viandas_por_colaborador_id")
    private List<ColaboradorFisico>  colaboradores;

    @Override
    public void reportar() {
        for (ColaboradorFisico colaborador : colaboradores) {
            List<Map<String, String>> reporteViandasPorColaborador = reportador.generarReporteViandasPorColaborador(colaborador);
            List<String> encabezadosViandasColaborador = Arrays.asList("Nombre Colaborador", "Cantidad de viandas donadas");
            reportador.generarPDFReporte(this,"reporte_viandas_por_colaborador", reporteViandasPorColaborador, encabezadosViandasColaborador, "Reporte de Viandas por Colaborador");

            // Aquí imprimimos los datos del reporte
            System.out.println("Reporte de cantidad de viandas donadas por colaborador: ");
            for (Map<String, String> fila : reporteViandasPorColaborador) {
                System.out.println(fila);
            }
        }
    }
    public String getTipo(){
        return "REPORTE_VIANDAS_COLABORADOR";
    }
}
