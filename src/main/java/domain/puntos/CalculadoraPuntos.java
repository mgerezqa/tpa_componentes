package domain.puntos;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class CalculadoraPuntos {
    private static CalculadoraPuntos instancia;

    public CalculadoraPuntos() throws IOException {
        CalculadoraPuntosConfigLoader.init();
    }

    public static CalculadoraPuntos obtenerInstancia() throws IOException {
        if (instancia == null) {
            instancia = new CalculadoraPuntos();
        }
        return instancia;
    }

    public int puntosPesosDonados (double pesosDonados) {
        double coeficiente = CalculadoraPuntosConfigLoader.getDoubleProperty("coeficiente.dineroDonado");
        return (int) Math.round(pesosDonados * coeficiente);
    }

    public int puntosViandasDistribuidas (int viandasDistribuidas) {
        double coeficiente = CalculadoraPuntosConfigLoader.getDoubleProperty("coeficiente.viandasDistribuidas");
        return (int) Math.round(viandasDistribuidas * coeficiente);
    }

    public int puntosViandasDonadas (int viandasDonadas) {
        double coeficiente = CalculadoraPuntosConfigLoader.getDoubleProperty("coeficiente.viandasDonadas");
        return (int) Math.round(viandasDonadas * coeficiente);
    }

    public int puntosTarjetasRepatidas (int tarjetasRepartidas) {
        double coeficiente = CalculadoraPuntosConfigLoader.getDoubleProperty("coeficiente.tarjetasRepartidas");
        return (int) Math.round(tarjetasRepartidas * coeficiente);
    }

    public int puntosHeladerasActivas (int heladeras, int mesesActivas) {
        double coeficiente = CalculadoraPuntosConfigLoader.getDoubleProperty("coeficiente.heladerasActivas");
        return (int) Math.round(heladeras * mesesActivas * coeficiente);
    }
}
