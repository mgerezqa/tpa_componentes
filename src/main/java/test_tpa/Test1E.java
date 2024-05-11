package test_tpa;

import form_version_seg.Colaborador;
import form_version_seg.Formulario;

public class Test1E {
//
//    Requerimientos de dominio

    public static void main(String[] args) {


        // 2. Se debe permitir realizar colaboraciones
        ColaboradorFisico colaboradorFisico = new ColaboradorFisico("Juan", "Perez", "123",False,[]);
        ColaboradorJuridico  colaboradorJuridico = new ColaboradorJuridico ("AFA", "ONG","15-4444-4444",False,[]);

        Vianda vianda = new Vianda ("sandwich",HELADERA_A, "05/08/2024", "05/12/2024", False);
        Donacion dinero2K = new DonacionDinero("05/08/2024",2000,FRECUENCIA_MENSUAL);
        Distribucion distribuirViandas = newDistribuir(HELADERA_A,HELADERA_B,200,TIPO_RESPUESTA_A_B, "05/08/2024");

        colaboradorFisico.donar(vianda);
        donar(){
            donaciones.add(vianda);
        }

        //1. Se debe permitir el alta, baja y modificación de colaboradores

        colaboradorFisico.puedeSerDadoDeAlta(); // logica de si la lista de colab > 0
        colaboradorFisico.serDadoDeAlta();
        colaboradorFisico.serDadoDeBaja();
        colaboradorFisico.setearNombre("OtroNombre");
        colaboradorFisico.setearApellido("OtroApellido");
        colaboradorFisico.setearMedioContacto("OtroNombre"); //value object,profundizar

        // ===================================
        public void serDadoDeAlta(){
            this.estado = True;
        }

        public void serDadoDeBaja(){
            this.estado = False;
        }
        // ===================================
        public void modificarDatos(ColaboradorFisico colaboradorF){
            this = colaboradorF;
        }

//3. Se debe permitir ingresar viandas a la heladera (como una forma de contribución)

        vianda.getHeladera();
        vianda.setHeladera(OTRA_HELADERA);

// 4. Se debe permitir el alta de personas en situacion vulnerable

        PersonaVulnerable personaVulnerable = new PersonaVulnerable("Juan", "Perez", "05/05/2024",False, TIPO_DOC,"40222444",[MenoresACargo]);//Object value Documento
        personaVulnerable.serDadoDeAlta();

// 5. Se debe permitir el alta, baja y modificación de heladeras

        Heladera heladera = new Heladera(Ubicacion,String nombre,Integer unidad,LocalDate fechaInicioFuncionamiento, Bool estado);// estado empieza en True, Object value: Float latitud, Float longitud, String direccion

        heladera.darDeBaja();
        heladera.modificar(unidad = 10);
// Caso 1: modificar la cantidad de unidades y los otros atributos mantenerlos iguales
// Caso 2: crear una nueva instancia, con los datos modificados
// (ambos casos valen para la clase heladera como para los colaboradores)


//=======================================================================================================

        //Opcion 1
        colaboradorFisico.donar(dinero2K);
        colaboradorFisico.donar(vianda);
        colaboradorFisico.donar(ropa);


        donacionVianda = colaboradorFisico.getDonacion(donacionDeVianda);
        donacionVianda.donar(vianda);



        //Opcion 2
        ColaboradorFisico adrian = new ColaboradorFisico("Juan", "Perez", "123",False, new Donacion [vianda,dinero2K]);


        adrian.agregarDonacion(vianda); //parmetro tipo vianda
        adrian.agregarDonacion(dinero2K); //parametro tipo dinero
        adrian.donar();
        adrian = colaboradorFisico.getDonacion(donacionDeDinero);
        /*
        //atributo de colaboradorFisico:
        //listaDonaciones(ArrayList<TipoDonacion>) = {donacionDinero, donacionVianda}
        void donar(Donacion donacion){
            getTipoDonacion(donacion).donar(donacion);
        }
        TipoDonacion getTipoDonacion(Donacion donacion){
            return filtrarListaDonacionesYSeleccionarElTipoDonacionApropiada(donacion)
        }*/
        // 1. Se debe permitir el alta, baja y modificaci贸n de colaboradores


        Formulario formulario = new Formulario();
        formulario.registrar(colaboradorFisico);
        formulario.registrar(colaboradorJuridico);

        administrador.aprobar(colaboradorFisico); // Se aprueba el colaborador






        // 4. Se debe permitir el alta de personas en situaci贸n vulnerable
        // 5. Se debe permitir el alta, baja y modificaci贸n de heladeras
    }
}