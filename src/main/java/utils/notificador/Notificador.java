package utils.notificador;

import domain.contacto.MedioDeContacto;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Alerta;
import domain.incidentes.FallaTecnica;
import domain.incidentes.Incidente;
import domain.suscripciones.TipoDeSuscripcion;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.Tecnico;
import jakarta.mail.MessagingException;

public class Notificador {

    public void notificar(ColaboradorFisico colaborador, Heladera heladera, TipoDeSuscripcion notificacion){
        colaborador.getMediosDeContacto().forEach(medio -> {
            if(medio.isNotificar()){
                try {
                    medio.enviarMensaje(colaborador, heladera, notificacion);
                    //registrar mensaje enviado en logger
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                colaborador.setNotificacionRecibida(true);
               }
        });
    }

    public void notificar(Tecnico tecnico, Alerta alerta){
        tecnico.getMediosDeContacto().forEach(medio -> {
            if(medio.isNotificar()){
                try {
                    medio.enviarMensaje(tecnico, alerta);
                //registrar mensaje enviado en logger
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                tecnico.setNotificacionRecibida(true);
            }
        });
    }

    public void notificar(Tecnico tecnico, FallaTecnica fallaTecnica){
        tecnico.getMediosDeContacto().forEach(medio -> {
            if(medio.isNotificar()){
                try {
                    medio.enviarMensaje(tecnico, fallaTecnica);
                //registrar mensaje enviado en logger
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                tecnico.setNotificacionRecibida(true);
            }
        });
    }

    public void habilitarNotificacion(ColaboradorFisico colaborador, MedioDeContacto medioDeContacto){
        if(colaborador.getMediosDeContacto().isEmpty())
            throw new RuntimeException("No tiene medios de contactos agregados");
        colaborador.getMediosDeContacto().forEach(medio -> {
            if(medio.equals(medioDeContacto)){
                medio.setNotificar(true);
            }
        });
    }

    public void habilitarNotificacion(Tecnico tecnico, MedioDeContacto medioDeContacto){
        habilitarNotificacion(tecnico, medioDeContacto);
    }

    public void deshabilitarNotificacion(Tecnico tecnico, MedioDeContacto medioDeContacto){
        deshabilitarNotificacion(tecnico, medioDeContacto);
    }

    public  void deshabilitarNotificacion(ColaboradorFisico colaborador, MedioDeContacto medioDeContacto){
        if(colaborador.getMediosDeContacto().isEmpty())
            throw new RuntimeException("No tiene medios de contactos agregados");
        colaborador.getMediosDeContacto().forEach(medio -> {
            if(medio.equals(medioDeContacto)){
                medio.setNotificar(false);
            }
        });

    }


}
