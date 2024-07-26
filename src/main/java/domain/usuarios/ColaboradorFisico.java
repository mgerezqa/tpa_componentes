package domain.usuarios;

import domain.contacto.MedioDeContacto;
import domain.donaciones.Vianda;
import domain.geografia.area.AreaDeCobertura;
import domain.heladera.Heladera.Heladera;
import domain.suscripciones.iSuscriptor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;

@Getter @Setter
public class ColaboradorFisico extends Colaborador {

    private String nombre;
    private String apellido;

    private List<Vianda> viandasDonadas;

    private boolean notificacionRecibida;
    @Getter
    private AreaDeCobertura zona;
    // ============================================================ //
    // Constructor //
    // ============================================================ //
    public ColaboradorFisico(String nombre, String apellido){
        this.notificacionRecibida = false;
        this.nombre = nombre;
        this.apellido = apellido;
        this.activo = true;
        this.mediosDeContacto = new HashSet<>();
    }

    // ============================================================ //
    // Metodos //
    // ============================================================ //





}
