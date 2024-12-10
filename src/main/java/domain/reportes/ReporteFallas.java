package domain.reportes;

import domain.heladera.Heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import javax.persistence.*;

import java.util.Map;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("reporte_fallas_por_heladera")
public class ReporteFallas extends Reporte {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reporte_fallas_por_heladera_id")
    private List<Heladera> heladeras;

    @Override
    public void reportar() {
        for (Heladera heladera : heladeras) {
            List<Map<String, String>> reporteFallas = reportador.generarReporteFallasPorHeladera(heladera);
            List<String> encabezadosFallas = Arrays.asList("Nombre Heladera", "Descripcion", "Tipo de falla", "Fecha y Hora");
            reportador.generarPDFReporte("reporte_fallas_por_heladera.pdf", reporteFallas, encabezadosFallas, "Reporte de Fallas por Heladera");

            // Aquí imprimimos los datos del reporte
            System.out.println("Reporte de cantidad de fallas por heladera: ");
            for (Map<String, String> fila : reporteFallas) {
                System.out.println(fila);
            }
        }
    }
}


