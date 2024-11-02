package domain.donaciones;


import domain.persona.PersonaVulnerable;
import domain.tarjeta.Tarjeta;
import domain.tarjeta.TarjetaVulnerable;
import domain.usuarios.ColaboradorFisico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "colaborador_id")
    private ColaboradorFisico colaborador;

    @Column(name = "fecha_del_registro")
    private LocalDate fechaRegistro;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "tarjeta_uuid")
    private TarjetaVulnerable tarjeta;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "vulnerable_id")
    private PersonaVulnerable personaVulnerable;

    public RegistroDePersonaVulnerable(ColaboradorFisico colaborador, TarjetaVulnerable tarjeta, PersonaVulnerable vulnerable, LocalDate fechaRegistro) {
        this.colaborador = colaborador;
        this.tarjeta = tarjeta;
        this.personaVulnerable = vulnerable;
        this.fechaRegistro = fechaRegistro;
        tarjeta.setVulnerable(vulnerable);
        vulnerable.setFechaRegitrado(fechaRegistro);
        tarjeta.setFechaInicioDeFuncionamiento(fechaRegistro);
    }
}