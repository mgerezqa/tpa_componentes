package utils.notificador;

import domain.contacto.eTipoMedioDeContacto;
import domain.usuarios.Colaborador;

public class Notificador {


    public static void habilitarNotificacion(Colaborador colaborador, String medioDeContacto){
        colaborador.getMediosDeContacto().forEach(medio -> {
            if(medio.tipoMedioDeContacto().equals(medioDeContacto)){
                medio.setNotificar(true);
            }
        });
    }

    public static void habilitarNotificacion(Colaborador colaborador, eTipoMedioDeContacto medioDeContacto){
        colaborador.getMediosDeContacto().forEach(medio -> {
            if(medio.tipoMedioDeContacto().equals(medioDeContacto.name())){
                medio.setNotificar(true);
            }
        });
    }

    public static void deshabilitarNotificacion(Colaborador colaborador, String medioDeContacto){
        colaborador.getMediosDeContacto().forEach(medio -> {
            if(medio.tipoMedioDeContacto().equals(medioDeContacto)){
                medio.setNotificar(false);
            }
        });
    }

    public static void deshabilitarNotificacion(Colaborador colaborador, eTipoMedioDeContacto medioDeContacto){
        colaborador.getMediosDeContacto().forEach(medio -> {
            if(medio.tipoMedioDeContacto().equals(medioDeContacto.name())){
                medio.setNotificar(false);
            }
        });
    }





}
