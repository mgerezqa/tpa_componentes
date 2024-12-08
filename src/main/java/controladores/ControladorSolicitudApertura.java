package controladores;

import domain.contacto.Email;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.SolicitudApertura;
import domain.heladera.Heladera.SolicitudAperturaFactory;
import domain.usuarios.*;
import dtos.SolicitudAperturaDTO;
import repositorios.interfaces.*;

public class ControladorSolicitudApertura {
    private IRepositorioSolicitudApertura repositorioSolicitudApertura;
    private IRepositorioHeladeras repositorioHeladeras;
    private IRepositorioColaboradores repositorioColaboradores;

    public ControladorSolicitudApertura(IRepositorioSolicitudApertura repositorioSolicitudApertura, IRepositorioHeladeras repositorioHeladeras, IRepositorioColaboradores repositorioColaboradores) {
        this.repositorioSolicitudApertura = repositorioSolicitudApertura;
        this.repositorioHeladeras = repositorioHeladeras;
        this.repositorioColaboradores = repositorioColaboradores;
    }

    public void crearSolicitudApertura(SolicitudAperturaDTO solicitudAperturaDTO){

        Heladera heladera = repositorioHeladeras.obtenerHeladeraPorNombre(solicitudAperturaDTO.getHeladera());
        //TODO
        //REPO DE COLABORADORES

        //TODO
        //Reemplazar parametro
        SolicitudApertura solicitudApertura = SolicitudAperturaFactory.crear(solicitudAperturaDTO, new ColaboradorFisico("Lalo", "Menz"));
        heladera.registrarSolicitud(solicitudApertura);
    }
}
