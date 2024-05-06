package form_version_seg;

public class FormularioPorDefecto {
    Campo campoNombre = new Campo("Nombre",TipoEntrada.ENTRADA_TEXTO);
    Campo campoApellido = new Campo("Apellido",TipoEntrada.ENTRADA_TEXTO);
    Campo campoContacto = new Campo("Contacto",TipoEntrada.ENTRADA_TEXTO);


    public String getCampoContacto() {
        return campoContacto.getdescripcion();
    }


    public String getCampoApellido() {
        return campoApellido.getdescripcion();
    }

    public String getCampoNombre() {
        return campoNombre.getdescripcion();
    }



}
