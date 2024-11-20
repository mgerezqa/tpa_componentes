package mappers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeladeraDTO {
    private String estado;
    private String estacion;
    private String direccion;
    private Float temperatura;
    private Integer capacidadMax;
    private Integer viandasDisponibles;
    private String inicioOperacion;
    private Float latitud;
    private Float longitud;
    private String descripcion;
} 