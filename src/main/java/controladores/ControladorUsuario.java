package controladores;

import domain.usuarios.*;
import dtos.TecnicoDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioTecnicos;
import repositorios.repositoriosBDD.RepositorioUsuarios;
import repositorios.repositoriosBDD.RepositorioRoles;
import utils.ICrudViewsHandler;
import javax.persistence.NoResultException;
import java.util.*;
import java.util.stream.Collectors;
import mappers.TecnicoMapper;
import io.javalin.validation.Validator;
import io.javalin.validation.Validation;
import io.javalin.validation.ValidationError;

public class ControladorUsuario implements ICrudViewsHandler, WithSimplePersistenceUnit {
    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioRoles repositorioRoles;
    private RepositorioColaboradores repositorioColaboradores;
    private RepositorioTecnicos repositorioTecnicos;

    public ControladorUsuario(RepositorioUsuarios repositorioUsuarios, RepositorioRoles repositorioRoles,RepositorioColaboradores repositorioColaboradores,RepositorioTecnicos repositorioTecnicos) {
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioRoles = repositorioRoles;
        this.repositorioColaboradores = repositorioColaboradores;
        this.repositorioTecnicos = repositorioTecnicos;
    }

    public void login(Context ctx) {
        // Agregar validaciones
        Validator<String> name = ctx.formParamAsClass("exampleInputEmail1", String.class)
            .check(v -> v != null && !v.isEmpty(), "El nombre de usuario es obligatorio");
        Validator<String> password = ctx.formParamAsClass("exampleInputPassword1", String.class)
            .check(v -> v != null && !v.isEmpty(), "La contraseña es obligatoria");
        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(name, password);

        if (!errors.isEmpty()) {
            System.out.println(errors);
            ctx.redirect("/");
            return;
        }

        String nameValue = ctx.formParam("exampleInputEmail1");
        String passwordValue = ctx.formParam("exampleInputPassword1");

        Optional<Usuario> usuario = repositorioUsuarios.buscarPorNombre(nameValue);
        if (usuario.isEmpty()) {
            System.out.println("Usuario no encontrado");
            ctx.redirect("/");
            return;
        }

        Usuario usuarioActual = usuario.get();
        if (!Objects.requireNonNull(passwordValue).equals(usuarioActual.getContrasenia())) {
            System.out.println("Contraseña incorrecta");
            ctx.redirect("/");
            return;
        }

        // Login exitoso
        List<String> rolesString = usuarioActual.getRoles().stream()
            .map(rol -> rol.getNombre().toString())
            .collect(Collectors.toList());
        
        ctx.sessionAttribute("id_usuario", usuarioActual.getId());
        ctx.sessionAttribute("roles", rolesString);
        String redirectUrl = rolesString.contains(RoleENUM.ADMIN.toString()) ? "/dashboard" : "/home";
        ctx.redirect(redirectUrl);
    }
    @Override
    public void show(Context context) {
        context.render("/dashboard/forms/ajustes.hbs");
    }

    @Override
    public void index(Context context) {

    }

    //Ejemplo de creación de ADMIN
    @Override
    public void create(Context ctx) {
        String nombre = ctx.queryParam("nombre");
        String password = ctx.queryParam("password");

        if (nombre == null || password == null) {
            ctx.status(400);
            ctx.result("Nombre y password son requeridos");
            return;
        }

        withTransaction(() -> {
            Rol rolAdmin;
            try {
                rolAdmin = repositorioRoles.buscarRolPorNombre(RoleENUM.ADMIN);
            } catch (NoResultException e) {
                rolAdmin = new Rol(RoleENUM.ADMIN);
                repositorioRoles.guardar(rolAdmin);
            }

            // Crear usuario
            Usuario usuario = new Usuario(nombre, password);
            usuario.agregarRol(rolAdmin);

            repositorioUsuarios.guardar(usuario);

            ctx.sessionAttribute("nombre", nombre);
            ctx.sessionAttribute("id_usuario", usuario.getId());
            List<String> rolesString = usuario.getRoles().stream()
                    .map(rol -> rol.getNombre().toString())
                    .collect(Collectors.toList());
            ctx.sessionAttribute("roles", rolesString);

        });

        ctx.redirect("/dashboard");
    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }

    @Override
    public void remove(Context context) {

    }
    public void home(Context ctx) {
        List<String> roles = ctx.sessionAttribute("roles");
        Map<String, Object> model = Map.of(
            "tecnico", tieneRol(roles, RoleENUM.TECNICO),
            "fisico", tieneRol(roles, RoleENUM.FISICO),
            "juridico", tieneRol(roles, RoleENUM.JURIDICO)
        );
        
        ctx.render("home/home.hbs", model);
    }

    private boolean tieneRol(List<String> roles, RoleENUM rol) {
        return roles.contains(rol.toString());
    }

    public void perfil(Context ctx) {
        Long usuarioId = ctx.sessionAttribute("id_usuario");
        List<String> roles = ctx.sessionAttribute("roles");

        Map<String, Object> model = new HashMap<>();

        Usuario usuario = (Usuario) repositorioUsuarios.buscarPorID(Usuario.class, usuarioId).orElse(null);
        model.put("usuario", usuario);

        String rolPrincipal = obtenerRolPrincipal(roles);

        //A este punto se deberia obtener un fisico, tecnico o juridico, debido a que en el authMiddleware se realizo la verificación del usuarioId.
        Object datos = obtenerUsuarioSegun(usuarioId, rolPrincipal);
        
        if (datos != null) {
            model.put("datos", datos);
        }
        
        ctx.render(String.format("home/perfiles/%s.hbs", rolPrincipal.toLowerCase()), model);
    }

    private String obtenerRolPrincipal(List<String> roles) {
        if (roles.contains(RoleENUM.TECNICO.toString())) return "tecnico";
        if (roles.contains(RoleENUM.FISICO.toString())) return "fisico";
        if (roles.contains(RoleENUM.JURIDICO.toString())) return "juridico";
        throw new RuntimeException("Rol no válido");
    }

    private Object obtenerUsuarioSegun(Long usuarioId, String rol) {
        return switch (rol) {
            case "tecnico" -> repositorioTecnicos.buscarTecPorIdUsuario(usuarioId)
                .map(TecnicoMapper::toDTO)
                .orElse(null);
            case "fisico" -> (ColaboradorFisico) repositorioColaboradores.buscarColaboradorPorIdUsuario(usuarioId).orElse(null);
            case "juridico" -> (ColaboradorJuridico) repositorioColaboradores.buscarColaboradorPorIdUsuario(usuarioId).orElse(null);
            default -> null;
        };
    }
}
