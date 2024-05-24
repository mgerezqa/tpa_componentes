package entrega_1;

import domain.contacto.Email;
import domain.contacto.Telefono;
import domain.contacto.Whatsapp;
import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.usuarios.Colaborador;
import domain.usuarios.TipoRazonSocial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test_2 {
    Formulario formColabFisico, formColabJuridico;
    List<Colaborador> listaColaboradores;

    @BeforeEach
    public void init(){
        // por el momento no hay verificacion pero los campos no deben ser repetidos
        formColabFisico = new Formulario(Arrays.asList(
                new Campo("nombre"),
                new Campo("apellido"),
                new Campo("contacto"),
                new Campo("fecha de nacimiento")
        ));

        formColabJuridico = new Formulario(Arrays.asList(
                new Campo("razon social"),
                new Campo("tipo"),
                new Campo("contacto"),
                new Campo("direccion")
        ));

        // por el momento la unica forma de distinguir un campo es un string, pero
        // se buscara alguna otra forma mas segura, talves un enum de cada tipo de campo?

        formColabFisico.responderCampo("nombre", "pepe");
        formColabFisico.responderCampo("apellido", "argento");
        formColabFisico.responderCampo("contacto", new Telefono(54,11,12345678));
        formColabFisico.responderCampo("fecha de nacimiento", LocalDate.parse("2000-10-20"));

        formColabJuridico.responderCampo("razon social", "unaRazon");
        formColabJuridico.responderCampo("tipo", TipoRazonSocial.EMPRESA);
        formColabJuridico.responderCampo("contacto", new Whatsapp("+123456789012"));
        formColabJuridico.responderCampo("contacto", new Email("unMail@empresa.com"));
        formColabJuridico.responderCampo("direccion", "calle falsa 123");

        listaColaboradores = new ArrayList<>();


    }

    @Test
    public void realizarColaboracion(){

    }
}
