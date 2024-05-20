package domain.usuarios;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoRazonSocial {
    GUBERNAMENTAL("GUBERNAMENTAL"),
    ONG("ONG"),
    EMPRESA("EMPRESA"),
    INSTITUCION("INSTITUCION");
    private final String descripcion; //

    }
