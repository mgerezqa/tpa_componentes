package formularios;

import domain.contacto.Email;
import domain.contacto.Telefono;
import domain.contacto.Whatsapp;
import domain.donaciones.TipoContribucion;
import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.formulario.TipoCampoFormulario;
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
        formColabFisico.agregarCampo(new Campo(TipoCampoFormulario.NOMBRE, "Ingrese su nombre"));
        formColabFisico.agregarCampo(new Campo(TipoCampoFormulario.APELLIDO, "Ingrese su apellido"));
        formColabFisico.agregarCampo(new Campo(TipoCampoFormulario.TIPO_DOCUMENTO, "Seleccione su tipo de documento"));
        formColabFisico.agregarCampo(new Campo(TipoCampoFormulario.NRO_DOCUMENTO, "Ingrese su numero de documento"));
        formColabFisico.agregarCampo(new Campo(TipoCampoFormulario.CONTACTO, "Ingrese almenos 1 forma de contacto"));
        formColabFisico.agregarCampo(new Campo(TipoCampoFormulario.FORMA_CONTRIBUCION, "Seleccione su forma de contribucion"));
        // campos opcionales
        formColabFisico.agregarCampo(new Campo(TipoCampoFormulario.FECHA_NACIMIENTO, "Ingrese su fecha de nacimiento"));
        formColabFisico.agregarCampo(new Campo(TipoCampoFormulario.DIRECCION, "Ingrese su direccion"));

        formColabJuridico = new Formulario();
        // estos 5 campos son necesarios para registrar un colaborador juridico
        formColabJuridico.agregarCampo(new Campo(TipoCampoFormulario.RAZON_SOCIAL, "Ingrese su razon social"));
        formColabJuridico.agregarCampo(new Campo(TipoCampoFormulario.TIPO_JURIDICO, "Seleccione el tipo de su razon social"));
        formColabJuridico.agregarCampo(new Campo(TipoCampoFormulario.RUBRO, "Ingrese su rubro"));
        formColabJuridico.agregarCampo(new Campo(TipoCampoFormulario.CONTACTO, "Ingrese almenos 1 forma de contacto"));
        formColabJuridico.agregarCampo(new Campo(TipoCampoFormulario.FORMA_CONTRIBUCION, "Seleccione su forma de contribucion"));
        // campos opcionales
        formColabJuridico.agregarCampo(new Campo(TipoCampoFormulario.DIRECCION, "Ingrese su direccion"));

        // por el momento la unica forma de distinguir un campo es un string, pero
        // se buscara alguna otra forma mas segura, talves un enum de cada tipo de campo?

        // CADA CAMPO DEBE TENER ALMENOS 1 INSTANCIA DE RESPUESTA ASOCIADA (por verificacion si el formulario esta completo)
        // (incluso si es un campo que no tiene nada que ver con nosotros, y/o si el contenido de la respuesta es null)
        formColabFisico.responderCampo(TipoCampoFormulario.NOMBRE, "pepe");
        formColabFisico.responderCampo(TipoCampoFormulario.APELLIDO, "argento");
        formColabFisico.responderCampo(TipoCampoFormulario.TIPO_DOCUMENTO, TipoDocumento.DNI);
        formColabFisico.responderCampo(TipoCampoFormulario.NRO_DOCUMENTO, 123456789);
        formColabFisico.responderCampo(TipoCampoFormulario.CONTACTO, new Telefono(54,11,1234567));
        formColabFisico.responderCampo(TipoCampoFormulario.CONTACTO, new Email("mailPersonal@fisico.com"));
        formColabFisico.responderCampo(TipoCampoFormulario.FORMA_CONTRIBUCION, TipoContribucion.DINERO);
        formColabFisico.responderCampo(TipoCampoFormulario.FORMA_CONTRIBUCION, TipoContribucion.VIANDA);
        formColabFisico.responderCampo(TipoCampoFormulario.FORMA_CONTRIBUCION, TipoContribucion.TARJETA);
        formColabFisico.responderCampo(TipoCampoFormulario.DIRECCION, "calle falsa 123");
        formColabFisico.responderCampo(TipoCampoFormulario.FECHA_NACIMIENTO, LocalDate.parse("2000-10-20"));

        formColabJuridico.responderCampo(TipoCampoFormulario.RAZON_SOCIAL, "unaRazon");
        formColabJuridico.responderCampo(TipoCampoFormulario.TIPO_JURIDICO, TipoRazonSocial.EMPRESA);
        formColabJuridico.responderCampo(TipoCampoFormulario.RUBRO, Rubro.FINANZAS);
        formColabJuridico.responderCampo(TipoCampoFormulario.CONTACTO, new Whatsapp("+123456789012"));
        formColabJuridico.responderCampo(TipoCampoFormulario.CONTACTO, new Email("unMail@empresa.com"));
        formColabJuridico.responderCampo(TipoCampoFormulario.FORMA_CONTRIBUCION, TipoContribucion.DINERO);
        formColabJuridico.responderCampo(TipoCampoFormulario.FORMA_CONTRIBUCION, TipoContribucion.HELADERA);
        formColabJuridico.responderCampo(TipoCampoFormulario.DIRECCION, "mozart 900");

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
