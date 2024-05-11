package domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

public class ColaboradorJuridico {
    @NonNull @Getter @Setter private String razonSocial;
    @NonNull @Getter @Setter private TipoRazonSocial tipoRazonSocial;
    @NonNull @Getter @Setter private List<String> MedioContacto; //value object
    @Getter @Setter private Formulario formulario;
    private List<Objects> donacionesRealizadas;

}
