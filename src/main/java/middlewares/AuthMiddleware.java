package middlewares;

import domain.usuarios.Rol;
import io.javalin.Javalin;
import io.javalin.security.RouteRole;
import repositorios.repositoriosBDD.RepositorioRoles;
import server.exceptions.AccessDeniedException;
import java.util.List;
import java.util.Set;

public class AuthMiddleware {

    private RepositorioRoles repositorioRoles;

    public AuthMiddleware(RepositorioRoles repositorioRoles) {
        this.repositorioRoles = repositorioRoles;
    }

    public void apply(Javalin app) {
        app.beforeMatched(ctx -> {
            Set<RouteRole> rolesRequeridos = ctx.routeRoles();
            if (!rolesRequeridos.isEmpty()) {
                Long usuarioId = ctx.sessionAttribute("id_usuario");

                //Checkeo que si tiene sesión, por lo tanto no debo verificar esto en las rutas
                if (usuarioId == null) {
                    throw new AccessDeniedException();
                }
                //Tambien podria obtener los roles mediante la sesión al logeo, en este caso lo dejo asi porque tendria que refactorizar.
                List<Rol> rolesUsuario = this.repositorioRoles.buscarRolesPorIdUsuario(usuarioId);
                //Checkeo si tiene los roles que deben tener segun la ruta, no debo verificar en las rutas los roles.
                if (!tieneRolValido(rolesUsuario, rolesRequeridos)) {
                    throw new AccessDeniedException();
                }
            }
        });
    }

    private boolean tieneRolValido(List<Rol> rolesUsuario, Set<RouteRole> rolesRequeridos) {
        return rolesUsuario.stream()
            .anyMatch(rol -> rolesRequeridos.stream()
                .anyMatch(rolRequerido -> rolCoincide(rol, rolRequerido)));
    }

    private boolean rolCoincide(Rol rol, RouteRole rolRequerido) {
        return rol.getNombre().equals(rolRequerido);
    }
}