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
        for(ColaboradorFisico colaborador : colaboradores){
            Map<String, Integer> reporteViandasPorColaborador = reportador.generarReporteViandasPorColaborador(colaborador);


            for(Map.Entry<String, Integer> entry : reporteViandasPorColaborador.entrySet()){
                reporteViandasPorColaborador.merge(entry.getKey(), entry.getValue(), Integer::sum);
            }
        }

        String nombreArchivo = "ReporteViandas_TodosLosColaboradores.pdf";
        reportador.generarPDFReporte(nombreArchivo, reporteViandasPorColaborador);

        System.out.println("Reporte de cantidad de viandas por todos los colaboradores generado.");
    }

}
