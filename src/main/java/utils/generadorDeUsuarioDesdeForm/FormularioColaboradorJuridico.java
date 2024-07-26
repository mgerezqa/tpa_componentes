package utils.generadorDeUsuarioDesdeForm;

import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.formulario.eTipoCampo;

public class FormularioColaboradorJuridico extends Formulario {


    public FormularioColaboradorJuridico(){
        super();
        agregarCampo("Razon Social", new Campo(eTipoCampo.CAMPO_TEXTO));
        agregarCampo("Tipo de Razon Social", new Campo(eTipoCampo.CAMPO_NOMBRE));
        agregarCampo("Rubro", new Campo(eTipoCampo.CAMPO_NOMBRE));
        agregarCampo("Email", new Campo(eTipoCampo.CAMPO_EMAIL));
        agregarCampo("Telegram", new Campo(eTipoCampo.CAMPO_TELEFONO));
        agregarCampo("Whatsapp", new Campo(eTipoCampo.CAMPO_TELEFONO));
    }

    public FormularioColaboradorJuridico(String razonSocial, String tipoRazonSocial, String rubro) {
        super();
        agregarCampo("Razon Social", new Campo(eTipoCampo.CAMPO_TEXTO));
        agregarCampo("Tipo de Razon Social", new Campo(eTipoCampo.CAMPO_NOMBRE));
        agregarCampo("Rubro", new Campo(eTipoCampo.CAMPO_NOMBRE));
        ingresarRespuesta("Razon Social", razonSocial);
        ingresarRespuesta("Tipo de Razon Social", tipoRazonSocial);
        ingresarRespuesta("Rubro", rubro);

    }


    public FormularioColaboradorJuridico(String razonSocial, String tipoRazonSocial, String rubro, String email, String telegram, String whatsapp) {
        super();
        agregarCampo("Razon Social", new Campo(eTipoCampo.CAMPO_TEXTO));
        agregarCampo("Tipo de Razon Social", new Campo(eTipoCampo.CAMPO_NOMBRE));
        agregarCampo("Rubro", new Campo(eTipoCampo.CAMPO_NOMBRE));
        agregarCampo("Email", new Campo(eTipoCampo.CAMPO_EMAIL));
        agregarCampo("Telegram", new Campo(eTipoCampo.CAMPO_TELEFONO));
        agregarCampo("Whatsapp", new Campo(eTipoCampo.CAMPO_TELEFONO));
        ingresarRespuesta("Razon Social", razonSocial);
        ingresarRespuesta("Tipo de Razon Social", tipoRazonSocial);
        ingresarRespuesta("Rubro", rubro);
        ingresarRespuesta("Email", email);
        ingresarRespuesta("Telegram", telegram);
        ingresarRespuesta("Whatsapp", whatsapp);
    }






}
