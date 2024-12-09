package domain.puntos;

import domain.donaciones.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import domain.Config;
import domain.heladera.Heladera.Heladera;

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
    public int puntosViandasDistribuidas (Integer viandasDistribuidas) {
        double coeficiente = Double.parseDouble(config.getProperty("puntos.coefViandasDistribuidas"));
        return (int) Math.round(viandasDistribuidas * coeficiente);
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
    public int puntosHeladerasActivas (List<MantenerHeladera> mantenciones) {
        double coeficiente = Double.parseDouble(config.getProperty("puntos.coefHeladerasActivas"));
        List<Heladera> heladeras = mantenciones.stream().map(MantenerHeladera::getHeladera).collect(Collectors.toList());
        int cantActivas  = (int) heladeras.stream().filter(Heladera::estaActivaHeladera).count();
        int meses = mantenciones.stream().mapToInt(MantenerHeladera::mesesMantenida).sum();
        int puntos = (int) Math.round(cantActivas *meses *coeficiente);

        return puntos;
    }
}
