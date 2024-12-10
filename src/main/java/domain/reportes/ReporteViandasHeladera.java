package domain.reportes;

import domain.heladera.Heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.reportador.Reportador;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("reporte_viandas_por_heladera")
public class ReporteViandasHeladera extends Reporte {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reporte_viandas_por_heladera_id")
    private List<Heladera> heladeras;

    @Override
    public void reportar() {
        for (Heladera heladera : heladeras) {
            List<Map<String, String>> reporteViandas = reportador.generarReporteViandasPorHeladera(heladera);
            List<String> encabezadosViandasHeladera = Arrays.asList("Nombre Heladera", "Cantidad de viandas donadas");
            reportador.generarPDFReporte("reporte_viandas_por_heladera.pdf", reporteViandas, encabezadosViandasHeladera, "Reporte de Viandas por Heladera");

            // Aquí imprimimos los datos del reporte
            System.out.println("Reporte de cantidad de viandas donadas por heladera: ");
            for (Map<String, String> fila : reporteViandas) {
                System.out.println(fila);
            }
        }
    }
}


