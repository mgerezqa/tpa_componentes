package domain.heladera.Heladera;

public class CondicionActual {
    private Heladera heladera;


    // Constructor protegido que solo puede ser llamado desde la clase Heladera
    protected CondicionActual(Heladera heladera) {
        this.heladera = heladera;
    }

    //int cantidad de viandas hasta alcance maximo
    public int cantidadDeViandasHastaAlcanzarMaximo() {
        return heladera.getCapacidadMax() - heladera.getCapacidadActual();
    }

    //int cantidad de viandas disponibles
    public int cantidadDeViandasDisponibles() {
        return heladera.getCapacidadActual();
    }

    //Heladera con desperfecto
    public boolean desperfecto() {
        return heladera.getEstadoHeladera() == EstadoHeladera.FUERA_DE_SERVICIO;
    }

    // Verificar si la cantidad de viandas disponibles es igual a n
    public boolean verificarCantidadViandasDisponibles(int n) {
        return cantidadDeViandasDisponibles() <= n;
    }

    // Verificar si la cantidad de viandas hasta alcanzar el máximo es igual a n
    public boolean verificarCantidadViandasHastaAlcanzarMaximo(int n) {
        return cantidadDeViandasHastaAlcanzarMaximo() <= n;
    }

    // Verificar si la heladera tiene desperfecto
    public boolean verificarDesperfecto() {
        return desperfecto();
    }


}
