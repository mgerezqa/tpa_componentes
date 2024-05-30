package domain.contacto;

public abstract class MedioDeContacto {

    // TODO
    public abstract String obtenerDescripcion();

    @Override
    public String toString() {
        return this.obtenerDescripcion();
    }
}
