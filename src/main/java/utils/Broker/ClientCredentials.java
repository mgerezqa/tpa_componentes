package utils.Broker;

import lombok.Data;

@Data
public class ClientCredentials {
    private String clientID;
    private String username;
    private String password;

    public ClientCredentials(String clientID, String username, String password) {
        this.clientID = clientID;
        this.username = username;
        this.password = password;
    }
}
