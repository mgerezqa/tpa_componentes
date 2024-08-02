package domain;

import domain.contacto.MedioDeContacto;
import domain.contacto.eTipoMedioDeContacto;
import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.formulario.eTipoCampo;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.ColaboradorJuridico;
import domain.usuarios.Rubro;
import domain.usuarios.TipoRazonSocial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.generadorDeUsuarioDesdeForm.FormularioColaboradorFisico;
import utils.generadorDeUsuarioDesdeForm.FormularioColaboradorJuridico;
import utils.generadorDeUsuarioDesdeForm.FormularioUtils;
import utils.notificador.Notificador;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        formulario.agregarCampo("fecha",new Campo(eTipoCampo.CAMPO_FECHA));
        formulario.agregarCampo("texto libre",new Campo(eTipoCampo.CAMPO_TEXTO));
        formulario.agregarCampo("numerico",new Campo(eTipoCampo.CAMPO_NUMERICO));

        formulario.ingresarRespuesta("fecha","2024-01-17");
        formulario.ingresarRespuesta("texto libre","Hola mundo");
        formulario.ingresarRespuesta("numerico","1234");
        assertEquals(3, formulario.cantCampos());

    }


    @Test
    @DisplayName("El formulario acepta la carga de preguntas de multiples respuestas")
    public void unFormularioTieneUnaEntradaDeMultiplesRespuestas(){
        formulario.agregarCampo("idiomas", new Campo(eTipoCampo.CAMPO_MULTIPLE));

        formulario.ingresarRespuesta("idiomas", "español");
        formulario.ingresarRespuesta("idiomas", "ingles");
        formulario.ingresarRespuesta("idiomas", "frances");

        assertEquals(3, formulario.obtenerRespuesta("idiomas").split(",").length);
        System.out.println(formulario.obtenerRespuesta("idiomas"));
    }

    @Test
    @DisplayName("El formulario acepta la carga de preguntas con fecha")
    public void unFormularioAdmitePreguntasDeTipoFecha(){
        formulario.agregarCampo("fecha de hoy", new Campo(eTipoCampo.CAMPO_FECHA));
        formulario.ingresarRespuesta("fecha de hoy", fechaActual.toString());

        assertEquals(LocalDate.now().toString(), formulario.obtenerRespuesta("fecha de hoy"));
        System.out.println(formulario.obtenerRespuesta("fecha de hoy"));
    }

    @Test
    @DisplayName("El formulario acepta entradas de CUIT")
    public void unFormularioAdmiteIngresoDeCuil(){
        formulario.agregarCampo("Cuit",new Campo(eTipoCampo.CAMPO_CUIT));
        formulario.ingresarRespuesta("Cuit", "20-40333666-6");

    }

    @Test
    @DisplayName("El formulario admite entradas de medio de contacto")
    public void ingresarMediosDeContacto(){

        formulario.agregarCampo("Nombre",new Campo(eTipoCampo.CAMPO_NOMBRE));
        formulario.ingresarRespuesta("Nombre","Pepe");
        formulario.agregarCampo("Apellido",new Campo(eTipoCampo.CAMPO_NOMBRE));
        formulario.ingresarRespuesta("Apellido","Menz");
        formulario.agregarCampo(eTipoMedioDeContacto.TELEGRAM,new Campo(eTipoCampo.CAMPO_TELEFONO));
        formulario.ingresarRespuesta(eTipoMedioDeContacto.TELEGRAM,"+5491165974084");
        formulario.agregarCampo("Email",new Campo(eTipoCampo.CAMPO_EMAIL));
        formulario.ingresarRespuesta("Email","mgerez@frba.utn.edu.ar");

    }

    @Test
    @DisplayName("Se puede generar un colaborador fisico a partir de un formulario")
    public void generarColaboradorFisicoDesdeFormulario(){
        FormularioColaboradorFisico formulario = new FormularioColaboradorFisico("Pepe","Menz","pepe@gmail.com","melli11ok","+5491165974084");
        ColaboradorFisico pepe = FormularioUtils.crearColaboradorFisico(formulario);

        assertEquals("Pepe",pepe.getNombre());
        assertEquals("Menz",pepe.getApellido());
        assertEquals(3,pepe.getMediosDeContacto().size());
        //busca en la lista de set de medios de contacto
        assertTrue(pepe.getMediosDeContacto().stream().anyMatch(medio -> medio.tipoMedioDeContacto().equals("Telegram")));


    }

    @Test
    @DisplayName("Se puede generar un colaborador juridico a partir de un formulario")
    public void generarColaboradorJuridicoDesdeFormulario(){
        FormularioColaboradorJuridico formulario = new FormularioColaboradorJuridico("Metrovias S.A", TipoRazonSocial.EMPRESA.getDescripcion(), Rubro.SERVICIOS.getDescripcion());
        formulario.agregarCampo("Email",new Campo(eTipoCampo.CAMPO_EMAIL));
        formulario.ingresarRespuesta("Email","metrovias@gmail.com");
        ColaboradorJuridico metrovias = FormularioUtils.crearColaboradorJuridico(formulario);


        assertEquals("Metrovias S.A",metrovias.getRazonSocial());
        assertEquals(TipoRazonSocial.EMPRESA,metrovias.getTipoRazonSocial());
        assertEquals(Rubro.SERVICIOS,metrovias.getTipoDeRubro());


        assertEquals(1,metrovias.getMediosDeContacto().size());
        //busca en la lista de set de medios de contacto
        assertTrue(metrovias.getMediosDeContacto().stream().anyMatch(medio -> medio.informacionDeMedioDeContacto().equals("metrovias@gmail.com")));

    }


}
