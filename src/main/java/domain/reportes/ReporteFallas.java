package domain.reportes;

import domain.heladera.Heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.reportador.Reportador;
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

    @Override
    public void reportar(){
        for (Heladera heladera : heladeras) {
            Map<String, Integer> reporteFallas = reportador.generarReporteFallasPorHeladera(heladera);
            System.out.println("Reporte de cantidad de fallas por heladera: ");
            reporteFallas.forEach((nombreHeladera, cantidad) -> System.out.println(heladera + ": " + cantidad));
        }
    }




}
