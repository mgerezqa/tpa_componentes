package domain.usuariosNuevo;

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

    public static TipoRazonSocial obtenerEnum(String unString){
        try{
            return TipoRazonSocial.valueOf(unString);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de razon social invalido!");
        }
    }
}


