package domain.formularioNuevo.campo;

import domain.formularioNuevo.TipoEntrada;

public class FactoryCampoFormulario {
    public static iCampo crear(TipoEntrada unTipoEntrada){
        switch (unTipoEntrada){
            case ENTRADA_MULTIPLE:
                return new CampoMultiple();
            default:
                return new CampoSimple();
        }
    }
}
