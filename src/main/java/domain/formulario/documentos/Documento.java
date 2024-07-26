package domain.formulario.documentos;

import domain.excepciones.NumeroDeDocumentoInvalidoException;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Documento {

    private TipoDocumento tipo;
    private String numeroDeDocumento;

    public Documento(TipoDocumento tipo, String numeroDeDocumento) {

        if (numeroDeDocumento.length() < 7 || numeroDeDocumento.length() > 9) {
            throw new NumeroDeDocumentoInvalidoException("El número de documento debe tener entre 7 y 9 caracteres.");
        }

        this.tipo = tipo;
        this.numeroDeDocumento = numeroDeDocumento;
    }

}
