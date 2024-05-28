package domain.contacto.Documentos;

import lombok.Getter;
import lombok.Setter;


public class Dni implements Documento{

    @Getter @Setter
    public String numero;

    @Override
    public String tipoDocumento() {
        return "Dni";
    }

    @Override
    public String numeroDocumento() {
        return numero;
    }

    public Dni(String numero) {
        this.numero = numero;
    }
}
