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
import java.util.List;
import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("reporte_viandas_por_colaborador")
public class ReporteViandasColaborador extends Reporte {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reporte_viandas_por_colaborador_id")
    private List<ColaboradorFisico>  colaboradores;

    @Override
    public void reportar() {
        for(ColaboradorFisico colaborador : colaboradores){
            Map<String, Integer> reporteViandasPorColaborador = reportador.generarReporteViandasPorColaborador(colaborador);
            System.out.println("Reporte de cantidad de viandas donadas por colaborador: ");
            reporteViandasPorColaborador.forEach((nombreColaborador, cantidad) -> System.out.println(colaborador + ": " + cantidad));
        }
    }





}
