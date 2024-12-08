package domain.usuarios;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "colaboradores_juridicos")
public class ColaboradorJuridico extends Colaborador {

    @Column(name = "razonSocial")
    private String razonSocial;

    @Enumerated(EnumType.STRING)
    private TipoRazonSocial tipoRazonSocial;

    @Enumerated(EnumType.STRING)
    private Rubro tipoDeRubro;

    public ColaboradorJuridico(String razonSocial, TipoRazonSocial tipoRazonSocial, Rubro rubro) {
        this.razonSocial = razonSocial;
        this.tipoRazonSocial = tipoRazonSocial;
        this.tipoDeRubro = rubro;
        this.mediosDeContacto = new HashSet<>();
        this.activo = true;

    }

    public ColaboradorJuridico(String razonSocial, String tipoRazonSocial, String rubro) {
        this.razonSocial = razonSocial;
        this.tipoRazonSocial = TipoRazonSocial.valueOf(tipoRazonSocial.toUpperCase());
        this.tipoDeRubro = Rubro.valueOf(rubro.toUpperCase());
        this.mediosDeContacto = new HashSet<>();
        this.activo = true;

    }

}


