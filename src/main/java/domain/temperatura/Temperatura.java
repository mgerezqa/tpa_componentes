package domain.temperatura;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Embeddable
public class Temperatura {

    @Column(name = "temperatura")
    private Float temperatura;

    @Column(name = "fechaYHora", columnDefinition = "DATETIME")
    private LocalDateTime fechaYhora;

    // ============================================================ //
    // < CONSTRUCTOR > //
    // ============================================================ //
    public Temperatura(Float temperatura, LocalDateTime fechaYhora) {
        this.temperatura = temperatura;
        this.fechaYhora = fechaYhora;
    }

}
