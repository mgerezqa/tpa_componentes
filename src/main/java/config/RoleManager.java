package config;
import domain.usuarios.Rol;
import io.javalin.security.RouteRole;

public class RoleManager {
    public enum Roles implements RouteRole {
        ADMIN,
        USER,
    }

    public static RouteRole convertirRol(Rol rol) {
        return Roles.valueOf(rol.getNombre().toUpperCase());
    }
} 