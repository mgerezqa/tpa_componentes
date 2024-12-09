package domain.reportes;

import domain.heladera.Heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.reportador.Reportador;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("reporte_viandas_por_heladera")
public class ReporteViandasHeladera extends Reporte{
    @Transient
    Map<String, Integer> reporteViandasPorHeladera = new HashMap<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reporte_viandas_por_heladera_id")
    private List<Heladera> heladeras;

    @Override
    public void reportar(){
        for (Heladera heladera : heladeras) {
            Map<String, Integer> reporteViandasHeladera = reportador.generarReporteViandasPorHeladera(heladera);


            for (Map.Entry<String, Integer> entry : reporteViandasHeladera.entrySet()) {
                // Si ya existe una falla para esa clave, sumamos las cantidades
                reporteViandasPorHeladera.merge(entry.getKey(), entry.getValue(), Integer::sum);
            }
        }

        String nombreArchivo = "ReporteViandas_TodasLasHeladeras.pdf";
        reportador.generarPDFReporte(nombreArchivo, reporteViandasPorHeladera);

        System.out.println("Reporte de cantidad de viandas por todas las heladeras generado.");
    }

    public String getTipo(){
        return "REPORTE_VIANDAS_HELADERA";
    }
}
