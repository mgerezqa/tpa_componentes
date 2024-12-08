package repositorios.repositoriosBDD;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.ColaboradorJuridico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Data;
import repositorios.Repositorio;

import java.util.List;
import java.util.Optional;

@Data
public class RepositorioColaboradores extends Repositorio implements WithSimplePersistenceUnit {

    public void guardar(Colaborador colaborador) {
        entityManager().persist(colaborador);
    }

    public void eliminarPorId(String id) {
        Colaborador colaborador = entityManager().find(Colaborador.class, id);
        if (colaborador != null) {
            entityManager().remove(colaborador);
        }
    }

    public void eliminar(Colaborador colaborador) {
        entityManager().remove(colaborador);
    }

    public Colaborador obtenerPorId(Long id) {
        return entityManager().find(Colaborador.class, id);
    }

    public ColaboradorFisico obtenerFisicoId(Long id){
        return entityManager().find(ColaboradorFisico.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Colaborador> colaboradoresActivos() {
        return entityManager()
                .createQuery("FROM Colaborador c WHERE c.activo = true", Colaborador.class)
                .getResultList();
    }

    public List<ColaboradorFisico> obtenerColaboradoresFisicos() {
        return entityManager()
                .createQuery("FROM ColaboradorFisico", ColaboradorFisico.class)
                .getResultList();
    }

    public List<ColaboradorJuridico> obtenerColaboradoresJuridicos() {
        return entityManager()
                .createQuery("FROM ColaboradorJuridico", ColaboradorJuridico.class)
                .getResultList();
    }

    public List<ColaboradorFisico> obtenerColaboradoresFisicosActivos() {
        return entityManager()
                .createQuery("FROM ColaboradorFisico cf WHERE cf.activo = true", ColaboradorFisico.class)
                .getResultList();
    }

    public List<ColaboradorJuridico> obtenerColaboradoresJuridicosActivos() {
        return entityManager()
                .createQuery("FROM ColaboradorJuridico cj WHERE cj.activo = true", ColaboradorJuridico.class)
                .getResultList();
    }

    public Optional<Colaborador> buscarColaboradorPorIdUsuario(Long idUsuario) {
        return entityManager()
                .createQuery("SELECT c FROM Colaborador c WHERE c.usuario.id = :idUsuario", Colaborador.class)
                .setParameter("idUsuario", idUsuario)
                .getResultStream()
                .findFirst();
    }

    public Optional<Colaborador> buscarPorDocumento(String numeroDocumento) {
        return entityManager()
                .createQuery("SELECT c FROM Colaborador c WHERE c.documento.numeroDeDocumento = :numeroDocumento", Colaborador.class)
                .setParameter("numeroDocumento", numeroDocumento)
                .getResultStream()
                .findFirst();
    }

    public String obtenerNombreORazonSocialPorId(Long id) {
        ColaboradorFisico colaboradorFisico = entityManager().find(ColaboradorFisico.class, id);
        if (colaboradorFisico != null) {
            return colaboradorFisico.getNombre() + " " + colaboradorFisico.getApellido();
        }

        ColaboradorJuridico colaboradorJuridico = entityManager().find(ColaboradorJuridico.class, id);
        if (colaboradorJuridico != null) {
            return colaboradorJuridico.getRazonSocial();
        }

        throw new IllegalArgumentException("No se encontró un colaborador con el ID proporcionado: " + id);
    }


}