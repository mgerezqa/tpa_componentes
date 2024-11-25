package utils.cargaMasiva;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import config.ServiceLocator;
import domain.contacto.Email;
import domain.donaciones.*;
import domain.formulario.documentos.Documento;
import domain.mensajeria.EmailSender;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.Usuario;
import jakarta.mail.MessagingException;
import repositorios.repositoriosBDD.*;

import javax.persistence.Embedded;
import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImportadorCSV {

    private static final String asunto = " . . . ";
    private static final String cuerpo = " . . . ";

    private final RepositorioColaboradores repositorioColaboradores;
    private final RepositorioDonacionesDinero repositorioDonacionesDinero;
    private final RepositorioViandas repositorioViandas;
    private final RepositorioDistribuciones repositorioDistribuciones;
    private final RepositorioMantenciones repositorioMantenciones;
    private final RepositorioRegistrosVulnerables repositorioRegistrosVulnerables;

    public EmailSender emailSender = EmailSender.getInstance();

    public ImportadorCSV(
            RepositorioColaboradores repositorioColaboradores,
            RepositorioDonacionesDinero repositorioDonacionesDinero,
            RepositorioViandas repositorioViandas,
            RepositorioDistribuciones repositorioDistribuciones,
            RepositorioMantenciones repositorioMantenciones,
            RepositorioRegistrosVulnerables repositorioRegistrosVulnerables
    ) {
        this.repositorioColaboradores = repositorioColaboradores;
        this.repositorioDonacionesDinero = repositorioDonacionesDinero;
        this.repositorioViandas = repositorioViandas;
        this.repositorioDistribuciones = repositorioDistribuciones;
        this.repositorioMantenciones = repositorioMantenciones;
        this.repositorioRegistrosVulnerables = repositorioRegistrosVulnerables;
    }

    public List<Donacion> importarDonaciones(String archivoCSV, List<ColaboradorFisico> colaboradores) {
        List<Donacion> donaciones = new ArrayList<>();
        EntityManager em = ServiceLocator.instanceOf(EntityManager.class);

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                RegistroCSV registro = new RegistroCSV(campos);
                ColaboradorFisico colaborador = buscarOAgregarColaborador(registro, colaboradores, em, repositorioColaboradores);
                Donacion donacion = GeneradorDonacion.generar(registro, colaborador);

                em.getTransaction().begin();  // Inicia transacción
                em.persist(donacion);     // Persiste la donación
                em.persist(colaborador);  // Persiste el colaborador (si es nuevo)
                em.getTransaction().commit(); // Confirma transacción

                donaciones.add(donacion);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Deshace cambios en caso de error
            }
            e.printStackTrace();
        }

        return donaciones;
    }

    private ColaboradorFisico buscarOAgregarColaborador(RegistroCSV registro, List<ColaboradorFisico> colaboradores, EntityManager em, RepositorioColaboradores repositorio) {
        // Busca el colaborador utilizando el método del repositorio
        Optional<Colaborador> colaboradorExistente = repositorio.buscarPorDocumento(registro.getNroDoc().toString());

        // Si el colaborador existe, lo devuelve; si no, lo crea
        if (colaboradorExistente.isPresent()) {
            return (ColaboradorFisico) colaboradorExistente.get(); // Asegúrate de que el tipo sea ColaboradorFisico
        } else {
            // Crear un nuevo colaborador si no existe
            ColaboradorFisico nuevoColaborador = new ColaboradorFisico();

            Documento documento = new Documento(registro.getTipoDoc(), String.valueOf(registro.getNroDoc()));
            nuevoColaborador.setDocumento(documento);
            nuevoColaborador.setNombre(registro.getNombre());
            nuevoColaborador.setApellido(registro.getApellido());

            Email email = new Email(registro.getMail());
            nuevoColaborador.agregarMedioDeContacto(email);

            // Crear y asignar un Usuario asociado
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombreUsuario(nuevoColaborador.getDocumento().getNumeroDeDocumento());
            nuevoUsuario.setContrasenia(nuevoColaborador.getDocumento().getNumeroDeDocumento());
            nuevoColaborador.setUsuario(nuevoUsuario);

            // Persistir en la base de datos
            em.persist(nuevoColaborador);

            // Notificar por email al nuevo colaborador
            try {
                emailSender.sendEmail(
                        "Bienvenido al sistema HOPEONG",
                        "¡Hola " + nuevoColaborador.getNombre() + "! Te has registrado exitosamente en nuestro sistema." + "Tu usuario y clave de acceso" +
                                " es tu numero de documento :), por favor modificalo para tu seguridad.",
                        registro.getMail()
                );
            } catch (Exception e) {
                System.err.println("Error al enviar email al nuevo colaborador: " + e.getMessage());
                e.printStackTrace();
            }

            return nuevoColaborador;
        }
    }


}