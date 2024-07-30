package utils.notificador;

import domain.incidentes.Incidente;
import domain.usuarios.Tecnico;

public interface INotificadorIncidentes {
    Tecnico tecnicoMasCercano(Incidente incidente);
    void notificarTecnico(Incidente incidente);
}
