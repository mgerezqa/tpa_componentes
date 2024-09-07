package domain.contacto;

import domain.heladera.Heladera.Heladera;
import domain.suscripciones.TipoDeSuscripcion;
import domain.usuarios.ColaboradorFisico;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter @Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "telefonos")
@DiscriminatorColumn(name = "tipo")
public abstract class Telefono extends MedioDeContacto {

    @Column(name = "numero")
    private String numero;
    @Column(name = "username")
    private String username;

    public abstract String tipoMedioDeContacto();

    public abstract String informacionDeMedioDeContacto();

    public void enviarMensaje(ColaboradorFisico colaborador, Heladera heladera, TipoDeSuscripcion tipoDeSuscripcion){


    }

}
