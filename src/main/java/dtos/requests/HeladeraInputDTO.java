package dtos.requests;

import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import lombok.Data;

@Data
public class HeladeraInputDTO {
    private Long id;
    private String nombreIdentificador;
    private Integer capacidadMax;
    private Integer capacidadActual;
    private String fechaInicioFuncionamiento;
    private EstadoHeladera estadoHeladera;
    private Float temperatura;
    private ModeloDeHeladera modeloDeHeladera;
}
