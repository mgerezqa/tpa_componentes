package formularios;

import domain.contacto.Email;
import domain.contacto.Telefono;
import domain.contacto.Whatsapp;
import domain.donaciones.TipoContribucion;
import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.usuarios.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TestColaborador {

    Formulario formColabFisico, formColabJuridico;

    @BeforeEach
    public void init(){
        formColabFisico = new Formulario();
        // estos 6 campos son necesarios para registrar un colaborador fisico
        formColabFisico.agregarCampo(new Campo("nombre"));
        formColabFisico.agregarCampo(new Campo("apellido"));
        formColabFisico.agregarCampo(new Campo("tipo de documento"));
        formColabFisico.agregarCampo(new Campo("numero de documento"));
        formColabFisico.agregarCampo(new Campo("contacto"));
        formColabFisico.agregarCampo(new Campo("forma de contribucion"));
        // campos opcionales (si comentas los campos y respuestas aun funciona)
        formColabFisico.agregarCampo(new Campo("fecha de nacimiento"));
        formColabFisico.agregarCampo(new Campo("direccion"));

        formColabJuridico = new Formulario();
        // estos 5 campos son necesarios para registrar un colaborador juridico
        formColabJuridico.agregarCampo(new Campo("razon social"));
        formColabJuridico.agregarCampo(new Campo("tipo"));
        formColabJuridico.agregarCampo(new Campo("rubro"));
        formColabJuridico.agregarCampo(new Campo("contacto"));
        formColabJuridico.agregarCampo(new Campo("forma de contribucion"));
        // campos opcionales (si comentas los campos y respuestas aun funciona)
        formColabJuridico.agregarCampo(new Campo("direccion"));

        // por el momento la unica forma de distinguir un campo es un string, pero
        // se buscara alguna otra forma mas segura, talves un enum de cada tipo de campo?

        // CADA CAMPO DEBE TENER ALMENOS 1 INSTANCIA DE RESPUESTA ASOCIADA (por verificacion si el formulario esta completo)
        // (incluso si es un campo que no tiene nada que ver con nosotros, y/o si el contenido de la respuesta es null)
        formColabFisico.responderCampo("nombre", "pepe");
        formColabFisico.responderCampo("apellido", "argento");
        formColabFisico.responderCampo("tipo de documento", TipoDocumento.DNI);
        formColabFisico.responderCampo("numero de documento", 123456789);
        formColabFisico.responderCampo("contacto", new Telefono(54,11,1234567));
        formColabFisico.responderCampo("contacto", new Email("mailPersonal@fisico.com"));
        formColabFisico.responderCampo("forma de contribucion", TipoContribucion.DINERO);
        formColabFisico.responderCampo("forma de contribucion", TipoContribucion.VIANDA);
        formColabFisico.responderCampo("forma de contribucion", TipoContribucion.TARJETA);
        formColabFisico.responderCampo("direccion", "calle falsa 123");
        formColabFisico.responderCampo("fecha de nacimiento", LocalDate.parse("2000-10-20"));

        formColabJuridico.responderCampo("razon social", "unaRazon");
        formColabJuridico.responderCampo("tipo", TipoRazonSocial.EMPRESA);
        formColabJuridico.responderCampo("rubro", Rubro.FINANZAS);
        formColabJuridico.responderCampo("contacto", new Whatsapp("+123456789012"));
        formColabJuridico.responderCampo("contacto", new Email("unMail@empresa.com"));
        formColabJuridico.responderCampo("forma de contribucion", TipoContribucion.DINERO);
        formColabJuridico.responderCampo("forma de contribucion", TipoContribucion.HELADERA);
        formColabJuridico.responderCampo("direccion", "mozart 900");
    }

    @Test
    public void cargarColaboradores(){
        ColaboradorFisico unColabFisico = new ColaboradorFisico(formColabFisico);
        ColaboradorJuridico unColabJuridico = new ColaboradorJuridico(formColabJuridico);

        System.out.println("Colaboradores: ");
        System.out.println(unColabFisico);
        System.out.println(unColabJuridico);
    }
}
