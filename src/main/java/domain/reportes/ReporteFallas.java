package domain.reportes;

import domain.heladera.Heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.reportador.Reportador;

import java.util.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("reporte_fallas_por_heladera")
public class ReporteFallas extends Reporte{

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reporte_fallas_por_heladera_id")
    private List<Heladera> heladeras;
    @Transient
    Map<String, Integer> reporteFallasTotales = new HashMap<>();

    @Override
    public void reportar() {
        for (Heladera heladera : heladeras) {
            List<Map<String, String>> reporteFallas = reportador.generarReporteFallasPorHeladera(heladeras);
            List<String> encabezadosFallas = Arrays.asList("Nombre Heladera", "Cantidad de fallas");
            reportador.generarPDFReporte(this,"reporte_fallas_por_heladera", reporteFallas, encabezadosFallas, "Reporte de Fallas por Heladera");

            // Aquí imprimimos los datos del reporte
            System.out.println("Reporte de cantidad de fallas por heladera: ");
            for (Map<String, String> fila : reporteFallas) {
                System.out.println(fila);
            }
        }
    }

    @Override
    public String getTipo() {
        return "REPORTE_FALLA";
    }

}
