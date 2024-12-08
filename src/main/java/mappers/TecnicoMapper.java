package mappers;

import domain.contacto.MedioDeContacto;
import domain.usuarios.Tecnico;
import dtos.MedioDeContactoDTO;
import dtos.TecnicoDTO;

public class TecnicoMapper {
    public static TecnicoDTO toDTO(Tecnico tecnico) {
        TecnicoDTO dto = new TecnicoDTO();
        
        dto.setWhatsapp(new MedioDeContactoDTO());
        dto.setTelegram(new MedioDeContactoDTO());
        dto.setEmail(new MedioDeContactoDTO());
        
        dto.setId(tecnico.getId());
        dto.setNombre(tecnico.getNombre());
        dto.setApellido(tecnico.getApellido());
        dto.setTipoDocumento(tecnico.tipoDeDocumento().toString());
        dto.setNroDocumento(tecnico.numeroDeDocumento());
        dto.setCuil(tecnico.getCuil().obtenerDescripcion());
        dto.setActivo(tecnico.getActivo());
        /*if (tecnico.getArea() != null) {
            dto.setAreaDeCobertura(tecnico.getArea().toString());
        }*/
        
        if (tecnico.getMediosDeContacto() != null) {
            for (MedioDeContacto medio : tecnico.getMediosDeContacto()) {
                String tipoMedio = medio.tipoMedioDeContacto();
                String informacion = medio.informacionDeMedioDeContacto();
                
                MedioDeContactoDTO medioDTO = new MedioDeContactoDTO();
                medioDTO.setNotificar(medio.isNotificar());
                medioDTO.setValor(informacion);
                
                switch(tipoMedio) {
                    case "Whatsapp":
                        dto.setWhatsapp(medioDTO);
                        break;
                    case "Telegram":
                        dto.setTelegram(medioDTO);
                        break;
                    case "Email":
                        dto.setEmail(medioDTO);
                        break;
                }
            }
        }
        
        return dto;
    }
} 