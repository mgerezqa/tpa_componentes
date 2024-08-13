package utils.importadorCsv;

import domain.contribucionNuevo.TipoContribucion;
import domain.usuariosNuevo.TipoDocumento;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class RegistroCSV {
    private TipoDocumento tipoDoc;
    private Integer nroDoc;
    private String nombre;
    private String apellido;
    private String mail;
    private LocalDate fechaColab;
    private TipoContribucion tipoColab;
    private Integer cantidad;

    public RegistroCSV(String[] filaCVS){
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
                return TipoContribucion.REGISTRO_P_V;
            default: return null;
        }
    }
    private TipoDocumento obtenerTipoDocumento(String tipoDoc){
        switch (tipoDoc){
            case "LC":
                return TipoDocumento.LIBRETA_CIVICA;
            case "LE":
                return TipoDocumento.LIBRETA_ENROLAMIENTO;
            case "DNI":
                return TipoDocumento.DNI;
            default: return null;
        }
    }
}
