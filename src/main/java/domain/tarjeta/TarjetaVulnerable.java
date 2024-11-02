package domain.tarjeta;

import domain.excepciones.CantidadDisponibleLimitePorDiaException;
import domain.persona.PersonaVulnerable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@DiscriminatorValue("vulnerable")
public class TarjetaVulnerable extends Tarjeta {
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona_vulnerable")
    private PersonaVulnerable vulnerable;
    private static Integer cantidadDisponiblePorDefecto = 4;


    public int cantidadDisponiblePorMenores(){
        return 2*this.getVulnerable().cantidadDeMenoresACargo();
    }
    public int cantidadLimiteDisponiblePorDia(){
        return cantidadDisponiblePorDefecto + this.cantidadDisponiblePorMenores();
    }

    public boolean tieneCantidadDisponible(){
        return this.getCantidadUsadaEnElDia() < this.cantidadLimiteDisponiblePorDia();
    }
    public void usoDeTarjeta(RegistroDeUso nuevoRegistro){
        if(this.tieneCantidadDisponible()){
            this.aumentarCantidadDeUsoEnElDia();
            this.agregarRegistroDeUso(nuevoRegistro);
        }else{
            throw new CantidadDisponibleLimitePorDiaException("No hay más cantidad disponible por hoy!");
        }
    }
}
