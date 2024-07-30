package domain.formulario.documentos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoDocumento {
    DNI("DNI"),
    PASAPORTE("PASAPORTE"),
    CEDULA_DE_IDENTIDAD("CEDULA DE IDENTIDAD"),
    LIBRETA_DE_ENROLAMIENTO("LIBRETA DE ENROLAMIENTO");

    private final String descripcion; //

}
