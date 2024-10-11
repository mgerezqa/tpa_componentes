package dtos;

import lombok.Data;

@Data
public class SuscripcionDTO {
    private Long id;
    private Long idColaborador;
    private Long idHeladera;
    private String tipoDeSuscripcion;
    private Boolean estado;
}
