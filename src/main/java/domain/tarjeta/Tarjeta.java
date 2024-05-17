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
    private int cantidadTotalDisponibleActualmente;
    private List<RegistroDeUso> registros; //Debe quedar registrado, cuándo la usó, y en cuál heladera.
    //Constructor
    public Tarjeta(PersonaVulnerable titular){
        this.titular = titular;
        this.cantidadTotalDisponibleActualmente = this.getCantidadDisponiblePorDefecto() + this.cantidadDisponiblePorMenores();
        this.registros = new ArrayList<>();
    }

    public int cantidadDisponiblePorMenores(){
        return 2*this.getTitular().cantidadDeMenoresACargo();
    }
    public boolean tieneCantidadDisponible(){
        return this.getCantidadTotalDisponibleActualmente() > 0;
    }
    public void agregarRegistroDeUso(RegistroDeUso nuevoRegistro){
        this.getRegistros().add(nuevoRegistro);
    }
    public void usoDeTarjeta(RegistroDeUso nuevoRegistro){
        this.disminuirCantidadDisponible();
        this.agregarRegistroDeUso(nuevoRegistro);
    }
    private void disminuirCantidadDisponible() {
        this.setCantidadTotalDisponibleActualmente(this.getCantidadTotalDisponibleActualmente() - 1);
    }
}