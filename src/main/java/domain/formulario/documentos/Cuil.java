package domain.formulario.documentos;

import domain.excepciones.CuilInvalidoException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Cuil {
    @Column(name = "prefijo")
    private String prefijo;

    @Column(name = "dni")
    private String dni;

    @Column(name = "verificador")
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
    public Cuil(String cuilMezclado) {
        String[] partes = cuilMezclado.split("-");
        validarFormato(partes);
        validarPrefijo(partes[0]);
        validarDni(partes[1]);
        validarVerificador(partes[2]);
        
        this.prefijo = partes[0];
        this.dni = partes[1];
        this.verificador = partes[2];
    }

    private void validarFormato(String[] partes) {
        if (partes.length != 3) {
            throw new CuilInvalidoException("El formato del CUIL debe ser XX-XXXXXXXX-X");
        }
    }


    private void validarPrefijo(String prefijo) {
        int prefijoInt = Integer.parseInt(prefijo);
        if (prefijoInt < 20 || prefijoInt > 27) {
            throw new CuilInvalidoException("El prefijo debe ser mayor o igual a 20 y menor o igual a 27");
        }
    }

    private void validarDni(String dni) {
        int dniInt = Integer.parseInt(dni);
        if (dniInt < 1000000 || dniInt > 99999999) {
            throw new CuilInvalidoException("El DNI debe ser mayor o igual a 1000000 y menor o igual a 99999999");
        }
    }

    private void validarVerificador(String verificador) {
        int verificadorInt = Integer.parseInt(verificador);
        if (verificadorInt < 0 || verificadorInt > 9) {
            throw new CuilInvalidoException("El verificador debe ser mayor o igual a 0 y menor o igual a 9");
        }
    }

    public String obtenerDescripcion() {
        return prefijo + "-" + dni + "-" + verificador;
    }

}
