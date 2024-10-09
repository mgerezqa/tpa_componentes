package services.interfaces;

import dtos.requests.HeladeraInputDTO;
import dtos.responses.HeladeraOutputDTO;

import java.util.List;

public interface IServiceHeladera {
    HeladeraOutputDTO obtener(HeladeraInputDTO heladera);
    List<HeladeraOutputDTO> obtenerTodos();

    void crear(HeladeraInputDTO heladera);
    void modificar(HeladeraInputDTO heladera);
    void eliminar(HeladeraInputDTO heladera);
}
