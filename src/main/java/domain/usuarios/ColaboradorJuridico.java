package domain.usuarios;

import domain.donaciones.Donable;
import domain.formulario.Formulario;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ColaboradorJuridico {
    @NonNull @Getter @Setter private String razonSocial;
    @NonNull @Getter @Setter private TipoRazonSocial tipoRazonSocial;
    @NonNull @Getter @Setter private List<String> MedioContacto; //value object
    @NonNull @Getter @Setter private Rubro tipoDeRubro;
    @Getter @Setter private Formulario formulario;
    @Getter private List <Donable> donacionesRealizadas = new ArrayList<>();


    public ColaboradorJuridico(String razonSocial, TipoRazonSocial tipoRazonSocial, String medioDeContacto, Rubro rubro) {
        this.razonSocial = razonSocial;
        this.tipoRazonSocial = tipoRazonSocial;
        this.MedioContacto = new ArrayList<>();
        this.MedioContacto.add(medioDeContacto);
        this.tipoDeRubro = rubro;

    }
}
