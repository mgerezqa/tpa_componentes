package form_version_uno;

public class TestForm {

    public static void main(String[] args) {

        Formulario form = new Formulario();
        form.completarFormulario();

        Colaborador pepito = new Colaborador();
        pepito.actualizarInformacion(form.getFirstName(), form.getLastName(), form.getEmail(), form.getPhone(), form.getNuevosDatos());

        pepito.verificarInformacion();
    }
}
