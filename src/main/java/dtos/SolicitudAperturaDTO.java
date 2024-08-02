package dtos;

import lombok.Data;

@Data
public class SolicitudAperturaDTO {
    private String fechaHoraInicio;
    private String heladera;
    private String descripcion;
    private String colaborador;
    private String fechaHoraConcretado;
}
