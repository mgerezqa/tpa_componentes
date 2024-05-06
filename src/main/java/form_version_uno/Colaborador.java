package form_version_uno;

import java.util.HashMap;
import java.util.Map;

public class Colaborador {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Map<String, String> nuevosDatos;

    public Colaborador() {
        this.nuevosDatos = new HashMap<>();
    }

    public void actualizarInformacion(String firstName, String lastName, String email, String phone, Map<String, String> nuevosDatos) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.nuevosDatos = nuevosDatos;
    }

    public void verificarInformacion() {
        System.out.println("Información del Colaborador:");
        System.out.println("Nombre: " + firstName);
        System.out.println("Apellido: " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Teléfono: " + phone);

        System.out.println("Nuevos Datos:");
        for (Map.Entry<String, String> entry : nuevosDatos.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public Map<String, String> agregarCampoNuevo(String campo, String valor) {
        this.nuevosDatos.put(campo, valor);
        return this.nuevosDatos;
    }
}
