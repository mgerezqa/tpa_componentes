package domain.tarjeta;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

public class GeneradorUUID implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString().replace("-", "");
        return uuidString.substring(0, 11);
    }
}
