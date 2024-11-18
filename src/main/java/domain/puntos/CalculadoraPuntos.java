package domain.puntos;

import domain.donaciones.*;
import java.io.IOException;
import domain.Config;
public class CalculadoraPuntos {
    private static CalculadoraPuntos instancia;
    private Config config;

    public CalculadoraPuntos() {
            config = Config.getInstance();
    }

    public static CalculadoraPuntos obtenerInstancia(){
        if (instancia == null) {
            instancia = new CalculadoraPuntos();
        }
        return instancia;
    }

    public int puntosPesosDonados (Dinero donacion) {
        double coeficiente = Double.parseDouble(config.getProperty("puntos.coefDineroDonado"));
        return (int) Math.round(donacion.getCantidad() * coeficiente);
    }

    public int puntosViandasDistribuidas (Distribuir viandasDistribuidas) {
        double coeficiente = Double.parseDouble(config.getProperty("puntos.coefViandasDistribuidas"));
        return (int) Math.round(viandasDistribuidas.getCantidad() * coeficiente);
    }

    public int puntosViandasDonadas (int viandasDonadas) {
        double coeficiente = Double.parseDouble(config.getProperty("puntos.coefViandasDonadas"));
        return (int) Math.round(viandasDonadas * coeficiente);
    }

    public int puntosTarjetasRepatidas (int tarjetasRepartidas) {
        double coeficiente = Double.parseDouble(config.getProperty("puntos.coefTarjetasRepartidas"));
        return (int) Math.round(tarjetasRepartidas * coeficiente);
    }

    public int puntosHeladerasActivas (MantenerHeladera heladera) {
        double coeficiente = Double.parseDouble(config.getProperty("puntos.coefHeladerasActivas"));
        int puntos = (int) Math.round((heladera.mesesMantenida()-heladera.getMesesPuntarizados()) * coeficiente);

        heladera.setMesesPuntarizados(heladera.mesesMantenida());
        return puntos;
    }
}
