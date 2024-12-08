package repositorios.repositoriosBDD;

import domain.contacto.MedioDeContacto;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class RepositorioMediosDeContacto implements WithSimplePersistenceUnit {

    public void guardar(MedioDeContacto medioDeContacto) {
        if (entityManager().contains(medioDeContacto)) {
            entityManager().merge(medioDeContacto);
        } else {
            entityManager().persist(medioDeContacto);
        }
    }

    public void eliminarPorId(Long id) {
        MedioDeContacto medio = entityManager().find(MedioDeContacto.class, id);
        if (medio != null) {
            entityManager().remove(medio);
        }
    }

    public void eliminarTodos() {
        List<MedioDeContacto> mediosDeContacto = entityManager()
                .createQuery("SELECT m FROM MedioDeContacto m", MedioDeContacto.class)
                .getResultList();

        for (MedioDeContacto medioDeContacto : mediosDeContacto) {
            entityManager().remove(medioDeContacto);
        }
    }
}
