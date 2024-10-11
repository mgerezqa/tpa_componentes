package services.interfaces;


import domain.usuarios.ColaboradorJuridico;
import dtos.requests.ColaboradorJuridicoInputDTO;
import dtos.responses.ColaboradorJuridicoOutputDTO;

import java.util.List;

public interface IServiceColaboradorJuridico {
    ColaboradorJuridicoOutputDTO obtener(ColaboradorJuridicoOutputDTO colaboradorJuridico);
    List<ColaboradorJuridicoOutputDTO> obtenerTodos();

    void crear(ColaboradorJuridicoInputDTO colaboradorJuridico);
    void modificar(ColaboradorJuridicoInputDTO colaboradorJuridico);
    void eliminar(ColaboradorJuridicoInputDTO colaboradorJuridico);
}
