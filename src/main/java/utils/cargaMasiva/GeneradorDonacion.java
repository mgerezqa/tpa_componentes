package utils.cargaMasiva;
import domain.donaciones.*;
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

    // bien ! ! !
    private static Dinero generarDinero(RegistroCSV lecturaRegistro, Colaborador colaborador) {
        Dinero dinero = new Dinero(lecturaRegistro.getCantidad(), FrecuenciaDeDonacion.FRECUENCIA_UNICA, lecturaRegistro.getFechaColab(), colaborador);
        int puntos = (int) (lecturaRegistro.getCantidad() * 0.5);
        dinero.setPuntosOtorgados(puntos);

        return dinero;
    }

    // bien ! ! !
    private static Distribuir generarRedistribucionViandas(RegistroCSV lecturaRegistro, ColaboradorFisico colaborador) {
        Distribuir reparto = new Distribuir(colaborador, lecturaRegistro.getCantidad(), lecturaRegistro.getFechaColab());
        int puntos = lecturaRegistro.getCantidad();
        reparto.setPuntosOtorgados(puntos);

        return reparto;
    }

    private static Vianda generarVianda(RegistroCSV lecturaRegistro, ColaboradorFisico colaborador) {
        Vianda vianda = new Vianda(null, lecturaRegistro.getFechaColab(), colaborador);
        vianda.setCantidad(lecturaRegistro.getCantidad());
        int puntos = (int) (lecturaRegistro.getCantidad() * 1.5);
        vianda.setPuntosOtorgados(puntos);

        return vianda;
    }

    private static RegistroDePersonaVulnerable generarEntregaTarjetas(RegistroCSV lecturaRegistro, ColaboradorFisico colaborador) {
        RegistroDePersonaVulnerable registro = new RegistroDePersonaVulnerable(colaborador, lecturaRegistro.getFechaColab());
        registro.setCantidad(lecturaRegistro.getCantidad());
        int puntos = lecturaRegistro.getCantidad() * 2;
        registro.setPuntosOtorgados(puntos);
        colaborador.sumarPuntos(puntos);
        return registro;
    }
}
