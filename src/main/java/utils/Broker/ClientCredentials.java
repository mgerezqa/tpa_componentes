package utils.Broker;

import lombok.Data;

@Data
public class ClientCredentials {
    String clientID;
    String username;
    String password;

    public ClientCredentials(String clientID, String username, String password) {
        this.clientID = clientID;
        this.username = username;
        this.password = password;
    }
}
