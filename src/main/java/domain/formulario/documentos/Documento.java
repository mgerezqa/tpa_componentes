package domain.formulario.documentos;

import domain.excepciones.NumeroDeDocumentoInvalidoException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Setter @Getter
@Embeddable
public class Documento {
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipo;
    @Column(name = "numero_documento")
    private String numeroDeDocumento;

    public Documento(TipoDocumento tipo, String numeroDeDocumento) {

        if (numeroDeDocumento.length() < 7 || numeroDeDocumento.length() > 9) {
            throw new NumeroDeDocumentoInvalidoException("El número de documento debe tener entre 7 y 9 caracteres.");
        }

        this.tipo = tipo;
        this.numeroDeDocumento = numeroDeDocumento;
    }

}
