package domain.donaciones;


import domain.persona.PersonaVulnerable;
import domain.tarjeta.Tarjeta;
import domain.tarjeta.TarjetaVulnerable;
import domain.usuarios.Colaborador;
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
public class RegistroDePersonaVulnerable extends Donacion{

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "tarjeta_uuid")
    private TarjetaVulnerable tarjeta;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "vulnerable_id")
    private PersonaVulnerable personaVulnerable;

    public RegistroDePersonaVulnerable(Colaborador colaborador, TarjetaVulnerable tarjeta, PersonaVulnerable vulnerable, LocalDate fechaRegistro) {
        super(fechaRegistro,colaborador);
        this.tarjeta = tarjeta;
        this.personaVulnerable = vulnerable;
        tarjeta.setVulnerable(vulnerable);
        vulnerable.setFechaRegitrado(fechaRegistro);
        tarjeta.setFechaInicioDeFuncionamiento(fechaRegistro);
    }
    public RegistroDePersonaVulnerable(Colaborador colaborador, TarjetaVulnerable tarjeta, PersonaVulnerable vulnerable) {
        super(colaborador);
        this.tarjeta = tarjeta;
        this.personaVulnerable = vulnerable;
        tarjeta.setVulnerable(vulnerable);
        tarjeta.setFechaInicioDeFuncionamiento(vulnerable.getFechaRegitrado());
    }
    public RegistroDePersonaVulnerable(ColaboradorFisico colaboradorQueLaDono, LocalDate fechaDeDonacion) {
        super(colaboradorQueLaDono);
        this.fechaDeDonacion = fechaDeDonacion;
    }
    @Override
    public String getTipo() {
        return "RegistroDePersonaVulnerable";
    }
}