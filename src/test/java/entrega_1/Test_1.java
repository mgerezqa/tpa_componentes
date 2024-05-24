package entrega_1;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telefono;
import domain.contacto.Whatsapp;
import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.ColaboradorJuridico;
import domain.usuarios.TipoRazonSocial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Test_1 {
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

    // - - - - punto 1 - - - -
    @Test
    public void alta_baja_colaboradores(){
        // agrego a la lista de colaboradores, como la lista es de tipo Colaborador, deberia solo
        // usar los mensajes que conoce dicha clase
        listaColaboradores.add(new ColaboradorFisico(formColabFisico));
        listaColaboradores.add(new ColaboradorJuridico(formColabJuridico));
        // por defecto cuando se crea un colaborador se inicia como dado de baja
        listaColaboradores.stream().forEach(colaborador -> assertFalse(colaborador.estaActivo()));
        // doy de alta a todos los colaboradores
        listaColaboradores.stream().forEach(Colaborador::darAlta);
        // chequeo que esten en alta
        listaColaboradores.stream().forEach(colaborador -> assertTrue(colaborador.estaActivo()));
        // doy de baja a todos
        listaColaboradores.stream().forEach(Colaborador::darBaja);
        // vuelvo a chequear
        listaColaboradores.stream().forEach(colaborador -> assertFalse(colaborador.estaActivo()));
        System.out.println(listaColaboradores);
    }

    @Test
    public void modificar_colaboradores(){
        // por el momento solo se puede modificar de manera particular a cada colaborador con el setter
        // pisando el valor del campo, se plantea reutilizar el mecanismo que aplica en carga por formulario
        // pero requiere feedback y posiblemente hardcodeo en cierto punto
        ColaboradorFisico unColab = new ColaboradorFisico(formColabFisico);
        System.out.println(" - colaborador original: ");
        System.out.println(unColab);
        System.out.println(" - se cambia el nombre a 'coqui': ");
        unColab.setNombre("coqui");
        System.out.println(unColab);
        assertEquals("coqui", unColab.getNombre());
        System.out.println(" - se cambia la lista de contactos a otra lista con un mail: ");
        // en si agregar/modificar/eliminar donaciones/contactos/listas cuentan como acciones de abm
        // aca tomo 'modificar' como reemplazar un campo de valor por otro
        List<MedioDeContacto> nuevaLista = new ArrayList<>();
        nuevaLista.add(new Email("casados@conhijos.com"));
        unColab.setMedioContacto(nuevaLista);
        System.out.println(unColab);
        assertTrue(unColab.mostrarInformacionDeContacto().contains("casados@conhijos.com"));
        System.out.println(" - tambien se puede usar metodo para agregar un medio de contacto");
        unColab.agregarContacto(new Whatsapp("+541234567890"));
        assertTrue(unColab.mostrarInformacionDeContacto().contains("+541234567890"));
        System.out.println(unColab);
        System.out.println(" - y otro para sacar un contacto por su descripcion");
        // si no encuentra la descripcion, no hace nada, no lanza excepcion
        unColab.removerContacto("casados@conhijos.com");
        assertFalse(unColab.mostrarInformacionDeContacto().contains("casados@conhijos.com"));
        System.out.println(unColab);
    }
}
