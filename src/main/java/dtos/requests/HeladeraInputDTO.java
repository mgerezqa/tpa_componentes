package dtos.requests;

import domain.geografia.Ubicacion;
import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HeladeraInputDTO {
    private Long id;
    private String nombreIdentificador;
    private ModeloDeHeladera modeloDeHeladera;
    private Ubicacion ubicacion;
    private Integer capacidadMax;
    private Integer capacidadActual;
    private String fechaInicioFuncionamiento;
    private EstadoHeladera estadoHeladera;
    private Float temperatura;

}
