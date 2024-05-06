package form_version_uno;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Formulario {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Map<String, String> nuevosDatos;
    private int flag;

    public Formulario() {

        this.nuevosDatos = new HashMap<>(); // Inicializa nuevosDatos como un HashMap
    }

    public void completarFormulario() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Completa el formulario:");
        System.out.print("Nombre: ");
        this.firstName = scanner.nextLine();
        System.out.print("Apellido: ");
        this.lastName = scanner.nextLine();
        System.out.print("Email: ");
        this.email = scanner.nextLine();
        System.out.print("Teléfono: ");
        this.phone = scanner.nextLine();

        System.out.print("Desea agregar otro dato?\n");
        System.out.print("*--------------------------*\n");
        System.out.print("1.Si 0.No \n");
        this.flag = Integer.parseInt(scanner.nextLine());
        while(this.flag == 1){
            System.out.print("Ingrese el nombre del campo: ");
            String campo = scanner.nextLine();
            System.out.print("Ingrese el valor del campo: ");
            String valor = scanner.nextLine();
            this.nuevosDatos.put(campo, valor); //nuevosDatos no existe en la clase formulario.
            System.out.print("Desea agregar otro dato?");
            System.out.print("*--------------------------*\n");
            System.out.print("1.Si 0.No ");
            this.flag = Integer.parseInt(scanner.nextLine());
        }

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Map<String, String> getNuevosDatos() {
        return nuevosDatos;
    }

}
