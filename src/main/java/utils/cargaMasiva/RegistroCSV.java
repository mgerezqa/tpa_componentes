package utils.cargaMasiva;

import domain.formulario.documentos.TipoDocumento;
import lombok.Getter;
import utils.cargaMasiva.TipoDonacion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
public class RegistroCSV {
    private TipoDocumento tipoDoc;
    private Integer nroDoc;
    private String nombre;
    private String apellido;
    private String mail;
    private LocalDate fechaColab;
    private TipoDonacion tipoColab;
    private Integer cantidad;

    public RegistroCSV(String[] filaCSV) {
        if (filaCSV.length < 8) {
            throw new IllegalArgumentException("El registro CSV no contiene suficientes columnas.");
        }

        this.tipoDoc = obtenerTipoDocumento(filaCSV[0]);
        this.nroDoc = parsearEntero(filaCSV[1], "Número de documento");
        this.nombre = filaCSV[2];
        this.apellido = filaCSV[3];
        this.mail = filaCSV[4];
        this.fechaColab = parsearFecha(filaCSV[5], "Fecha de colaboración");
        this.tipoColab = obtenerTipoDonacion(filaCSV[6]);
        this.cantidad = parsearEntero(filaCSV[7], "Cantidad");
    }

    private TipoDonacion obtenerTipoDonacion(String tipoColab) {
        return switch (tipoColab.toUpperCase()) {
            case "DINERO" -> TipoDonacion.DINERO;
            case "DONACION_VIANDAS" -> TipoDonacion.VIANDA;
            case "REDISTRIBUCION_VIANDAS" -> TipoDonacion.DISTRIBUCION;
            case "ENTREGA_TARJETAS" -> TipoDonacion.REGISTRO_PV;
            default -> throw new IllegalArgumentException("Tipo de donación inválido: " + tipoColab);
        };
    }

    private TipoDocumento obtenerTipoDocumento(String tipoDoc) {
        return switch (tipoDoc.toUpperCase()) {
            case "LC" -> TipoDocumento.LC;
            case "LE" -> TipoDocumento.LE;
            case "DNI" -> TipoDocumento.DNI;
            case "PASAPORTE" -> TipoDocumento.PASAPORTE;
            case "CEDULA DE IDENTIDAD" -> TipoDocumento.CEDULA_DE_IDENTIDAD;
            default -> throw new IllegalArgumentException("Tipo de documento inválido: " + tipoDoc);
        };
    }

    private Integer parsearEntero(String valor, String campo) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo '" + campo + "' no es un número válido: " + valor, e);
        }
    }

    private LocalDate parsearFecha(String fecha, String campo) {
        try {
            return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("El campo '" + campo + "' no tiene un formato de fecha válido: " + fecha, e);
        }
    }
}
