package domain.usuarios;

import domain.contacto.MedioDeContacto;
import domain.formulario.Campo;
import domain.formulario.eTipoCampo;
import domain.geografia.area.AreaDeCobertura;
import domain.suscripciones.iSuscriptor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

@Getter @Setter
public class ColaboradorFisico extends Colaborador implements iSuscriptor {

    private String nombre;
    private String apellido;
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

    @Override
    public void update() {
        setNotificacionRecibida(true);
        System.out.println("Usted ha sido notificado "+ this.getNombre() + " " + this.getApellido());
    }




}
