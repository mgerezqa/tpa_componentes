package controladores;

import domain.usuarios.Rol;
import domain.usuarios.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import repositorios.repositoriosBDD.RepositorioUsuarios;
import repositorios.repositoriosBDD.RepositorioRoles;
import utils.ICrudViewsHandler;
import javax.persistence.NoResultException;

public class ControladorUsuario implements ICrudViewsHandler, WithSimplePersistenceUnit {
    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioRoles repositorioRoles;

    public ControladorUsuario(RepositorioUsuarios repositorioUsuarios, RepositorioRoles repositorioRoles) {
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioRoles = repositorioRoles;
    }

    @Override
    public void show(Context context) {
        context.render("/dashboard/forms/ajustes.hbs");
    }


    @Override
    public void index(Context context) {

    }


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
                rolAdmin = repositorioRoles.buscarRolPorNombre("ADMIN");
            } catch (NoResultException e) {
                rolAdmin = new Rol();
                rolAdmin.setNombre("ADMIN");
                repositorioRoles.guardar(rolAdmin);
            }

            // Crear usuario
            Usuario usuario = new Usuario(nombre, password);
            usuario.agregarRol(rolAdmin);

            repositorioUsuarios.guardar(usuario);
            ctx.sessionAttribute("nombre", nombre);
            ctx.sessionAttribute("id_usuario", usuario.getId());
        });

        ctx.result("Usuario ADMIN creado exitosamente");
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
