package domain.tarjeta;

import domain.excepciones.CantidadDisponibleLimitePorDiaException;
import domain.persona.PersonaVulnerable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table (name = "tarjeta_persona_vulnerable")
@NoArgsConstructor
public class Tarjeta {
    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "domain.tarjeta.generadorUUID")
    private String codigoIdentificador;
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona_vulnerable", referencedColumnName = "id")
    private PersonaVulnerable titular;
    @Transient
    private static Integer cantidadDisponiblePorDefecto = 4;
    @Column(name = "cantidad_usada_dia")
    private Integer cantidadUsadaEnElDia;
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tarjeta")
    private List<RegistroDeUso> registros;
    @Column(name = "fecha_inicio_funcionamiento")
    private LocalDateTime fechaInicioDeFuncionamiento;

    public Tarjeta(PersonaVulnerable titular){
        this.titular = titular;
        this.cantidadUsadaEnElDia = 0;
        this.registros = new ArrayList<>();
        this.fechaInicioDeFuncionamiento = LocalDateTime.now();
    }
    public int cantidadDisponiblePorMenores(){
        return 2*this.getTitular().cantidadDeMenoresACargo();
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