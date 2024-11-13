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
    private String cuil;
    private String areaDeCobertura;
    private Boolean activo;
    private MedioDeContactoDTO whatsapp;
    private MedioDeContactoDTO telegram;
    private MedioDeContactoDTO email;
}
