package dtos;

import lombok.Data;

import javax.persistence.Column;
@Data
public class TecnicoDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String tipoDocumento;
    private String nroDocumento;
    private Boolean activo;
    private String calle;
    private String altura;
    private String tamanioArea;
}
