package domain.tarjeta;

import domain.persona.PersonaVulnerable;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Tarjeta {
    String codigoIdentificador; // Código alfanumérico de 11 caracteres
    PersonaVulnerable titular;
    private int cantidadDisponiblePorDefecto = 4;
    private int cantidadUsadaEnElDia;
    private List<RegistroDeUso> registros; //Debe quedar registrado, cuándo la usó, y en cuál heladera.
    //Constructor
    public Tarjeta(PersonaVulnerable titular){
        this.titular = titular;
        this.cantidadUsadaEnElDia = 0;
        this.registros = new ArrayList<>();
    }
    public int cantidadDisponiblePorMenores(){
        return 2*this.getTitular().cantidadDeMenoresACargo();
    }
    public int cantidadLimiteDisponiblePorDia(){
        return this.getCantidadDisponiblePorDefecto() + this.cantidadDisponiblePorMenores();
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
    public void usoDeTarjeta(RegistroDeUso nuevoRegistro) throws Exception{
            if(this.tieneCantidadDisponible()){
                this.aumentarCantidadDeUsoEnElDia();
                this.agregarRegistroDeUso(nuevoRegistro);
            }else{
                throw new Exception("No hay más cantidad disponible por hoy!");
            }
    }
}