package domain.contacto;

import domain.heladera.Heladera.Heladera;
import domain.incidentes.Alerta;
import domain.incidentes.FallaTecnica;
import domain.incidentes.Incidente;
import domain.suscripciones.TipoDeSuscripcion;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.Tecnico;
import jakarta.mail.MessagingException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter @Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "medios_de_contacto")
@DiscriminatorColumn(name = "tipo_de_medio")
public abstract class MedioDeContacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private boolean notificar;

    @Column(name = "tipoMedio")
    public abstract String tipoMedioDeContacto();
    @Transient
    public abstract String informacionDeMedioDeContacto();

    public abstract void enviarMensaje(ColaboradorFisico colaborador, Heladera heladera, TipoDeSuscripcion tipoDeSuscripcion) throws MessagingException;
    public void enviarMensaje(Tecnico tecnico, Alerta alerta) throws MessagingException {}
    public void enviarMensaje(Tecnico tecnico, FallaTecnica alerta) throws MessagingException {}
}
