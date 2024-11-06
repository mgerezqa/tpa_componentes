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

                if (usuarioId == null) {
                    throw new AccessDeniedException();
                }

                List<Rol> rolesUsuario = this.repositorioRoles.buscarRolesPorIdUsuario(usuarioId);
                
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