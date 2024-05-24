package entrega_1;

import domain.contacto.Email;
import domain.contacto.Telefono;
import domain.contacto.Whatsapp;
import domain.donaciones.*;
import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.ColaboradorJuridico;
import domain.usuarios.TipoRazonSocial;
import domain.vianda.Vianda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test_2 {
    Formulario formColabFisico, formColabJuridico;
    ColaboradorFisico unColabFisico;
    ColaboradorJuridico unColabJuridico;
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

        // puede donar: dinero, vianda, reparto
        unColabFisico = new ColaboradorFisico(formColabFisico);
        // puede donar: dinero, heladera
        unColabJuridico = new ColaboradorJuridico(formColabJuridico);
    }

    @Test
    public void realizarColaboracion(){
        // la accion de realizar una colaboracion, dado que no realizamos la accion, sino que funciona como
        // un registro de que existio, solamente guarda una descripcion de la donacion, las entidades
        // involucradas y el colaborador que la hizo.
        Vianda unaVianda = new Vianda("sanguche de mila", LocalDate.now(), null, null, 2000, 400);

        Donacion unaDonacionDinero = new DonacionDinero(1000, FrecuenciaDeDonacion.FRECUENCIA_SEMANAL, LocalDate.now());
        Donacion otraDonacionDiero = new DonacionDinero(2000, FrecuenciaDeDonacion.FRECUENCIA_MENSUAL, LocalDate.now());
        Donacion unaDonacionVianda = new DonacionVianda(unaVianda);
        // para abstraerme de registrar heladeras, paso null. para este test no afecta
        Donacion unaDonacioHeladera = new DonacionHeladera(null);

        System.out.println("teniendo creado 4 donaciones distintas (dinerox2, vianda, heladera), las registro");
        System.out.println(" - registro las donaciones de dinero a un colaborador fisico y juridico: ");
        unaDonacionDinero.registrarDonacion(unColabFisico);
        otraDonacionDiero.registrarDonacion(unColabJuridico);
        assertEquals(unColabFisico, unaDonacionDinero.getColaboradorQueRealizo());
        assertEquals(unColabJuridico, otraDonacionDiero.getColaboradorQueRealizo());
        System.out.println("Donacion de dinero 1 realizada por: "+unaDonacionDinero.getColaboradorQueRealizo());
        System.out.println("Donacion de dinero 2 realizada por: "+otraDonacionDiero.getColaboradorQueRealizo());

        System.out.println(" - registro la donacion de un reparto a un colaborador fisico");
        unaDonacionVianda.registrarDonacion(unColabFisico);
        assertEquals(unColabFisico, unaDonacionDinero.getColaboradorQueRealizo());
        System.out.println("Donacion de reparto realizada por: "+unaDonacionVianda.getColaboradorQueRealizo());
        System.out.println(" - registro la donacion de una heladera a un colaborador juridico");
        unaDonacioHeladera.registrarDonacion(unColabJuridico);
        assertEquals(unColabJuridico, unaDonacioHeladera.getColaboradorQueRealizo());
        System.out.println("Donacion de heladera realizada por: "+unaDonacioHeladera.getColaboradorQueRealizo());
        System.out.println(" - para consultar las donaciones realizadas por un colaborador, busco dentro de una lista de donaciones los que haya realizado un colaborador");
        List<Donacion> listaDonaciones = new ArrayList<>(Arrays.asList(unaDonacionVianda, unaDonacioHeladera, unaDonacionDinero, otraDonacionDiero));
        List<Donacion> donacionesFisico = listaDonaciones.stream().filter(donacion -> donacion.getColaboradorQueRealizo().equals(unColabFisico)).collect(Collectors.toList());
        List<Donacion> donacionesJuridico = listaDonaciones.stream().filter(donacion -> donacion.getColaboradorQueRealizo().equals(unColabJuridico)).collect(Collectors.toList());
        System.out.println(" * Donaciones realiazadas por un colaborador fisico: ");
        System.out.println(donacionesFisico.stream().map(Donacion::getClass).collect(Collectors.toList()));
        System.out.println(" * Donaciones realiazadas por un colaborador juridico: ");
        System.out.println(donacionesJuridico.stream().map(Donacion::getClass).collect(Collectors.toList()));
        System.out.println(" * se devuelve en formato clases porque mostrar toda la donacion es muy largo");
    }
}
