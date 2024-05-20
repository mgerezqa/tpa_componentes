package domain.contacto;

import lombok.Getter;
import lombok.Setter;

@Getter

public class Telefono extends MedioDeContacto{
    private Integer codPais;
    private Integer codArea;
    private double numeroAbonado;

    public Telefono(Integer codPais, Integer codArea, Integer numeroAbonado) {

        // Validar que los valores proporcionados no sean nulos
        if (codPais == null || codArea == null || numeroAbonado == null) {
            throw new IllegalArgumentException("Los campos codPais, codArea y numeroAbonado no pueden ser nulos");
        }

        // Validar que los códigos de país, área y número de abonado sean positivos
        if (codPais <= 0 || codArea <= 0 || numeroAbonado <= 0) {
            throw new IllegalArgumentException("Los campos codPais, codArea y numeroAbonado deben ser valores positivos");
        }

        // Validar longitud del número de abonado ( Entre 6 y 10 dígitos)
        String numeroAbonadoStr = String.valueOf(numeroAbonado);
        int longitudNumero = numeroAbonadoStr.length();

        if (longitudNumero < 6 || longitudNumero > 10) {
            throw new IllegalArgumentException("La longitud del número de abonado debe estar entre 6 y 10 dígitos");
        }

        this.codPais = codPais;
        this.codArea = codArea;
        this.numeroAbonado = numeroAbonado;
    }

    @Override
    public String obtenerDescripcion() {
        return codPais+ " " + codArea + " " + numeroAbonado;
    }
}
