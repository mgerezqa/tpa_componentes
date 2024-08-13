package utils.generadores;

import domain.formularioNuevo.FormularioNuevo;
import domain.usuariosNuevo.ColaboradorFisico;
import domain.usuariosNuevo.ColaboradorJuridico;
import domain.usuariosNuevo.DatosColabFisico;
import domain.usuariosNuevo.DatosColabJuridico;

public interface GeneradorColaborador {
    static ColaboradorFisico colabFisico(FormularioNuevo unForm){
        ColaboradorFisico aux = colabFisico(DatosColabFisico.generar(unForm));
        aux.setFormulario(unForm);
        return aux;
    }

    static ColaboradorFisico colabFisico(DatosColabFisico datos){
        ColaboradorFisico aux = new ColaboradorFisico();
        aux.setNombre(datos.getNombre());
        aux.setApellido(datos.getApellido());
        aux.setTipoDocumento(datos.getTipoDocumento());
        aux.setNumeroDocumento(datos.getNumeroDocumento());
        aux.setMediosDeContacto(datos.getContactos());
        aux.setContribucionesQueRealizara(datos.getContribuciones());
        aux.setFechaNac(datos.getFechaNac());
        aux.setDireccion(datos.getDireccion());
        return aux;
    }

    static ColaboradorJuridico colabJuridico(FormularioNuevo unForm){
        ColaboradorJuridico aux = colabJuridico(DatosColabJuridico.generar(unForm));
        aux.setFormulario(unForm);
        return aux;
    }

    static ColaboradorJuridico colabJuridico(DatosColabJuridico datos){
        ColaboradorJuridico aux = new ColaboradorJuridico();
        aux.setRazonSocial(datos.getRazonSocial());
        aux.setTipoRazonSocial(datos.getTipoRazonSocial());
        aux.setTipoDeRubro(datos.getTipoDeRubro());
        aux.setMediosDeContacto(datos.getContactos());
        aux.setContribucionesQueRealizara(datos.getContribuciones());
        aux.setDireccion(datos.getDireccion());
        return aux;
    }

}
