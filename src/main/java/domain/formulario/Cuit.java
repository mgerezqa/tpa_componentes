package domain.formulario;

public class Cuit {
    private Integer prefijo;
    private Integer nroSociedad;
    private Integer verificador;

    public Cuit(Integer prefijo, Integer nroSociedad, Integer verificador) {

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
        this.prefijo = prefijo;
        this.nroSociedad = nroSociedad;
        this.verificador = verificador;
    }

    public String obtenerDescripcion() {
        return prefijo + "-" + nroSociedad + "-" + verificador;
    }
}
