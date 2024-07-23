package domain.geografia.area;
import domain.geografia.Ubicacion;
import lombok.Data;

@Data
public class AreaDeCobertura {

    private Ubicacion ubicacionPrincipal;
    private TamanioArea tamanioArea;

    public AreaDeCobertura(Ubicacion ubicacionPrincipal, TamanioArea tamanioArea) {
        this.ubicacionPrincipal = ubicacionPrincipal;
        this.tamanioArea = tamanioArea;
    }

    public Boolean estaEnElAreaDelTecnico(Ubicacion ubicacion){
        return (ubicacionPrincipal.calcularDistanciaKmA(ubicacion)
                <= tamanioArea.getLongitud());
    }

}
