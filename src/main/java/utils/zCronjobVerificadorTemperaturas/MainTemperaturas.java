package utils.zCronjobVerificadorTemperaturas;

public class MainTemperaturas {
    public static void main(String[] args){
        if(args.length != 1){
            System.out.println("No se ha pasado el argumento correcto");
            System.exit(1);
        }

        String filePath = args[0];
        TemperaturasSetUp temperaturasSetUp = new TemperaturasSetUp();
        temperaturasSetUp.ejecucion(filePath);
    }
}
