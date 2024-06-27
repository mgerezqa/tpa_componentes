package controladores;
import domain.incidentes.Incidente;
import domain.incidentes.IncidenteFactory;
import dtos.FallaTecnicaInputDTO;
import repositorios.IRepositorioIncidentes;

import java.time.LocalDateTime;

public class ControladorFallasTecnicas {

    private IRepositorioIncidentes iRepositorioIncidentes;

    // Duda a consultar: la "recepcion" de los datos, la deberia hacer mediante un DTO? o directamente en el "crearFallaTecnica" ???
    public void crearFallaTecnica(FallaTecnicaInputDTO dto){

        if(dto.getFoto() == null){
            dto.setFoto("sin foto");
        }
        if(dto.getDescripcion() == null){
            dto.setDescripcion("sin descripcion");
        }

        Incidente incidente = IncidenteFactory.crearFallaTecnica(
                        dto.getHeladera(),
                        dto.getReportadoPor().toString(),
                        dto.getDescripcion(),
                        dto.getFoto());

        iRepositorioIncidentes.agregarIncidente(incidente);
    }

}
