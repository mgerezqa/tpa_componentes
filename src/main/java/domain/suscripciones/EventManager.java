package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    @Setter @Getter
    private Suscripcion suscripcion;


    //declarar lista de observers
    private List<iSuscriptor> observers = new ArrayList<>();

    public void suscribe(iSuscriptor suscriptor) {
        observers.add(suscriptor);
    }
    public void unsuscribe(iSuscriptor suscriptor) {
        observers.remove(suscriptor);
    }

    public  void notifyObservers()
    {
        for (iSuscriptor observer : observers) {
            if(suscripcion.getTipoSuscripcion().verificarCondicion(suscripcion.getHeladera())){
                observer.update();
            }
        }
    }

}
