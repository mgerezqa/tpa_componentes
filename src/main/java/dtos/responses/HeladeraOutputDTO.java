package dtos.responses;

import domain.geografia.Ubicacion;
import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import lombok.Data;

@Data
public class HeladeraOutputDTO {
    private Long id;
    private String nombreIdentificador;
    private Integer capacidadMax;
    private Integer capacidadActual;
    private String fechaInicioFuncionamiento;
    private EstadoHeladera estadoHeladera;
    private Float temperatura;
    private ModeloDeHeladera modeloDeHeladera;
}
