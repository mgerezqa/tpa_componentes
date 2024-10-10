package controladores;


import domain.usuarios.ColaboradorFisico;
import domain.usuarios.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioUsuarios;
import utils.ICrudViewsHandler;
import io.javalin.http.Context;

public class ControladorColaboradorFisico implements ICrudViewsHandler, WithSimplePersistenceUnit {
    private RepositorioColaboradores repositorioColaboradores;
    private RepositorioUsuarios repositorioUsuarios;
    public ControladorColaboradorFisico(RepositorioColaboradores repositorioColaboradores, RepositorioUsuarios repositorioUsuarios) {
        this.repositorioColaboradores = repositorioColaboradores;
        this.repositorioUsuarios = repositorioUsuarios;
    }
    @Override
    public void index(Context context){
        //TODO metodo para renderizar todos los colaboradores
    }
    @Override
    public void create(Context context) {

    }
    @Override
    public void save(Context context){
        withTransaction(()->{
            //Instancias de entidades relacionadas al endpoint
            String nombre = context.formParam("nombre");
            String apellido = context.formParam("apellido");
            ColaboradorFisico colaborador = new ColaboradorFisico(nombre,apellido);
            String email = context.formParam("emailUsuario");
            String contrasenia = context.formParam("password");
            Usuario usuario = new Usuario(email,contrasenia);
            //Validación de repetición de usuario
            if(!repositorioUsuarios.buscarPorNombre(email).isEmpty()){
                context.redirect("Error al querer registrar usuario ya existente!");
                return;
            }
            //TODO -> Hacer validación de que no sea una contraseña tipica de los 10000
            //Guardado de datos
            repositorioColaboradores.guardar(colaborador);
            repositorioUsuarios.guardar(usuario);
            context.redirect("/"); //Sugerencia -> Redirección a una pantalla de exito del registro.
        });
    }
    @Override
    public void show(Context context) {
    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }
    @Override
    public void remove(Context context) {

    }
}
