package repositorios.reposEnMemoria;

import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.SolicitudApertura;
import repositorios.interfaces.IRepositorioSolicitudApertura;

import java.util.ArrayList;
import java.util.List;

public class RepositorioSolicitudApertura implements IRepositorioSolicitudApertura {
    private List<SolicitudApertura> solicitudesApertura;

    public RepositorioSolicitudApertura() {
        this.solicitudesApertura = new ArrayList<>();
    }

    @Override
    public void alta(SolicitudApertura solicitudApertura) {
        solicitudesApertura.add(solicitudApertura);
    }

    @Override
    public List<SolicitudApertura> obtenerTodas() {
        return solicitudesApertura;
    }

    @Override
    public SolicitudApertura obtener(String fechaHoraInicio, String heladera, String descripcion) {
        //TODO
        return null;
    }

}
