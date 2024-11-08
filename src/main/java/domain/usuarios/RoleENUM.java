package domain.usuarios;
import io.javalin.security.RouteRole;

public enum RoleENUM implements RouteRole {
    ADMIN,
    USER,
    COLABORADOR
}