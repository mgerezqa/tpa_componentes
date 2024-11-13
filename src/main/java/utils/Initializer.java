package utils;

import domain.contacto.Email;
import domain.contacto.Telegram;
import domain.contacto.Whatsapp;
import domain.formulario.documentos.Cuil;
import domain.formulario.documentos.Documento;
import domain.formulario.documentos.TipoDocumento;
import domain.geografia.*;
import domain.geografia.area.AreaDeCobertura;
import domain.geografia.area.TamanioArea;
import domain.usuarios.*;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioRoles;
import repositorios.repositoriosBDD.RepositorioTecnicos;
import repositorios.repositoriosBDD.RepositorioUsuarios;

public class Initializer implements WithSimplePersistenceUnit {
    private RepositorioRoles repositorioRoles;
    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioColaboradores repositorioColaboradores;
    private RepositorioTecnicos repositorioTecnicos;

    public Initializer(RepositorioRoles repositorioRoles, RepositorioUsuarios repositorioUsuarios, RepositorioColaboradores repositorioColaboradores, RepositorioTecnicos repositorioTecnicos) {
        this.repositorioRoles = repositorioRoles;
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioColaboradores = repositorioColaboradores;
        this.repositorioTecnicos = repositorioTecnicos;
    }

    public void init() {
        withTransaction(()->{
            this.instaciarLosRoles();
            this.instaciarLosDistintosUsuariosRoles();
        });
    }
    private void instaciarLosRoles(){
        Rol admin = new Rol(RoleENUM.ADMIN);
        Rol juridico = new Rol(RoleENUM.JURIDICO);
        Rol fisico = new Rol(RoleENUM.FISICO);
        Rol tecnico = new Rol(RoleENUM.TECNICO);

        repositorioRoles.guardar(admin);
        repositorioRoles.guardar(juridico);
        repositorioRoles.guardar(fisico);
        repositorioRoles.guardar(tecnico);
    }
    private void instaciarLosDistintosUsuariosRoles(){
        Usuario admin = new Usuario("admin","admin");
        Usuario usuarioJuridico = new Usuario("juridico","juridico");
        Usuario usuarioFisico = new Usuario("fisico","fisico");
        Usuario usuarioTecnico = new Usuario("tecnico","tecnico");
        Rol adminRol = repositorioRoles.buscarRolPorNombre(RoleENUM.ADMIN);
        Rol juridicoRol = repositorioRoles.buscarRolPorNombre(RoleENUM.JURIDICO);
        Rol fisicoRol = repositorioRoles.buscarRolPorNombre(RoleENUM.FISICO);
        Rol tecnicoRol = repositorioRoles.buscarRolPorNombre(RoleENUM.TECNICO);
        admin.agregarRol(adminRol);
        usuarioJuridico.agregarRol(juridicoRol);
        usuarioFisico.agregarRol(fisicoRol);
        usuarioTecnico.agregarRol(tecnicoRol);

        //Tecnico
        Documento documentoTecnico = new Documento(TipoDocumento.DNI, "12325678");
        Cuil cuilTecnico = new Cuil("20", "12145678", "4");
        Tecnico tecnico = new Tecnico("nombreTecnico","apellidoTecnico",documentoTecnico,cuilTecnico);
        Provincia provinciaTecnico = new Provincia("Buenos Aires");
        Ubicacion ubicacionTecnico = new Ubicacion(-34.6044723f, -58.3816322f, new Calle("rivadavia", "14345")); // Coordenadas de Buenos Aires
        ubicacionTecnico.setProvincia(provinciaTecnico);
        AreaDeCobertura areaTecnico = new AreaDeCobertura(ubicacionTecnico, TamanioArea.GRANDE);
        Whatsapp whatsappTecnico = new Whatsapp("+5491123256789");
        Email emailTecnico = new Email("tecnico1@example.com");
        Telegram telegramTecnico = new Telegram("tecnico1_telegram");
        telegramTecnico.setNumero("+5491123256789");
        telegramTecnico.setNotificar(true);
        tecnico.agregarMedioDeContacto(whatsappTecnico);
        tecnico.agregarMedioDeContacto(emailTecnico);
        tecnico.agregarMedioDeContacto(telegramTecnico);
        tecnico.setAreaDeCobertura(areaTecnico);
        tecnico.setUsuario(usuarioTecnico);

        //Fisico
        Localidad localidadFisico = new Localidad("Almafuerte");
        Barrio barrioFisico = new Barrio("Barrio Las Heras");
        Provincia provinciaFisico = new Provincia("Cordoba");
        Ubicacion ubicacionFisico = new Ubicacion(-67.6044723f, -43.3816322f, new Calle("av. carlos paz", "4567"));
        ubicacionFisico.setProvincia(provinciaFisico);
        ubicacionFisico.setLocalidad(localidadFisico);
        ubicacionFisico.setBarrio(barrioFisico);
        AreaDeCobertura areaFisico = new AreaDeCobertura(ubicacionFisico, TamanioArea.GRANDE);
        areaFisico.setProvincia(provinciaFisico);

        Whatsapp whatsappFisico = new Whatsapp("+5491159384624");
        Email emailFisico = new Email("colaborador1@example.com");
        Telegram telegramFisico = new Telegram("colab1-telegram");

        ColaboradorFisico colaboradorFisico = new ColaboradorFisico("Valentin", "Griggio");
        colaboradorFisico.agregarMedioDeContacto(whatsappFisico);
        colaboradorFisico.agregarMedioDeContacto(emailFisico);
        colaboradorFisico.agregarMedioDeContacto(telegramFisico);
        colaboradorFisico.setZona(areaFisico);
        colaboradorFisico.setUsuario(usuarioFisico);

        //Juridico
        Whatsapp whatsappJuridico = new Whatsapp("+5491165419940");
        Email emailJuridico = new Email("empresaX@example.com");
        Telegram telegramJuridico = new Telegram("empresa-telegram");

        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico("Electrodomesticos SA", TipoRazonSocial.EMPRESA, Rubro.COMERCIO);
        colaboradorJuridico.agregarMedioDeContacto(whatsappJuridico);
        colaboradorJuridico.agregarMedioDeContacto(emailJuridico);
        colaboradorJuridico.agregarMedioDeContacto(telegramJuridico);
        colaboradorJuridico.setUsuario(usuarioJuridico);

        repositorioColaboradores.guardar(colaboradorJuridico);
        repositorioColaboradores.guardar(colaboradorFisico);
        repositorioTecnicos.guardar(tecnico);
        repositorioUsuarios.guardar(admin);
    }
}