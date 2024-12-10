package utils.cargaMasiva;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import config.ServiceLocator;
import domain.contacto.Email;
import domain.donaciones.*;
import domain.formulario.documentos.Documento;
import domain.mensajeria.EmailSender;
import domain.puntos.CalculadoraPuntos;
import domain.tarjeta.TarjetaColaborador;
import domain.usuarios.*;
import jakarta.mail.MessagingException;
import repositorios.repositoriosBDD.RepositorioColaboradores;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ImportadorCSV {

    private final RepositorioColaboradores repositorioColaboradores;
    public EmailSender emailSender = EmailSender.getInstance();
    static CalculadoraPuntos calculadoraPuntos = new CalculadoraPuntos();

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
                final ColaboradorFisico colaborador = buscarOAgregarColaborador(registro, colaboradores, em, repositorioColaboradores);
                Donacion donacion = GeneradorDonacion.generar(registro, colaborador);

                int puntos = 0;
                if (donacion instanceof Dinero) {
                    puntos = calculadoraPuntos.puntosPesosDonados((Dinero) donacion);
                } else if (donacion instanceof Vianda) {
                    puntos = calculadoraPuntos.puntosViandasDonadas(registro.getCantidad());
                } else if (donacion instanceof Distribuir) {
                    puntos = calculadoraPuntos.puntosViandasDistribuidas(((Distribuir) donacion).getCantidad());
                } else if (donacion instanceof RegistroDePersonaVulnerable) {
                    puntos = calculadoraPuntos.puntosTarjetasRepatidas(registro.getCantidad());
                }

                // Registrar puntos al colaborador
                colaborador.sumarPuntos(puntos);

                withTransaction(em, () -> {
                    if (em.contains(colaborador)) {
                        em.merge(colaborador);  // Reenlazar la entidad existente
                    } else {
                        em.persist(colaborador); // Persistir nueva entidad
                    }
                    em.persist(donacion); // Persistir la donación
                });

                donaciones.add(donacion);
            }
        } catch (Exception e) {
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
        TarjetaColaborador tarjetaColaborador = TarjetaColaborador.of(nuevoColaborador);

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

        Rol rol = new Rol(RoleENUM.FISICO);
        nuevoUsuario.agregarRol(rol);

        nuevoColaborador.setUsuario(nuevoUsuario);

        // Persistir en la base de datos
        withTransaction(em, () -> {
            em.persist(nuevoColaborador);
            em.persist(tarjetaColaborador);
        });

        // Agregar el nuevo colaborador a la lista temporal
        colaboradores.add(nuevoColaborador);

        // Configurar Handlebars
        ClassPathTemplateLoader loader = new ClassPathTemplateLoader("/templates", ".hbs");
        Handlebars handlebars = new Handlebars(loader);
        Template template = null;
        try {
            template = handlebars.compile("email");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crear el modelo de datos
        Map<String, Object> model = new HashMap<>();
        model.put("name", nuevoColaborador.getNombre());
        model.put("username", nuevoColaborador.getDocumento().getNumeroDeDocumento());
        model.put("password", nuevoColaborador.getDocumento().getNumeroDeDocumento());

        // Renderizar el contenido HTML del email
        String htmlContent = null;
        try {
            htmlContent = template.apply(model);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Notificar por email al nuevo colaborador
        try {
            EmailSender emailSender = EmailSender.getInstance();
            emailSender.sendHtmlEmail(
                    "Bienvenido al sistema HOPEONG",
                    htmlContent,
                    registro.getMail()
            );
        } catch (MessagingException e) {
            System.err.println("Error al enviar email al nuevo colaborador: " + e.getMessage());
        }

        return nuevoColaborador;
    }

    private void withTransaction(EntityManager em, Runnable action) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.run();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }
}
