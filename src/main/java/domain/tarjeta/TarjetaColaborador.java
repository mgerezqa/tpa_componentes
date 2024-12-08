package domain.tarjeta;

import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
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
    public static TarjetaColaborador of(ColaboradorFisico colaborador){
        return TarjetaColaborador.builder()
                .colaborador(colaborador)
                .fechaInicioDeFuncionamiento(LocalDate.now())
                .cantidadUsadaEnElDia(0)
                .registros(new ArrayList<>())
                .estado(true)
                .build();
    }
}
