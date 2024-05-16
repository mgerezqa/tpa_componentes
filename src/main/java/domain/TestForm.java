package domain;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telefono;
import domain.contacto.Whatsapp;
import domain.donaciones.Dinero;
import domain.donaciones.Distribuir;
import domain.donaciones.Vianda;
import domain.formulario.Campo;
import domain.formulario.Respuesta;
import domain.formulario.TipoEntrada;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.ColaboradorJuridico;
import domain.usuarios.Rubro;
import domain.usuarios.TipoRazonSocial;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static domain.donaciones.FrecuenciaDeDonacion.FRECUENCIA_MENSUAL;
import static domain.donaciones.Motivo.*;
import static domain.formulario.TipoEntrada.*;


public class TestForm {

        public static void main(String[] args) {

            Campo campoCantHijos = new Campo("Cantidad de hijos",ENTRADA_NUMERICA);
            Campo campoDireccion = new Campo("Dirección",ENTRADA_TEXTO);
            Campo campoEstadoCivil = new Campo("Estado civil",ENTRADA_TIPO_SELECCION);
            Campo campoFechaNacimiento = new Campo("Fecha de nacimiento",ENTRADA_TIPO_FECHA);
            Campo campoGenero = new Campo("Género",ENTRADA_TIPO_SELECCION);

            Ubicacion ubicacion = new Ubicacion(-54F,-48F,new Calle("Av. Rivadavia", "1234"));

            LocalDate fechaVencimiento = LocalDate.of(2024, 5, 31); // Ejemplo de fecha de vencimiento
            LocalDate fechaDeDonacion = LocalDate.now(); // Ejemplo de fecha de donación (fecha actual)
            LocalDate fechaInicioFuncionamiento = LocalDate.of(2021, 5, 31); // Ejemplo de fecha de inicio de funcionamiento

            Heladera heladera = new Heladera(ubicacion,"Heladera Palermo",200,fechaInicioFuncionamiento,20F,-10F);

            MedioDeContacto laloEmail = new Email("lalo@gmail.com");
            MedioDeContacto laloTelefono = new Telefono(54,11,40009000);
            MedioDeContacto laloWhastapp = new Whatsapp("+549116574460");

            ColaboradorFisico lalo = new ColaboradorFisico("Lalo", "Menz");
            ColaboradorJuridico metrovias = new ColaboradorJuridico("Metrovias S.A",TipoRazonSocial.EMPRESA, "12345678", Rubro.ENERGIA);

            Vianda sangucheMila = new Vianda(fechaVencimiento,fechaDeDonacion,lalo); // Se crea una vianda (donación de un colaborador físico
            Dinero dinero2K = new Dinero(2000,FRECUENCIA_MENSUAL,fechaDeDonacion);
            Distribuir distribucionDeViandas = new Distribuir(heladera,heladera,10,DESPERFECTO_HELADERA,fechaDeDonacion);

            // Crear campos y respuestas
            Campo nombre = new Campo("Nombre", TipoEntrada.ENTRADA_TEXTO);
            Campo apellido = new Campo("Apellido", TipoEntrada.ENTRADA_TEXTO);
            Campo cantidadDeHijos = new Campo("Cantidad de hijos", TipoEntrada.ENTRADA_NUMERICA);
            Campo idiomas = new Campo("Idiomas", TipoEntrada.ENTRADA_RESPUESTA_MULTIPLE);
            Campo telefono = new Campo("Telefono", ENTRADA_TELEFONO);
            Campo email = new Campo("Email", ENTRADA_EMAIL);
            Campo whastapp = new Campo("Whatsapp", ENTRADA_WHATSAPP);

            List listaDeIdiomas = new ArrayList<>();
            listaDeIdiomas.add("Ingles");
            listaDeIdiomas.add("Español");


            Respuesta respuestaNombreDeLalo = Respuesta.crearRespuesta(nombre, "Lalo");
            Respuesta respuestaApellidoDeLalo = Respuesta.crearRespuesta(apellido, "González");
            Respuesta respuestaCantidadDeHijos = Respuesta.crearRespuesta(cantidadDeHijos, 2);
            Respuesta respuestaIdiomasDeLalo = Respuesta.crearRespuesta(idiomas, listaDeIdiomas);
            Respuesta respuestaTelefonoDeLalo = Respuesta.crearRespuesta(telefono,new Telefono(54,11,45447777));
            Respuesta respuestaEmailDeLalo = Respuesta.crearRespuesta(email,new Email("lalo@gmail.com"));

            lalo.completarFormulario();//necesario para empezar a completar el formulario

            //unFormulario.agregar(campoGenero); //agrego un campo dinámico al formulario de lalo
            lalo.agregarRespuesta(respuestaNombreDeLalo);
            lalo.agregarRespuesta(respuestaApellidoDeLalo);
            lalo.agregarRespuesta(respuestaIdiomasDeLalo);
            lalo.agregarRespuesta(respuestaCantidadDeHijos);
            lalo.agregarRespuesta(respuestaEmailDeLalo);
            lalo.agregarRespuesta(respuestaTelefonoDeLalo);


            lalo.agregarRespuesta(Respuesta.crearRespuesta(new Campo("Monto a donar",ENTRADA_NUMERICA),200));

            lalo.leerFormulario();

            lalo.donar(sangucheMila);
            lalo.donar(dinero2K);
            lalo.donar(distribucionDeViandas);

            System.out.println(lalo);

//            administrador.aprobar(colaboradorFisico); // Se aprueba el colaborador


        }
}
