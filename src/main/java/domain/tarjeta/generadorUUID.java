package domain.tarjeta;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

public class GeneradorUUID implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        // Generar un UUID alfanumérico de 11 caracteres
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString().replace("-", "");
        return uuidString.substring(0, 11);  // Retornar solo los primeros 11 caracteres
    }
}

