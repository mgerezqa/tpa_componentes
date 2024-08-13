package domain.tarjeta;

import domain.heladera.Heladera.Heladera;
import domain.usuariosNuevo.Colaborador;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class TarjetaColaborador {
    private String codigoTarjeta;
    private Colaborador unColaborador;
    private List<EventoTarjetaColab> registroEventos;

    public static TarjetaColaborador generar(Colaborador colab){
        return TarjetaColaborador
                .builder()
                .codigoTarjeta(generarCodigoTarjeta())
                .unColaborador(colab)
                .registroEventos(new ArrayList<>())
                .build();
    }

    private static String generarCodigoTarjeta(){ return "AB123456789"; }
    private void registrarEvento(EventosTarjetasColab evento, Heladera unaHeladera){
        registroEventos.add(EventoTarjetaColab.generar(evento.getDescripcion(), unaHeladera));
    }
    private void registrarEvento(EventosTarjetasColab evento){
        registroEventos.add(EventoTarjetaColab.generar(evento.getDescripcion()));
    }

    public void usarTarjeta(EventosTarjetasColab evento, Heladera unaHeladera){
        //TODO: hace algo
        registrarEvento(evento, unaHeladera);
    }
    public void registrarEnvioTarjeta(){
        registrarEvento(EventosTarjetasColab.ENTREGA);
    }
    public void registrarCreacionTarjeta(){
        registrarEvento(EventosTarjetasColab.CREACION);
    }
    public String registroEventos(){
        return registroEventos.stream().map(EventoTarjetaColab::mostrarEvento).collect(Collectors.joining(""));
    }
}
