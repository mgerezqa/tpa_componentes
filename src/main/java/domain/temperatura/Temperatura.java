package domain.temperatura;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter @Getter
public class Temperatura {

    private Float temperatura;
    private LocalDateTime fechaYhora;

    // ============================================================ //
    // < CONSTRUCTOR > //
    // ============================================================ //
    public Temperatura(Float temperatura, LocalDateTime fechaYhora) {
        this.temperatura = temperatura;
        this.fechaYhora = fechaYhora;
    }

}
