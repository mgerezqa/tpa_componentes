package domain.tarjeta;

public enum EventosTarjetasColab {
    CREACION("Creacion de tarjeta"),
    ENTREGA("Entrega de tarjeta"),
    USO_HELADERA("Uso en heladera");

    private String descripcion;

    EventosTarjetasColab (String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
