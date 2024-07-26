package domain.suscripciones;

import utils.notificador.Notificador;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private List<Suscripcion> suscripciones = new ArrayList<>();
    private Notificador notificador = new Notificador();

    public void suscribe(Suscripcion suscripcion) {
        suscripciones.add(suscripcion);
    }

    public void unsuscribe(Suscripcion suscripcion) {
        suscripciones.remove(suscripcion);
    }

    public void notifyObservers() {
        for (Suscripcion suscripcion : suscripciones) {
            if (suscripcion.getTipoDeSuscripcion().cumpleCriterio(suscripcion.getHeladera())) {
                notificador.notificar(suscripcion.getColaboradorFisico(), suscripcion.getHeladera());
            }
        }
    }
}

