package domain.reportes;

import lombok.Getter;
import lombok.Setter;
import utils.reportador.Reportador;

import java.time.LocalDateTime;

public abstract class Reporte {
    @Getter
    @Setter
    protected String id;
    @Getter
    @Setter
    protected LocalDateTime fechaGeneracion;
    @Setter
    protected Reportador reportador;



    public abstract void reportar();
}
