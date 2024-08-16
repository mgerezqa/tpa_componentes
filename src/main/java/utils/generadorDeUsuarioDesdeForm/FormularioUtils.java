package utils.generadorDeUsuarioDesdeForm;

import domain.contacto.Email;
import domain.contacto.Telegram;
import domain.contacto.Whatsapp;
import domain.formulario.Formulario;
import domain.usuarios.*;

public class FormularioUtils {

    public static ColaboradorFisico crearColaboradorFisico(Formulario formulario){

        ColaboradorFisico colaborador = new ColaboradorFisico(
                formulario.obtenerRespuesta("Nombre"),
                formulario.obtenerRespuesta("Apellido")
        );

        agregarMedioDeContacto(formulario, colaborador);
        //verificar que el colaborador tenga al menos 1 medio de contacto
        //validarCantidadDeMediosDeContacto(colaborador);

        return colaborador;

    }



    public static ColaboradorJuridico crearColaboradorJuridico(Formulario formulario){

        ColaboradorJuridico colaborador = new ColaboradorJuridico(
                formulario.obtenerRespuesta("Razon Social"),
                formulario.obtenerRespuesta("Tipo de Razon Social"),
                formulario.obtenerRespuesta("Rubro")
        );

        agregarMedioDeContacto(formulario,colaborador);
        validarCantidadDeMediosDeContacto(colaborador);

        return colaborador;
    }


    public static void agregarMedioDeContacto(Formulario formulario, Colaborador colaborador){
        if(formulario.getCampos().containsKey("Email")){
            String email = formulario.obtenerRespuesta("Email");
            colaborador.agregarMedioDeContacto(new Email(email));
        }

        if (formulario.getCampos().containsKey("Telegram")){
            String numero = formulario.obtenerRespuesta("Telegram");
            colaborador.agregarMedioDeContacto(new Telegram(numero));
        }

        if (formulario.getCampos().containsKey("Whatsapp")){
            String numero = formulario.obtenerRespuesta("Whatsapp");
            colaborador.agregarMedioDeContacto(new Whatsapp(numero));
        }
    }

    public static void validarCantidadDeMediosDeContacto(Colaborador colaborador){
        if((colaborador.getMediosDeContacto().isEmpty()))
            throw new RuntimeException("El colaborador debe tener al menos 1 medio de contacot");
    }

}
