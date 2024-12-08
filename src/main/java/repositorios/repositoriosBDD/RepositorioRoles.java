package repositorios.repositoriosBDD;

import domain.usuarios.Rol;
import domain.usuarios.RoleENUM;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.util.List;

public class RepositorioRoles extends Repositorio implements WithSimplePersistenceUnit {
    @SuppressWarnings("unchecked")
    public List<Rol> buscarRolesPorIdUsuario(Long idUsuario) {
        String sql = "SELECT r.* " +
                "FROM rol r " +
                "INNER JOIN usuario_rol ur ON r.id = ur.rol_id " +
                "WHERE ur.usuario_id = ?";

        return entityManager()
                .createNativeQuery(sql, Rol.class)
                .setParameter(1, idUsuario)
                .getResultList();
    }

    public Rol buscarRolPorNombre(RoleENUM nombreRol) {
        return entityManager()
                .createQuery("FROM Rol r WHERE r.nombre = :nombre", Rol.class)
                .setParameter("nombre", nombreRol)
                .getSingleResult();
    }
}
