package domain.suscripciones;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    //declarar lista de observers
    private List<iSuscriptor> observers = new ArrayList<>();

    public void suscribe(iSuscriptor suscriptor) {
        observers.add(suscriptor);
    }
    public void unsuscribe(iSuscriptor suscriptor) {
        observers.remove(suscriptor);
    }
    public void notifyObservers() {
        for (iSuscriptor observer : observers) {
            observer.update();
        }
    }

}
