package domain.heladera.Sensores;

import domain.heladera.Heladera;

public class SensorMovimiento {

    public void detectoMovimiento(Heladera heladera){
        heladera.enviarAlerta();
    }

}
