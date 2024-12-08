package utils.recomendacioneDeUbicaciones.entidades;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class ListadoDeComunidades {
    @SerializedName("lugares")
    public List<Comunidad> comunidades;
}
