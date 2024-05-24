package test_gral;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.formulario.UnaRespuesta;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestForm {
    /*
    @Test
    public void testRespuesta(){
        String nombre = "pablo";
        Integer edad = 18;
        LocalDate fechaNac = LocalDate.parse("2000-01-22");
        LocalDate fechaHoy = LocalDate.now();

        UnaRespuesta respuestaNombre = new UnaRespuestaTexto(new Campo("nombre", TipoEntrada.ENTRADA_TEXTO));
        UnaRespuesta respuestaEdad = new UnaRespuestaNumero(new Campo("edad", TipoEntrada.ENTRADA_NUMERICA));
        UnaRespuesta respuestaNac = new UnaRespuestaFecha(new Campo("fecha de nacimiento", TipoEntrada.ENTRADA_TIPO_FECHA));

        respuestaEdad.agregarRespuesta(18);
        respuestaNombre.agregarRespuesta("pablo");
        respuestaNac.agregarRespuesta(LocalDate.parse("2000-01-22"));

        assertEquals(edad, respuestaEdad.obtenerRespuesta());
        assertEquals(nombre, respuestaNombre.obtenerRespuesta());
        assertEquals(fechaNac, respuestaNac.obtenerRespuesta());
        assertNotEquals(fechaHoy, respuestaNac.obtenerRespuesta());
    }
    */
    @Test
    public void testRespuestaGenerica(){
        String nombre = "pablo";
        Integer edad = 18;
        LocalDate fechaNac = LocalDate.parse("2000-01-22");
        LocalDate fechaHoy = LocalDate.now();

        UnaRespuesta<String> respuestaNombre = new UnaRespuesta<>(new Campo("nombre"));
        UnaRespuesta<Integer> respuestaEdad = new UnaRespuesta<>(new Campo("edad"));
        UnaRespuesta<LocalDate> respuestaNac = new UnaRespuesta<>(new Campo("fecha de nacimiento"));

        respuestaEdad.responder(18);
        respuestaNombre.responder("pablo");
        respuestaNac.responder(LocalDate.parse("2000-01-22"));

        assertEquals(edad, respuestaEdad.getRespuesta());
        assertEquals(nombre, respuestaNombre.getRespuesta());
        assertEquals(fechaNac, respuestaNac.getRespuesta());
        assertNotEquals(fechaHoy, respuestaNac.getRespuesta());
    }

    @Test
    public void obtenerFormulario(){
        Formulario unFormulario = new Formulario();
        String nombre = "pablo";
        Integer edad = 18;
        LocalDate fechaNac = LocalDate.parse("2000-01-22");
        LocalDate fechaHoy = LocalDate.now();
        Email unMail = new Email("unMail@dominio.com");

        unFormulario.agregarCampo(new Campo("nombre"));
        unFormulario.agregarCampo(new Campo("edad"));
        unFormulario.agregarCampo(new Campo("fecha de nacimiento"));

        unFormulario.agregarCampo(new Campo("contacto"));

        unFormulario.responderCampo("nombre", "pablo");
        unFormulario.responderCampo("edad", 18);
        unFormulario.responderCampo("fecha de nacimiento", LocalDate.parse("2000-01-22"));
        System.out.println(unFormulario.obtenerRespuesta("nombre").getClass());
        System.out.println(unFormulario.obtenerRespuesta("edad").getClass());
        System.out.println(unFormulario.obtenerRespuesta("fecha de nacimiento").getClass());
        //System.out.println(unFormulario.obtenerRespuesta("apellido").getClass());

        unFormulario.responderCampo("contacto", unMail);
        MedioDeContacto unContacto = unFormulario.obtenerRespuesta("contacto");
        System.out.println(unFormulario.obtenerRespuesta("contacto").getClass());
        System.out.println(unFormulario.obtenerRespuesta("contacto") instanceof MedioDeContacto);

        assertEquals(nombre, unFormulario.obtenerRespuesta("nombre"));
        assertEquals(edad, unFormulario.obtenerRespuesta("edad"));
        assertEquals(fechaNac, unFormulario.obtenerRespuesta("fecha de nacimiento"));
        assertNotEquals(fechaHoy, unFormulario.obtenerRespuesta("fecha de nacimiento"));
    }
}
