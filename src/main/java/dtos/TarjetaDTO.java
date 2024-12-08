package dtos;

import lombok.Data;

@Data
public class TarjetaDTO {
    private String codigoIdentificador;
    private Boolean estado;
    private Integer cantidadMaxDeUso;
    private Integer cantidadUsadaEnElDia;
    private String fechaInicioDeFuncionamiento;
    private Long idColaborador;
    private Long idBeneficiario;
}
