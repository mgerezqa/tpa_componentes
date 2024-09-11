package domain.donaciones;


import domain.tarjeta.Tarjeta;
import domain.usuarios.ColaboradorFisico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "donaciones_registro_vulnerables")
public class RegistroDePersonaVulnerable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "colaborador_id")
    private ColaboradorFisico colaborador;

    @OneToOne
    @JoinColumn(name = "tarjeta_uuid")
    private Tarjeta tarjeta;

}