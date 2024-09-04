package domain.tarjeta;

import domain.excepciones.CantidadDisponibleLimitePorDiaException;
import domain.persona.PersonaVulnerable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table (name = "tarjeta_persona_vulnerable")
public class Tarjeta {
    @Transient
    private String codigoIdentificador; //Leer commit donde se menciona la decisión del modelado del codigo de esta manera.
    @OneToOne
    @JoinColumn(name = "titular_id", referencedColumnName = "id")
    private PersonaVulnerable titular;

    private static Integer cantidadDisponiblePorDefecto = 4;
    private Integer cantidadUsadaEnElDia;
    private List<RegistroDeUso> registros; //Debe quedar registrado, cuándo la usó, y en cuál heladera.
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