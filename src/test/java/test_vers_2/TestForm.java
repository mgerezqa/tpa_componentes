package test_vers_2;

import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.formulario.TipoEntrada;
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

        UnaRespuesta<String> respuestaNombre = new UnaRespuesta<>(new Campo("nombre", TipoEntrada.ENTRADA_TEXTO));
        UnaRespuesta<Integer> respuestaEdad = new UnaRespuesta<>(new Campo("edad", TipoEntrada.ENTRADA_NUMERICA));
        UnaRespuesta<LocalDate> respuestaNac = new UnaRespuesta<>(new Campo("fecha de nacimiento", TipoEntrada.ENTRADA_TIPO_FECHA));

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

        unFormulario.agregarCampo(new Campo("nombre", TipoEntrada.ENTRADA_TEXTO));
        unFormulario.agregarCampo(new Campo("edad", TipoEntrada.ENTRADA_NUMERICA));
        unFormulario.agregarCampo(new Campo("fecha de nacimiento", TipoEntrada.ENTRADA_TIPO_FECHA));
        unFormulario.responderCampo("nombre", "pablo");
        unFormulario.responderCampo("edad", 18);
        unFormulario.responderCampo("fecha de nacimiento", LocalDate.parse("2000-01-22"));
        System.out.println(unFormulario.obtenerRespuesta("nombre").getClass());
        System.out.println(unFormulario.obtenerRespuesta("edad").getClass());
        System.out.println(unFormulario.obtenerRespuesta("fecha de nacimiento").getClass());
        //System.out.println(unFormulario.obtenerRespuesta("apellido").getClass());

        assertEquals(nombre, unFormulario.obtenerRespuesta("nombre"));
        assertEquals(edad, unFormulario.obtenerRespuesta("edad"));
        assertEquals(fechaNac, unFormulario.obtenerRespuesta("fecha de nacimiento"));
        assertNotEquals(fechaHoy, unFormulario.obtenerRespuesta("fecha de nacimiento"));
    }
}
