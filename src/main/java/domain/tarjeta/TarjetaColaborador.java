package domain.tarjeta;

import domain.usuarios.ColaboradorFisico;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@DiscriminatorValue("colaborador")
public class TarjetaColaborador extends Tarjeta {
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "colaborador_id")
    private ColaboradorFisico colaborador;

    @Override
    public void usoDeTarjeta(RegistroDeUso nuevoRegistro) {
        this.aumentarCantidadDeUsoEnElDia();
        this.agregarRegistroDeUso(nuevoRegistro);
    }
}
