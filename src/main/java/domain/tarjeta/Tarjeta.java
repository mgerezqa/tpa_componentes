package domain.tarjeta;

import domain.excepciones.CantidadDisponibleLimitePorDiaException;
import domain.persona.PersonaVulnerable;
import domain.usuarios.ColaboradorFisico;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table (name = "tarjetas")
public class Tarjeta {

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "domain.tarjeta.GeneradorUUID")
    @Column(name = "codigo_identificador", unique = true, nullable = false, length = 11)
    private String codigoIdentificador; //Leer commit donde se menciona la decisión del modelado del codigo de esta manera.

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona_vulnerable")
    private PersonaVulnerable vulnerable;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "colaborador_id")
    private ColaboradorFisico colaborador;

    private static Integer cantidadDisponiblePorDefecto = 4;

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
    }

    public int cantidadDisponiblePorMenores(){
        return 2*this.getVulnerable().cantidadDeMenoresACargo();
    }

    public int cantidadLimiteDisponiblePorDia(){
        return cantidadDisponiblePorDefecto + this.cantidadDisponiblePorMenores();
    }

    public boolean tieneCantidadDisponible(){
        return this.getCantidadUsadaEnElDia() < this.cantidadLimiteDisponiblePorDia();
    }

    public void agregarRegistroDeUso(RegistroDeUso nuevoRegistro){
        this.getRegistros().add(nuevoRegistro);
    }

    public void aumentarCantidadDeUsoEnElDia() {
        this.setCantidadUsadaEnElDia(this.getCantidadUsadaEnElDia() + 1);
    }

    public void usoDeTarjeta(RegistroDeUso nuevoRegistro){
        if(this.tieneCantidadDisponible()){
            this.aumentarCantidadDeUsoEnElDia();
            this.agregarRegistroDeUso(nuevoRegistro);
        }else{
            throw new CantidadDisponibleLimitePorDiaException("No hay más cantidad disponible por hoy!");
        }
    }
    public void resetCantidadUsadaEnElDia(){
        this.setCantidadUsadaEnElDia(0);
    }

}