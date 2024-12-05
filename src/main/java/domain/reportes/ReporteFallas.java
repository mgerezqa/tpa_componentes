package domain.reportes;

import domain.heladera.Heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.reportador.Reportador;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;
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


        // Recorrer todas las heladeras y acumular las fallas
        for (Heladera heladera : heladeras) {
            Map<String, Integer> reporteFallas = reportador.generarReporteFallasPorHeladera(heladera);

            // Agregar los datos de cada heladera al reporte total
            for (Map.Entry<String, Integer> entry : reporteFallas.entrySet()) {
                // Si ya existe una falla para esa clave, sumamos las cantidades
                reporteFallasTotales.merge(entry.getKey(), entry.getValue(), Integer::sum);
            }
        }

        // Crear un único archivo PDF con todos los datos
        String nombreArchivo = "ReporteFallas_TodasLasHeladeras.pdf";
        reportador.generarPDFReporte(nombreArchivo, reporteFallasTotales);

        System.out.println("Reporte de cantidad de fallas por todas las heladeras generado.");
    }

}
