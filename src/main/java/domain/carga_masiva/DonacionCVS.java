package domain.carga_masiva;

import domain.donaciones.TipoDonacion;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class DonacionCVS {
    private TipoDocumento tipoDoc;
    private Integer nroDoc;
    private String nombre;
    private String apellido;
    private String mail;
    private LocalDate fechaColab;
    private TipoDonacion tipoColab;
    private Integer cantidad;

    public DonacionCVS(String[] filaCVS){
        this.tipoDoc = obtenerTipoDocumento(filaCVS[0]);
        this.nroDoc = Integer.parseInt(filaCVS[1]);
        this.nombre = filaCVS[2];
        this.apellido = filaCVS[3];
        this.mail = filaCVS[4];
        this.fechaColab = LocalDate.parse(filaCVS[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.tipoColab = obtenerTipoDonacion(filaCVS[6]);
        this.cantidad = Integer.parseInt(filaCVS[7]);
    }
    private TipoDonacion obtenerTipoDonacion(String tipoColab){
        switch (tipoColab){
            case "DINERO":
                return TipoDonacion.DONA_DINERO;
            case "DONACION_VIANDAS":
                return TipoDonacion.DONA_VIANDA;
            case "REDISTRIBUCION_VIANDAS":
                return TipoDonacion.DONA_VIANDA;
            case "ENTREGA_TARJETAS":
                return TipoDonacion.DONA_TARJETA;
            default: return null;
        }
    }
    private TipoDocumento obtenerTipoDocumento(String tipoDoc){
        switch (tipoDoc){
            case "LC":
                return TipoDocumento.LC;
            case "LE":
                return TipoDocumento.LE;
            case "DNI":
                return TipoDocumento.DNI;
            default: return null;
        }
    }
}
