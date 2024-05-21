package domain.formulario;

public class EntradaCuit extends Entrada{
    private Integer prefijo;
    private Integer nroSociedad;
    private Integer verificador;
    private String entrada;

    public EntradaCuit(){

    }

    public void cargarEntrada(String entrada) {
        this.entrada = entrada;
        this.parsearEntrada(entrada);
        this.validarRespuesta();
    }

    private void parsearEntrada(String entrada) {
        String[] partes = entrada.split("-");
        if(partes.length != 3) {
            throw new IllegalArgumentException("El CUIT debe tener 3 partes separadas por guiones");
        }
        try {
            prefijo = Integer.parseInt(partes[0]);
            nroSociedad = Integer.parseInt(partes[1]);
            verificador = Integer.parseInt(partes[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El CUIT debe tener 3 partes numéricas separadas por guiones");
        }
    }

    public void validarRespuesta() {

        //Validaciones para prefijo
        if(prefijo < 20 || prefijo > 40) {
            throw new IllegalArgumentException("El prefijo debe ser mayor a 20 y menor a 40");
        }
        //Validaciones para nroSociedad
        if(nroSociedad < 10000000 || nroSociedad > 99999999) {
            throw new IllegalArgumentException("El nroSociedad debe ser mayor a 10000000 y menor a 99999999");
        }
        //Validaciones para verificador
        if(verificador < 0 || verificador > 9) {
            throw new IllegalArgumentException("El verificador debe ser mayor a 0 y menor a 9");
        }

    }

    @Override
    public String mostrarEntrada() {
        return prefijo + "-" + nroSociedad + "-" + verificador;
    }
}
