package main_tests.cron_asignador_de_tecnicos;


public class MainAsignadorDeTecnicos {
    private static String filePath = "src/main/resources/demoAsignador.txt";
    public static void main(String[] args){
//        if(args.length != 1){
//            System.out.println("No se ha pasado el argumento correcto");
//            System.exit(1);
//        }
//
//        String filePath = args[0];


        AsignadorDeTecnicosSetUp asignadorSetUp = new AsignadorDeTecnicosSetUp();
        asignadorSetUp.ejecucion(filePath);
    }
}
