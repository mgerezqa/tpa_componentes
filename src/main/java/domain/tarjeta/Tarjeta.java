package domain.tarjeta;

import domain.excepciones.CantidadDisponibleLimitePorDiaException;
import domain.persona.PersonaVulnerable;
import domain.usuarios.ColaboradorFisico;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_tarjeta")
@Table(name = "tarjetas")
public abstract class Tarjeta {
    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "domain.tarjeta.GeneradorUUID")
    @Column(name = "codigo_identificador", unique = true, nullable = false, length = 11)
    private String codigoIdentificador; //Leer commit donde se menciona la decisión del modelado del codigo de esta manera.

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "cantidad_usada_dia")
    private Integer cantidadUsadaEnElDia;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tarjeta")
    private List<RegistroDeUso> registros;//Debe quedar registrado, cuándo la usó, y en cuál heladera.

    @Column(name = "fecha_inicio_funcionamiento")
    private LocalDate fechaInicioDeFuncionamiento;

    public Tarjeta() {
        this.cantidadUsadaEnElDia = 0;
        this.registros = new ArrayList<>();
        this.estado = true;
    }


    public void agregarRegistroDeUso(RegistroDeUso nuevoRegistro){
        this.getRegistros().add(nuevoRegistro);
    }

    public void aumentarCantidadDeUsoEnElDia() {
        this.setCantidadUsadaEnElDia(this.getCantidadUsadaEnElDia() + 1);
    }

    public abstract void usoDeTarjeta(RegistroDeUso nuevoRegistro);
    public void resetCantidadUsadaEnElDia(){
        this.setCantidadUsadaEnElDia(0);
    }

}