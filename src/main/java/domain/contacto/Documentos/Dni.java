package domain.contacto.Documentos;

public class Dni implements Documento{

    @Override
    public String tipoDocumento() {
        return "Dni";
    }

    @Override
    public String numeroDocumento() {
        return null;
    }

}
