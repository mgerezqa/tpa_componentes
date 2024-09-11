package domain.formulario.documentos;
import domain.excepciones.NumeroDeDocumentoInvalidoException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class Documento {

    @Column(name = "numero_documento", nullable = false)
    private String numeroDeDocumento;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipo;

    public Documento(TipoDocumento tipo, String numeroDeDocumento) {

        setNumeroDeDocumento(numeroDeDocumento);
        this.tipo = tipo;
    }

    public void setNumeroDeDocumento(String numeroDeDocumento) {
        if (numeroDeDocumento.length() < 7 || numeroDeDocumento.length() > 9) {
            throw new NumeroDeDocumentoInvalidoException("El número de documento debe tener entre 7 y 9 caracteres.");
        }
        this.numeroDeDocumento = numeroDeDocumento;
    }

}


