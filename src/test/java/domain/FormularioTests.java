package domain;

import domain.contacto.MedioDeContacto;
import domain.formulario.Formulario;
import domain.formularioNuevo.FormularioNuevo;
import domain.formularioNuevo.TipoEntrada;
import domain.formularioNuevo.TipoFormulario;
import domain.usuariosNuevo.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.generadores.GeneradorColaborador;
import utils.generadores.GeneradorFormulario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormularioTests {
    private ColaboradorFisico lalo;
    private MedioDeContacto laloEmail;
    private MedioDeContacto laloTelefono;
    private MedioDeContacto laloWhatsapp;
    private Formulario formulario;
    private LocalDate fechaActual;

    // nuevos

    private FormularioNuevo formularioNuevo;
    private FormularioNuevo formularioFisico;
    private FormularioNuevo formularioJuridico;

    @BeforeEach
    public void setUp() {
        //Formulario
        this.formulario = new Formulario();
        this.fechaActual = LocalDate.now();

        this.formularioNuevo = new FormularioNuevo(TipoFormulario.OTRO);
        this.formularioFisico = GeneradorFormulario.colabFisico();
        this.formularioJuridico = GeneradorFormulario.colabJuridico();
    }

    @Test
    @DisplayName("Un formulario admite la creación de campos dinámicos")
    public void camposDinamicos(){

        formularioNuevo.agregarRegistro(TipoEntrada.ENTRADA_TEXTO, "CAMPO_1", "Titulo");
        formularioNuevo.agregarRegistro(TipoEntrada.ENTRADA_NUMERICA, "CAMPO_2", "Numero de paginas");
        formularioNuevo.agregarRegistro(TipoEntrada.ENTRADA_FECHA, "CAMPO_3", "Fecha de publicacion");

        formularioNuevo.ingresarRespuesta("CAMPO_1", "Este es un titulo");
        formularioNuevo.ingresarRespuesta("CAMPO_2", "4");
        formularioNuevo.ingresarRespuesta("CAMPO_3", "03/09/2012");


        Assertions.assertEquals(3, formularioNuevo.cantidadRegistros());

        formularioNuevo.getRegistros().forEach(System.out::println);
    }


    @Test
    @DisplayName("El formulario acepta la carga de preguntas de multiples respuestas")
    public void unFormularioTieneUnaEntradaDeMultiplesRespuestas(){
        formularioNuevo.agregarRegistro(TipoEntrada.ENTRADA_MULTIPLE, "CAMPO_1", "Listado");

        formularioNuevo.ingresarRespuesta("CAMPO_1", "elemento 1");
        formularioNuevo.ingresarRespuesta("CAMPO_1", "elemento 2");
        formularioNuevo.ingresarRespuesta("CAMPO_1", "elemento 3");

        Assertions.assertEquals(3, formularioNuevo.obtenerCampo("CAMPO_1").cantidadRespuestas());

        System.out.println(formularioNuevo.obtenerCampo("CAMPO_1").obtenerRespuestas());
    }

    @Test
    @DisplayName("El formulario acepta la carga de preguntas con fecha")
    public void unFormularioAdmitePreguntasDeTipoFecha(){
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        formularioNuevo.agregarRegistro(TipoEntrada.ENTRADA_FECHA, "Campo_1", "Una fecha");
        formularioNuevo.agregarRegistro(TipoEntrada.ENTRADA_FECHA, "Campo_2", "Fecha de hoy");

        formularioNuevo.ingresarRespuesta("Campo_1", "03/09/2012");
        formularioNuevo.ingresarRespuesta("Campo_2", hoy.format(formatoFecha));

        Assertions.assertEquals("03/09/2012", formularioNuevo.obtenerRespuesta("Campo_1"));

        formularioNuevo.getRegistros().forEach(System.out::println);
    }

    /* dado que el formuario guarda respuestas como strings, no tiene sentido estos test

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

     */

    @Test
    @DisplayName("Se puede generar un colaborador fisico a partir de un formulario. Este ejemplo no verifica los datos ingresados")
    public void generarColaboradorFisicoDesdeFormulario(){

        formularioFisico.ingresarRespuesta("CAMPO_NOMBRE", "Lio");
        formularioFisico.ingresarRespuesta("CAMPO_APELLIDO", "Messirve");
        formularioFisico.ingresarRespuesta("CAMPO_EMAIL", "el10@messirve.com");
        formularioFisico.ingresarRespuesta("CAMPO_TIPO_DOCUMENTO", "DNI");
        formularioFisico.ingresarRespuesta("CAMPO_NRO_DOCUMENTO", "123456");
        formularioFisico.ingresarRespuesta("CAMPO_FORMA_CONTRIBUCION", "VIANDA");
        formularioFisico.ingresarRespuesta("CAMPO_FORMA_CONTRIBUCION", "MANTENIMIENTO");
        formularioFisico.ingresarRespuesta("CAMPO_FECHA_NACIMIENTO", "04/12/2023");
        formularioFisico.ingresarRespuesta("CAMPO_DIRECCION", "calle falsa 123");

        ColaboradorFisico unColabFisico = GeneradorColaborador.colabFisico(formularioFisico);
        System.out.println(unColabFisico);

    }

    @Test
    @DisplayName("Se puede generar un colaborador juridico a partir de un formulario")
    public void generarColaboradorJuridicoDesdeFormulario(){
        /*
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

         */

        formularioJuridico.ingresarRespuesta("CAMPO_RAZON_SOCIAL", "Metrovias S.A");
        formularioJuridico.ingresarRespuesta("CAMPO_TIPO_JURIDICO", "ONG");
        formularioJuridico.ingresarRespuesta("CAMPO_RUBRO", "FINANZAS");
        formularioJuridico.ingresarRespuesta("CAMPO_EMAIL", "metrovias@gmail.com");
        formularioJuridico.ingresarRespuesta("CAMPO_FORMA_CONTRIBUCION", "VIANDA");
        formularioJuridico.ingresarRespuesta("CAMPO_FORMA_CONTRIBUCION", "MANTENIMIENTO");
        formularioJuridico.ingresarRespuesta("CAMPO_DIRECCION", "calle falsa 123");

        ColaboradorJuridico unColabJuridico = GeneradorColaborador.colabJuridico(formularioJuridico);

        System.out.println(unColabJuridico);
    }


}
