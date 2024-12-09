package domain.usuarios;

import domain.contacto.Email;
import domain.contacto.Telegram;
import domain.contacto.Whatsapp;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class ColaboradorFisicoFactory {

    public static ColaboradorFisico create(String nombre ,String apellido) {
        return ColaboradorFisico.builder()
                .notificacionRecibida(false)
                .nombre(nombre)
                .apellido(apellido)
                .activo(true)
                .mediosDeContacto(new HashSet<>())
                .viandasDonadas(new ArrayList<>())
                .direcciones(new ArrayList<>())
                .build();
    }
    public static ColaboradorFisico create(String nombre ,String apellido,String domicilio) {
        ColaboradorFisico colaboradorFisico  = create(nombre, apellido);
        if (domicilio != null && !domicilio.isEmpty()) {
            Calle calle = Calle.builder().nombre(domicilio).build();
            Ubicacion ubicacion = Ubicacion.builder().calle(calle).build();
            colaboradorFisico.agregarDireccion(ubicacion);
        }
        return colaboradorFisico;
    }
    public static ColaboradorFisico create(String nombre ,String apellido,String domicilio, String nacimiento) {
        ColaboradorFisico colaboradorFisico  = create(nombre, apellido,domicilio);
        if (nacimiento != null && !nacimiento.isEmpty()) {
            LocalDate fechaNacimiento = LocalDate.parse(nacimiento);
            colaboradorFisico.setNacimiento(fechaNacimiento);
        }
        return colaboradorFisico;
    }
    public static ColaboradorFisico create(String nombre ,String apellido,String domicilio, String nacimiento,String email, String wsp,String telegram) {
        ColaboradorFisico colaboradorFisico  = create(nombre, apellido,domicilio,nacimiento);
        agregarMedioContacto(colaboradorFisico,email,wsp,telegram);
        return colaboradorFisico;
    }

    public static ColaboradorFisico agregarMedioContacto(ColaboradorFisico colaborador
            ,String email, String whatsapp, String telegram) {

        if (email != null && !email.trim().isEmpty()) {
            colaborador.agregarMedioDeContacto(new Email(email));
        }

        if (whatsapp != null && !whatsapp.trim().isEmpty()) {
            colaborador.agregarMedioDeContacto(new Whatsapp(whatsapp));
        }

        if (telegram != null && !telegram.trim().isEmpty()) {
            colaborador.agregarMedioDeContacto(new Telegram(telegram));
        }
        return colaborador;
    }


}
