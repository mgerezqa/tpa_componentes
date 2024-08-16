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
        return switch (tipoColab) {
            case "DINERO" -> TipoContribucion.DINERO;
            case "DONACION_VIANDAS" -> TipoContribucion.VIANDA;
            case "REDISTRIBUCION_VIANDAS" -> TipoContribucion.DISTRIBUCION;
            case "ENTREGA_TARJETAS" -> TipoContribucion.REGISTRO_P_V;
            default -> null;
        };
    }
    private TipoDocumento obtenerTipoDocumento(String tipoDoc){
        return switch (tipoDoc) {
            case "LC" -> TipoDocumento.LIBRETA_CIVICA;
            case "LE" -> TipoDocumento.LIBRETA_ENROLAMIENTO;
            case "DNI" -> TipoDocumento.DNI;
            default -> null;
        };
    }
}
