package form_version_seg;

import static form_version_seg.TipoEntrada.*;

public class TestForm {

        public static void main(String[] args) {

            Campo campoCantHijos = new Campo("Cantidad de hijos",ENTRADA_NUMERICA);
            Campo campoDireccion = new Campo("Dirección",ENTRADA_TEXTO);
            Campo campoEstadoCivil = new Campo("Estado civil",ENTRADA_TIPO_SELECCION);
            Campo campoFechaNacimiento = new Campo("Fecha de nacimiento",ENTRADA_TIPO_FECHA);
            Campo campoGenero = new Campo("Género",ENTRADA_TIPO_SELECCION);


            Colaborador lalo = new Colaborador();
            Formulario formularioDeLalo = lalo.getFormulario();

            formularioDeLalo.mostrarCampos(); //campos por defecto para un colaborador, nombre, apellido, medio de contacto

            lalo.completarFormulario();
            lalo.verificarInformacion();

            formularioDeLalo.agregar(campoGenero); //agrego un campo dinámico al formulario de lalo
            lalo.completarFormulario(); //se sobreescriben datos anteriores
            lalo.verificarInformacion();



        }
}
