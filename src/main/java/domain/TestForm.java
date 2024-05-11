package domain;

import java.time.LocalDate;

import static domain.FrecuenciaDeDonacion.FRECUENCIA_MENSUAL;
import static domain.Motivo.DESPERFECTO_HELADERA;
import static domain.TipoEntrada.*;

public class TestForm {

        public static void main(String[] args) {

            Campo campoCantHijos = new Campo("Cantidad de hijos",ENTRADA_NUMERICA);
            Campo campoDireccion = new Campo("Dirección",ENTRADA_TEXTO);
            Campo campoEstadoCivil = new Campo("Estado civil",ENTRADA_TIPO_SELECCION);
            Campo campoFechaNacimiento = new Campo("Fecha de nacimiento",ENTRADA_TIPO_FECHA);
            Campo campoGenero = new Campo("Género",ENTRADA_TIPO_SELECCION);

            LocalDate fechaVencimiento = LocalDate.of(2024, 5, 31); // Ejemplo de fecha de vencimiento
            LocalDate fechaDeDonacion = LocalDate.now(); // Ejemplo de fecha de donación (fecha actual)


            Heladera heladera = new Heladera();
            ColaboradorFisico lalo = new ColaboradorFisico("Lalo", "Menz", "12345678");
            Vianda sangucheMila = new Vianda(heladera,fechaVencimiento,fechaDeDonacion, lalo);
            Dinero dinero2K = new Dinero(2000,FRECUENCIA_MENSUAL,fechaDeDonacion);
            Distribuir distribucionDeViandas = new Distribuir(heladera,heladera,10,DESPERFECTO_HELADERA,fechaDeDonacion);

            Formulario unFormulario = new Formulario();


            unFormulario.agregar(campoGenero); //agrego un campo dinámico al formulario de lalo
            lalo.completarFormulario(unFormulario); //se sobreescriben datos anteriores
            lalo.verificarInformacion();

            lalo.donar(sangucheMila);
            lalo.donar(dinero2K);
            lalo.donar(distribucionDeViandas);

            System.out.println(lalo);

//            administrador.aprobar(colaboradorFisico); // Se aprueba el colaborador


        }
}
