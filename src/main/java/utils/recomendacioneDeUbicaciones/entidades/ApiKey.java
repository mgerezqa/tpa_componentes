package utils.recomendacioneDeUbicaciones.entidades;

import lombok.Data;

@Data
public class ApiKey {
    private String mensaje;
    private String key;

    public ApiKey(String mensaje, String key) {
        this.mensaje = mensaje;
        this.key = key;
    }
}
