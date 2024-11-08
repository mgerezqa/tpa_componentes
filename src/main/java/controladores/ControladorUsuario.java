package controladores;

import domain.usuarios.Rol;
import domain.usuarios.RoleENUM;
import domain.usuarios.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import repositorios.repositoriosBDD.RepositorioUsuarios;
import repositorios.repositoriosBDD.RepositorioRoles;
import utils.ICrudViewsHandler;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ControladorUsuario implements ICrudViewsHandler, WithSimplePersistenceUnit {
    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioRoles repositorioRoles;

    public ControladorUsuario(RepositorioUsuarios repositorioUsuarios, RepositorioRoles repositorioRoles) {
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioRoles = repositorioRoles;
    }

    public void login(Context ctx) {
        //Falta hacer validaciones
        String name = ctx.formParam("exampleInputEmail1");
        String password = ctx.formParam("exampleInputPassword1");

        Optional<Usuario> usuario = repositorioUsuarios.buscarPorNombre(name);
        if (usuario.isPresent()) {
            Usuario usuarioActual = usuario.get();
            if(password.equals(usuarioActual.getContrasenia())){
                List<Rol> roles = usuario.get().getRoles();
                List<String> rolesString = roles.stream()
                    .map(rol -> rol.getNombre().toString())
                    .collect(Collectors.toList());
                
                ctx.sessionAttribute("id_usuario", usuarioActual.getId());
                ctx.sessionAttribute("roles", rolesString);
                
                if(roles.stream().anyMatch(rol -> RoleENUM.ADMIN.equals(rol.getNombre()))){
                    ctx.redirect("/dashboard");
                }
                if(roles.stream().anyMatch(rol -> RoleENUM.FISICO.equals(rol.getNombre()))){
                    ctx.redirect("/home");
                }
                if(roles.stream().anyMatch(rol -> RoleENUM.JURIDICO.equals(rol.getNombre()))){
                    ctx.redirect("/home");
                }
                if(roles.stream().anyMatch(rol -> RoleENUM.TECNICO.equals(rol.getNombre()))){
                    ctx.redirect("/home");
                }

                //ACA se sigue haciendo el redirect en caso de que tenga el rol de colaborador y mandarlo a la interface de colaboradores
            }else{
                ctx.redirect("/"); //Mostrar error de contraseña invalida
            }
        }else{
            ctx.status(HttpStatus.NOT_FOUND);
        }
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
}
