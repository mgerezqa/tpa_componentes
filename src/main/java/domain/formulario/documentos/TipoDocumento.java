package domain.formulario.documentos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoDocumento {
    DNI("DNI"),
    PASAPORTE("PASAPORTE"),
    CEDULA_DE_IDENTIDAD("CEDULA DE IDENTIDAD"),
    LE("LIBRETA DE ENROLAMIENTO"),
    LC("LIBRETA CIVICA");
    private final String descripcion; //

}
