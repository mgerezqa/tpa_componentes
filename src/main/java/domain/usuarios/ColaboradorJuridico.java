package domain.usuarios;

import domain.formulario.Formulario;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ColaboradorJuridico extends Colaborador{
    @NonNull @Getter @Setter private String razonSocial;
    @NonNull @Getter @Setter private TipoRazonSocial tipoRazonSocial;
    @NonNull @Getter @Setter private Rubro tipoDeRubro;
    @Getter @Setter private Formulario formulario;


    public ColaboradorJuridico(String razonSocial, TipoRazonSocial tipoRazonSocial, Rubro rubro) {
        this.razonSocial = razonSocial;
        this.tipoRazonSocial = tipoRazonSocial;
        this.tipoDeRubro = rubro;

    }
}
