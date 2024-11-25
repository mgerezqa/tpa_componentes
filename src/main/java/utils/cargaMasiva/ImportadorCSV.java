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

    private final RepositorioColaboradores repositorioColaboradores;

    public EmailSender emailSender = EmailSender.getInstance();

    public ImportadorCSV(RepositorioColaboradores repositorioColaboradores) {
        this.repositorioColaboradores = repositorioColaboradores;
    }

    public void importarDonaciones(String archivoCSV, List<ColaboradorFisico> colaboradores) {
        List<Donacion> donaciones = new ArrayList<>();
        EntityManager em = ServiceLocator.instanceOf(EntityManager.class);

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                RegistroCSV registro = new RegistroCSV(campos);
                ColaboradorFisico colaborador = buscarOAgregarColaborador(registro, colaboradores, em, repositorioColaboradores);
                Donacion donacion = GeneradorDonacion.generar(registro, colaborador);

                em.getTransaction().begin();
                em.persist(donacion); // Solo persiste la donación
                em.getTransaction().commit();

                donaciones.add(donacion);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Deshace cambios en caso de error
            }
            e.printStackTrace();
        }
    }

    private ColaboradorFisico buscarOAgregarColaborador(RegistroCSV registro, List<ColaboradorFisico> colaboradores, EntityManager em, RepositorioColaboradores repositorio) {

        // Busca en la lista temporal
        Optional<ColaboradorFisico> colaboradorEnLista = colaboradores.stream()
                .filter(c -> c.getDocumento().getNumeroDeDocumento().equals(registro.getNroDoc().toString()))
                .findFirst();

        if (colaboradorEnLista.isPresent()) {
            return colaboradorEnLista.get();
        }

        // Busca en la base de datos
        Optional<Colaborador> colaboradorExistente = repositorio.buscarPorDocumento(registro.getNroDoc().toString());

        if (colaboradorExistente.isPresent()) {
            return (ColaboradorFisico) colaboradorExistente.get();
        }

        // Crear un nuevo colaborador si no existe
        ColaboradorFisico nuevoColaborador = new ColaboradorFisico();

        Documento documento = new Documento(registro.getTipoDoc(), String.valueOf(registro.getNroDoc()));
        nuevoColaborador.setDocumento(documento);
        nuevoColaborador.setNombre(registro.getNombre());
        nuevoColaborador.setApellido(registro.getApellido());
        nuevoColaborador.setActivo(true);

        Email email = new Email(registro.getMail());
        nuevoColaborador.agregarMedioDeContacto(email);

        // Crear y asignar un Usuario asociado
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(nuevoColaborador.getDocumento().getNumeroDeDocumento());
        nuevoUsuario.setContrasenia(nuevoColaborador.getDocumento().getNumeroDeDocumento());
        nuevoColaborador.setUsuario(nuevoUsuario);

        // Persistir en la base de datos
        em.persist(nuevoColaborador);

        // Agregar el nuevo colaborador a la lista temporal
        colaboradores.add(nuevoColaborador);

        // Notificar por email al nuevo colaborador
        try {
            emailSender.sendEmail(
                    "Bienvenido al sistema HOPEONG",
                    "¡Hola " + nuevoColaborador.getNombre() + "! Te has registrado exitosamente en nuestro sistema. " +
                            "Tu usuario y clave de acceso es tu número de documento :). Por favor, modifícalo para tu seguridad.",
                    registro.getMail()
            );
        } catch (MessagingException e) {
            System.err.println("Error al enviar email al nuevo colaborador: " + e.getMessage());
        }

        return nuevoColaborador;
    }
}