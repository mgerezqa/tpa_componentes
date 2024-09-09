package domain.donaciones;


import domain.tarjeta.Tarjeta;
import domain.usuarios.ColaboradorFisico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Entity
@Table(name = "registro_persona_vulnerable")
public class RegistroDePersonaVulnerable {

    @ManyToOne
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    private ColaboradorFisico colaborador;

    @OneToOne
    @JoinColumn(name = "tarjeta_id", referencedColumnName = "id")
    private Tarjeta tarjeta;

}