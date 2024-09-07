package domain.temperatura;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Embeddable
public class Temperatura {

    @Column(name = "temperatura")
    private Float temperatura;

    @Column(name = "fechaYHora", columnDefinition = "DATE")
    private LocalDateTime fechaYhora;

    // ============================================================ //
    // < CONSTRUCTOR > //
    // ============================================================ //
    public Temperatura(Float temperatura, LocalDateTime fechaYhora) {
        this.temperatura = temperatura;
        this.fechaYhora = fechaYhora;
    }

}
