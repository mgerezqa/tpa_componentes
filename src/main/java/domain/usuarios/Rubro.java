package domain.usuarios;

import lombok.Getter;
import lombok.RequiredArgsConstructor;//Genera un constructor que acepta un parámetro por cada campo final no inicializado, que en este caso es descripcion.

@Getter
@RequiredArgsConstructor
public enum Rubro {
    EDUCACION("EDUCACION"),
    SALUD("SALUD"),
    TECNOLOGIA("TECNOLOGIA"),
    ENERGIA("ENERGIA"),
    PRODUCCION("PRODUCCION"),
    FINANZAS("FINANZAS"),
    COMERCIO("COMERCIO"),
    SERVICIOS("SERVICIOS"),
    CONSTRUCCION("CONSTRUCCION"),
    OTRO("OTRO");

    private final String descripcion; //
}
