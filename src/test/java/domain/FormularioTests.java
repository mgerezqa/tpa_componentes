package domain;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telefono;
import domain.contacto.Whatsapp;
import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.formulario.TipoCampo;
import domain.usuarios.ColaboradorFisico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormularioTests {
    private ColaboradorFisico lalo;
    private MedioDeContacto laloEmail;
    private MedioDeContacto laloTelefono;
    private MedioDeContacto laloWhatsapp;
    private Formulario formulario;
    private LocalDate fechaActual;


    @BeforeEach
    public void setUp() {
        //Formulario
        this.formulario = new Formulario();
        this.fechaActual = LocalDate.now();

    }

    @Test
    @DisplayName("Un formulario admite la creación de campos dinámicos")
    public void camposDinamicos(){
        formulario.agregarCampo("fecha",new Campo(TipoCampo.CAMPO_FECHA));
        formulario.agregarCampo("texto libre",new Campo(TipoCampo.CAMPO_TEXTO));
        formulario.agregarCampo("numerico",new Campo(TipoCampo.CAMPO_NUMERICO));

        formulario.cargarValor("fecha","2024-01-17");
        formulario.cargarValor("texto libre","Hola mundo");
        formulario.cargarValor("numerico","1234");
        assertEquals(3, formulario.cantCampos());

    }


    @Test
    @DisplayName("El formulario acepta la carga de preguntas de multiples respuestas")
    public void unFormularioTieneUnaEntradaDeMultiplesRespuestas(){
        formulario.agregarCampo("idiomas", new Campo(TipoCampo.CAMPO_MULTIPLE));

        formulario.cargarValor("idiomas", "español");
        formulario.cargarValor("idiomas", "ingles");
        formulario.cargarValor("idiomas", "frances");

        assertEquals(3, formulario.obtenerValor("idiomas").split(",").length);
        System.out.println(formulario.obtenerValor("idiomas"));
    }

    @Test
    @DisplayName("El formulario acepta la carga de preguntas con fecha")
    public void unFormularioAdmitePreguntasDeTipoFecha(){
        formulario.agregarCampo("fecha de hoy", new Campo(TipoCampo.CAMPO_FECHA));
        formulario.cargarValor("fecha de hoy", fechaActual.toString());

        assertEquals(LocalDate.now().toString(), formulario.obtenerValor("fecha de hoy"));
        System.out.println(formulario.obtenerValor("fecha de hoy"));
    }


}
