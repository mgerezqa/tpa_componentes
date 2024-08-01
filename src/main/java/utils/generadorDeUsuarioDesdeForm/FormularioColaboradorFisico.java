package utils.generadorDeUsuarioDesdeForm;

import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.formulario.eTipoCampo;

public class FormularioColaboradorFisico extends Formulario {

    public FormularioColaboradorFisico(String nombre, String apellido, String email, String telegram, String whatsapp) {
        super();
        agregarCampo("Nombre", new Campo(eTipoCampo.CAMPO_NOMBRE));
        agregarCampo("Apellido", new Campo(eTipoCampo.CAMPO_NOMBRE));
        agregarCampo("Email", new Campo(eTipoCampo.CAMPO_EMAIL));
        agregarCampo("Telegram", new Campo(eTipoCampo.CAMPO_TEXTO));
        agregarCampo("Whatsapp", new Campo(eTipoCampo.CAMPO_TELEFONO));

        ingresarRespuesta("Nombre", nombre);
        ingresarRespuesta("Apellido", apellido);
        ingresarRespuesta("Email", email);
        ingresarRespuesta("Telegram", telegram);
        ingresarRespuesta("Whatsapp", whatsapp);

    }

    public FormularioColaboradorFisico(String nombre, String apellido) {
        super();
        agregarCampo("Nombre", new Campo(eTipoCampo.CAMPO_NOMBRE));
        agregarCampo("Apellido", new Campo(eTipoCampo.CAMPO_NOMBRE));

        ingresarRespuesta("Nombre", nombre);
        ingresarRespuesta("Apellido", apellido);

    }


    public FormularioColaboradorFisico() {
        super();
        agregarCampo("Nombre", new Campo(eTipoCampo.CAMPO_NOMBRE));
        agregarCampo("Apellido", new Campo(eTipoCampo.CAMPO_NOMBRE));
        agregarCampo("Email", new Campo(eTipoCampo.CAMPO_EMAIL));
        agregarCampo("Telegram", new Campo(eTipoCampo.CAMPO_TEXTO));
        agregarCampo("Whatsapp", new Campo(eTipoCampo.CAMPO_TELEFONO));
    }





}
