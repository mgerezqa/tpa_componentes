package domain.contacto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum eTipoMedioDeContacto {
    EMAIL("EMAIL"),
    TELEGRAM("TELEGRAM"),
    WHATSAPP("WHATSAPP");

    private final String descripcion;
}

