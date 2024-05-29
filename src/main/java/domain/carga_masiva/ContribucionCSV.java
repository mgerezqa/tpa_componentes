package domain.carga_masiva;

import domain.donaciones.TipoContribucion;
import domain.usuarios.TipoDocumento;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class ContribucionCSV {
    private TipoDocumento tipoDoc;
    private Integer nroDoc;
    private String nombre;
    private String apellido;
    private String mail;
    private LocalDate fechaColab;
    private TipoContribucion tipoColab;
    private Integer cantidad;

    public ContribucionCSV(String[] filaCVS){
        this.tipoDoc = obtenerTipoDocumento(filaCVS[0]);
        this.nroDoc = Integer.parseInt(filaCVS[1]);
        this.nombre = filaCVS[2];
        this.apellido = filaCVS[3];
        this.mail = filaCVS[4];
        this.fechaColab = LocalDate.parse(filaCVS[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.tipoColab = obtenerTipoDonacion(filaCVS[6]);
        this.cantidad = Integer.parseInt(filaCVS[7]);
    }
    private TipoContribucion obtenerTipoDonacion(String tipoColab){
        switch (tipoColab){
            case "DINERO":
                return TipoContribucion.DINERO;
            case "DONACION_VIANDAS":
                return TipoContribucion.VIANDA;
            case "REDISTRIBUCION_VIANDAS":
                return TipoContribucion.DISTRIBUCION;
            case "ENTREGA_TARJETAS":
                return TipoContribucion.TARJETA;
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
