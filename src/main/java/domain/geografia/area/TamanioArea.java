package domain.geografia.area;

public enum TamanioArea {
    PEQUENA(2.0),
    MEDIANA(5.0),
    GRANDE(Double.MAX_VALUE);

    private final double longitud;

    TamanioArea(double longitud) {
        this.longitud = longitud;
    }

    // ME RETORNA LA LONGITUD MAXIMA DE CADA "TIPO DE AREA"
    public double getLongitud() {
        return longitud;
    }

}
