package repositorios.interfaces;

import domain.heladera.Heladera.SolicitudApertura;

import java.util.List;

public interface IRepositorioSolicitudApertura {
    void alta(SolicitudApertura solicitudApertura);
    List<SolicitudApertura> obtenerTodas();
    SolicitudApertura obtener(String fechaHoraInicio, String heladera, String descripcion);
}
