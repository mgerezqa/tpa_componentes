package domain.usuarios;

import domain.contacto.MedioDeContacto;
import domain.formulario.Campo;
import domain.formulario.eTipoCampo;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

@Setter @Getter
public class ColaboradorJuridico extends Colaborador{
    private String razonSocial;
    private TipoRazonSocial tipoRazonSocial;
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


