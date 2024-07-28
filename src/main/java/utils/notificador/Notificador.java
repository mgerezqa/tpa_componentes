package utils.notificador;

import domain.contacto.MedioDeContacto;
import domain.heladera.Heladera.Heladera;
import domain.suscripciones.TipoDeSuscripcion;
import domain.usuarios.ColaboradorFisico;
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



    public void habilitarNotificacion(ColaboradorFisico colaborador, MedioDeContacto medioDeContacto){
        if(colaborador.getMediosDeContacto().isEmpty())
            throw new RuntimeException("No tiene medios de contactos agregados");
        colaborador.getMediosDeContacto().forEach(medio -> {
            if(medio.equals(medioDeContacto)){
                medio.setNotificar(true);
            }
        });
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
