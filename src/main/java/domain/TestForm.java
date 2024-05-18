package domain;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telefono;
import domain.contacto.Whatsapp;
import domain.donaciones.Dinero;
import domain.donaciones.Distribuir;
import domain.donaciones.Vianda;
import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.formulario.TipoCampo;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.ColaboradorJuridico;
import domain.usuarios.Rubro;
import domain.usuarios.TipoRazonSocial;
import java.time.LocalDate;

import static domain.donaciones.FrecuenciaDeDonacion.FRECUENCIA_ANUAL;
import static domain.donaciones.FrecuenciaDeDonacion.FRECUENCIA_MENSUAL;
import static domain.donaciones.Motivo.*;


public class TestForm {

        public static void main(String[] args) {

            Ubicacion ubicacion = new Ubicacion(-54F,-48F,new Calle("Av. Rivadavia", "1234"));

            LocalDate fechaVencimiento = LocalDate.of(2024, 5, 31); // Ejemplo de fecha de vencimiento
            LocalDate fechaDeDonacion = LocalDate.now(); // Ejemplo de fecha de donación (fecha actual)
            LocalDate fechaInicioFuncionamiento = LocalDate.of(2021, 5, 31); // Ejemplo de fecha de inicio de funcionamiento

            Heladera heladera = new Heladera(ubicacion,"Heladera Palermo",200,fechaInicioFuncionamiento,20F,-10F);

            MedioDeContacto laloEmail = new Email("lalo@gmail.com");
            MedioDeContacto laloTelefono = new Telefono(54,11,40009000);
            MedioDeContacto laloWhastapp = new Whatsapp("+549116574460");

            ColaboradorFisico lalo = new ColaboradorFisico("Lalo", "Menz");
            ColaboradorJuridico metrovias = new ColaboradorJuridico("Metrovias S.A",TipoRazonSocial.EMPRESA, Rubro.ENERGIA);

            Vianda sangucheMila = new Vianda(fechaVencimiento,fechaDeDonacion,lalo); // Se crea una vianda (donación de un colaborador físico
            Dinero dinero2K = new Dinero(2000,FRECUENCIA_MENSUAL,fechaDeDonacion,lalo);
            Dinero dinero4K = new Dinero(4000,FRECUENCIA_ANUAL,fechaDeDonacion,metrovias);
            Distribuir distribucionDeViandas = new Distribuir(heladera,heladera,10,DESPERFECTO_HELADERA,fechaDeDonacion,lalo);

            // Crear campos y respuestas


            Formulario formulario = new Formulario();
            formulario.agregarCampo("nombre", new Campo(TipoCampo.CAMPO_NOMBRE));
            formulario.agregarCampo("apellido", new Campo(TipoCampo.CAMPO_NOMBRE));
            formulario.agregarCampo("idiomas", new Campo(TipoCampo.CAMPO_MULTIPLE));
            formulario.agregarCampo("cantidad de hijos", new Campo(TipoCampo.CAMPO_NUMERICO));
            formulario.agregarCampo("email",new Campo(TipoCampo.CAMPO_EMAIL));

            lalo.completarFormulario(formulario);//necesario para empezar a completar el formulario

            lalo.agregarRespuesta("nombre","lalo");
            lalo.agregarRespuesta("apellido","jar");
            lalo.agregarRespuesta("idiomas","español");
            lalo.agregarRespuesta("idiomas","ingles");
            lalo.agregarRespuesta("cantidad de hijos", "5");
            lalo.agregarRespuesta("email","lalo@gmail.com");

            lalo.leerFormulario();




//            administrador.aprobar(colaboradorFisico); // Se aprueba el colaborador


        }
}
