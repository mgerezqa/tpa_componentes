package controladores;
import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.Usuario;
import dtos.requests.ColaboradorFisicoInputDTO;
import dtos.responses.ColaboradorFisicoOutputDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.HttpStatus;
import io.javalin.validation.Validation;
import io.javalin.validation.ValidationError;
import repositorios.Repositorio;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioUsuarios;
import services.interfaces.IServiceColaboradorFisico;
import utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.validation.Validator;
import io.javalin.http.Context;

import java.util.*;
import java.util.Objects;

public class ControladorColaboradorFisico implements ICrudViewsHandler, WithSimplePersistenceUnit {

    private RepositorioColaboradores repositorioColaboradores;
    private RepositorioUsuarios repositorioUsuarios;
    private IServiceColaboradorFisico serviceColaboradorFisico;

    public ControladorColaboradorFisico(RepositorioColaboradores repositorioColaboradores, IServiceColaboradorFisico serviceColaboradorFisico, RepositorioUsuarios repositorioUsuarios) {
        this.repositorioColaboradores = repositorioColaboradores;
        this.serviceColaboradorFisico = serviceColaboradorFisico;
        this.repositorioUsuarios = repositorioUsuarios;
    }

    @Override // no funciona correctamente
    public void index(Context context){
        List<ColaboradorFisicoOutputDTO> colaboradoresFisicos = serviceColaboradorFisico.obtenerTodos();
        Map<String, Object> model = new HashMap<>();
        model.put("colaboradoresFisicos", colaboradoresFisicos);
        context.render("dashboard/fisicos.hbs", model);
    }

    @Override // funciona correctamente
    public void create(Context context) {
        Validator<String> nombre = context.formParamAsClass("nombre", String.class)
                .check(Objects::nonNull, "El nombre del colaborador es obligatorio");
        Validator<String> apellido = context.formParamAsClass("apellido", String.class)
                .check(Objects::nonNull, "El apellido del colaborador es obligatorio");
        Validator<String> email = context.formParamAsClass("email", String.class)
                .check(Objects::nonNull, "El email del colaborador es obligatorio");

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(nombre,apellido,email);

        if(!errors.isEmpty()){
            System.out.println(errors);
            context.redirect("/dashboard/fisicos");
            return;
        }

        ColaboradorFisicoInputDTO colaboradorFisicoInputDTO = new ColaboradorFisicoInputDTO();
        colaboradorFisicoInputDTO.setApellido(context.formParam("apellido"));
        colaboradorFisicoInputDTO.setNombre(context.formParam("nombre"));
        colaboradorFisicoInputDTO.setActivo(Boolean.valueOf(context.formParam("activo")));
        colaboradorFisicoInputDTO.setEmail(context.formParam("email"));

        serviceColaboradorFisico.crear(colaboradorFisicoInputDTO);
        context.redirect("/dashboard/fisicos");
    }

