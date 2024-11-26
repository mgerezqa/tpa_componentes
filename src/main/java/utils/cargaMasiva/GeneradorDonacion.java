package utils.cargaMasiva;
import domain.donaciones.*;
import domain.puntos.CalculadoraPuntos;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;

public class GeneradorDonacion {

    public static Donacion generar(RegistroCSV lecturaRegistro, Colaborador colaborador) {
        return switch (lecturaRegistro.getTipoColab()) {
            case DINERO -> generarDinero(lecturaRegistro, colaborador);
            case VIANDA -> generarVianda(lecturaRegistro, (ColaboradorFisico) colaborador);
            case DISTRIBUCION -> generarRedistribucionViandas(lecturaRegistro, (ColaboradorFisico) colaborador);
            case REGISTRO_PV -> generarEntregaTarjetas(lecturaRegistro, (ColaboradorFisico) colaborador);
            default -> throw new IllegalArgumentException("Tipo de donación no reconocido: " + lecturaRegistro.getTipoColab());
        };
    }

    private static Dinero generarDinero(RegistroCSV lecturaRegistro, Colaborador colaborador) {

        return new Dinero(
                lecturaRegistro.getCantidad(),
                FrecuenciaDeDonacion.FRECUENCIA_UNICA,  // Por defecto si no se especifica otra frecuencia
                lecturaRegistro.getFechaColab(),
                colaborador
        );

    }

    private static Vianda generarVianda(RegistroCSV lecturaRegistro, ColaboradorFisico colaborador) {

        return new Vianda(null, lecturaRegistro.getFechaColab(), colaborador);
    }

    private static Distribuir generarRedistribucionViandas(RegistroCSV lecturaRegistro, ColaboradorFisico colaborador) {
        return new Distribuir(colaborador, lecturaRegistro.getCantidad(), lecturaRegistro.getFechaColab());
    }

    private static RegistroDePersonaVulnerable generarEntregaTarjetas(RegistroCSV lecturaRegistro, ColaboradorFisico colaborador) {
        return new RegistroDePersonaVulnerable(colaborador, lecturaRegistro.getFechaColab());
    }
}
