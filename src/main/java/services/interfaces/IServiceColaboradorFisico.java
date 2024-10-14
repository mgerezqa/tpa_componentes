package services.interfaces;

import dtos.requests.ColaboradorFisicoInputDTO;
import dtos.responses.ColaboradorFisicoOutputDTO;

import java.util.List;

public interface IServiceColaboradorFisico {
    ColaboradorFisicoOutputDTO obtener(ColaboradorFisicoOutputDTO colaboradorFisico);
    List<ColaboradorFisicoOutputDTO> obtenerTodos();

    void crear(ColaboradorFisicoInputDTO colaboradorFisico);
    void modificar(ColaboradorFisicoInputDTO colaboradorFisico);
    void eliminar(ColaboradorFisicoInputDTO colaboradorFisico);
}
