package controladores;

import domain.usuarios.ColaboradorJuridico;
import domain.usuarios.Rubro;
import domain.usuarios.TipoRazonSocial;
import domain.usuarios.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioUsuarios;
import utils.ICrudViewsHandler;

import io.javalin.http.Context;

import java.util.Locale;

public class ControladorColaboradorJuridico implements ICrudViewsHandler, WithSimplePersistenceUnit {
    private RepositorioColaboradores repositorioColaboradores;
    private RepositorioUsuarios repositorioUsuarios;
    public ControladorColaboradorJuridico(RepositorioColaboradores repositorioColaboradores, RepositorioUsuarios repositorioUsuarios) {
        this.repositorioColaboradores = repositorioColaboradores;
        this.repositorioUsuarios = repositorioUsuarios;
    }
    @Override
    public void index(Context context){
        //TODO metodo para renderizar todos los colaboradores
    }
    @Override
    public void save(Context context){
        withTransaction(()->{
            String razonSocial = context.formParam("razonSocial");
            String tipoOrganizacion = context.formParam("tipoOrganizacion");
            String rubro = context.formParam("tipoRubro");
            String email = context.formParam("emailUsuario");
            String contrasenia = context.formParam("password");

            TipoRazonSocial tipoRazonSocial = TipoRazonSocial.valueOf(tipoOrganizacion.toUpperCase());
            Rubro tipoRubro = Rubro.valueOf(rubro.toUpperCase());
            ColaboradorJuridico colaborador = new ColaboradorJuridico(razonSocial,tipoRazonSocial,tipoRubro);
            Usuario usuario = new Usuario(email,contrasenia);

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
    public void create(Context context) {

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
}
