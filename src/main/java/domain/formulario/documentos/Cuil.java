package domain.formulario.documentos;

import domain.excepciones.CuilInvalidoException;

public class Cuil {
    private String prefijo;
    private String dni;
    private String verificador;

    public Cuil(String prefijo, String dni, String verificador) {
        // Validaciones para prefijo (según CUIL)
        int prefijoInt = Integer.parseInt(prefijo);
        if (prefijoInt < 20 || prefijoInt > 27) {
            throw new CuilInvalidoException("El prefijo debe ser mayor o igual a 20 y menor o igual a 27");
        }
        // Validaciones para DNI
        int dniInt = Integer.parseInt(dni);
        if (dniInt < 1000000 || dniInt > 99999999) {
            throw new CuilInvalidoException("El DNI debe ser mayor o igual a 1000000 y menor o igual a 99999999");
        }
        // Validaciones para verificador
        int verificadorInt = Integer.parseInt(verificador);
        if (verificadorInt < 0 || verificadorInt > 9) {
            throw new CuilInvalidoException("El verificador debe ser mayor o igual a 0 y menor o igual a 9");
        }
        this.prefijo = prefijo;
        this.dni = dni;
        this.verificador = verificador;
    }

    public String obtenerDescripcion() {
        return prefijo + "-" + dni + "-" + verificador;
    }

}