    @Override //
    public void edit(Context context) {
        Optional<Object> posibleColaborador =
                this.repositorioColaboradores.buscarPorID(ColaboradorFisico.class, Long.valueOf(context.pathParam("id")));

        if(posibleColaborador.isEmpty()){
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        ColaboradorFisico colaboradorFisico = (ColaboradorFisico) posibleColaborador.get();

        ColaboradorFisicoInputDTO colaboradorFisicoInputDTO = new ColaboradorFisicoInputDTO();
        colaboradorFisicoInputDTO.setId(colaboradorFisico.getId());
        colaboradorFisicoInputDTO.setNombre(colaboradorFisico.getNombre());
        colaboradorFisicoInputDTO.setApellido(colaboradorFisico.getApellido());
        colaboradorFisicoInputDTO.setActivo(colaboradorFisico.getActivo());
        colaboradorFisicoInputDTO.setEmail(colaboradorFisico.email());

        Map<String, Object> model = new HashMap<>();
        model.put("colaboradoresFisicos", colaboradorFisicoInputDTO);
        model.put("action","/dashboard/fisicos/" + colaboradorFisicoInputDTO.getId()+ "/edit");

        context.render("dashboard/forms/fisico.hbs", model);
    }

    @Override // hacer
    public void update(Context context) {
        Long colaboradorId = Long.valueOf(context.pathParam("id"));
        // Validaciones
        Validator<String> nombre = context.formParamAsClass("nombre", String.class)
                .check(Objects::nonNull, "El nombre del colaborador es obligatorio");
        Validator<String> apellido = context.formParamAsClass("apellido", String.class)
                .check(Objects::nonNull, "El apellido del colaborador es obligatorio");
        Validator<String> email = context.formParamAsClass("email", String.class)
                .check(Objects::nonNull, "El email del colaborador es obligatorio");

        boolean estado = context.formParam("activo") != null;

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(nombre, apellido, email);

        if (!errors.isEmpty()) {
            context.sessionAttribute("errors", errors);

            // Renderizar el modal nuevamente con los errores en lugar de redirigir
            context.render("dashboard/forms/fisico.hbs");
            return;
        }

        // Buscar colaborador y actualizar
        Optional<Object> posibleColaborador =
                this.repositorioColaboradores.buscarPorID(ColaboradorFisico.class, colaboradorId);

        if (posibleColaborador.isEmpty()) {
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        ColaboradorFisico colaboradorFisico = (ColaboradorFisico) posibleColaborador.get();
        colaboradorFisico.setNombre(nombre.get());
        colaboradorFisico.setApellido(apellido.get());
        colaboradorFisico.setActivo(estado);
        Set<MedioDeContacto> medioDeContactos = colaboradorFisico.getMediosDeContacto();

        Optional<Email> medioDeContacto = medioDeContactos.stream()
                .filter(v -> v instanceof Email) // Verifica si es instancia de Email
                .map(v -> (Email) v) // Cast a Email
                .findFirst();

        if (medioDeContacto.isPresent()){
            medioDeContacto.get().setEmail(email.get());
        }
        // Actualizar en transacción
        withTransaction(() -> {
            repositorioColaboradores.actualizar(colaboradorFisico);
        });

        // Redireccionar después de la actualización
        context.redirect("/dashboard/fisicos");
    }

    @Override // hacer
    public void delete(Context context) {
        Optional<Object> posibleColaborador =
                this.repositorioColaboradores.buscarPorID(ColaboradorFisico.class, Long.valueOf(context.pathParam("id")));

        if (posibleColaborador.isEmpty()) {
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        ColaboradorFisico colaboradorFisico = (ColaboradorFisico) posibleColaborador.get();

        withTransaction(() -> {
            repositorioColaboradores.eliminar(colaboradorFisico);
            System.out.println("Colaborador eliminado: " + colaboradorFisico.getId());
        });

        context.redirect("/dashboard/fisicos");

    }

    @Override // . . .
    public void save(Context context){
        withTransaction(()->{
            //Instancias de entidades relacionadas al endpoint
            String nombre = context.formParam("nombre");
            String apellido = context.formParam("apellido");
            ColaboradorFisico colaborador = new ColaboradorFisico(nombre,apellido);
            String email = context.formParam("emailUsuario");
            String contrasenia = context.formParam("password");
            Usuario usuario = new Usuario(email,contrasenia);
            //Validación de repetición de usuario
            if(!repositorioUsuarios.buscarPorNombre(email).isEmpty()){
                context.redirect("Error al querer registrar usuario ya existente!");
                return;
            }
            //TODO -> Hacer validación de que no sea una contraseña tipica de los 10000
            //Guardado de datos
            repositorioColaboradores.guardar(colaborador);
            repositorioUsuarios.guardar(usuario);
            context.redirect("/"); //Sugerencia -> Redirección a una pantalla de exito del registro.
        });
    }

    @Override // . . .
    public void show(Context context) {

    }

}