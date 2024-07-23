package domain.suscripciones;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private List<Suscripcion> suscripciones = new ArrayList<>();

    public void suscribe(Suscripcion suscripcion) {
        suscripciones.add(suscripcion);
    }

    public void unsuscribe(Suscripcion suscripcion) {
        suscripciones.remove(suscripcion);
    }

    public void notifyObservers() {
        for (Suscripcion suscripcion : suscripciones) {
            if (suscripcion.getTipoDeSuscripcion().cumpleCriterio(suscripcion.getHeladera())) {
                suscripcion.getColaboradorFisico().update();
            }
        }
    }
}

