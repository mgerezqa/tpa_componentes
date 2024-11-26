package domain.reportes;

import domain.heladera.Heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.reportador.Reportador;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("reporte_viandas_por_heladera")
public class ReporteViandasHeladera extends Reporte{

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reporte_viandas_por_heladera_id")
    private List<Heladera> heladeras;

    @Override
    public void reportar(){
        for (Heladera heladera : heladeras) {
            Map<String, Integer> reporteFallas = reportador.generarReporteViandasPorHeladera(heladera);
            System.out.println("Reporte de cantidad de fallas por heladera: ");
            reporteFallas.forEach((nombreHeladera, cantidad) -> System.out.println(heladera + ": " + cantidad));
        }
    }

}
