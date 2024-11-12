package controladores;

import domain.usuarios.*;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioTecnicos;
import repositorios.repositoriosBDD.RepositorioUsuarios;
import repositorios.repositoriosBDD.RepositorioRoles;
import utils.ICrudViewsHandler;
import javax.persistence.NoResultException;
import java.util.*;
import java.util.stream.Collectors;

public class ControladorUsuario implements ICrudViewsHandler, WithSimplePersistenceUnit {
    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioRoles repositorioRoles;
    private RepositorioColaboradores repositorioColaboradores;
    private RepositorioTecnicos repositorioTecnicos;

    public ControladorUsuario(RepositorioUsuarios repositorioUsuarios, RepositorioRoles repositorioRoles,RepositorioColaboradores repositorioColaboradores,RepositorioTecnicos repositorioTecnicos) {
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioRoles = repositorioRoles;
        this.repositorioColaboradores = repositorioColaboradores;
        this.repositorioTecnicos = repositorioTecnicos;
    }

    public void login(Context ctx) {
        //Falta hacer validaciones
        String name = ctx.formParam("exampleInputEmail1");
        String password = ctx.formParam("exampleInputPassword1");

        Optional<Usuario> usuario = repositorioUsuarios.buscarPorNombre(name);
        if (usuario.isPresent()) {
            Usuario usuarioActual = usuario.get();
            if(password.equals(usuarioActual.getContrasenia())){
                List<Rol> roles = usuario.get().getRoles();
                List<String> rolesString = roles.stream()
                    .map(rol -> rol.getNombre().toString())
                    .collect(Collectors.toList());
                
                ctx.sessionAttribute("id_usuario", usuarioActual.getId());
                ctx.sessionAttribute("roles", rolesString);
                
                if(roles.stream().anyMatch(rol -> RoleENUM.ADMIN.equals(rol.getNombre()))){
                    ctx.redirect("/dashboard");
                }
                if(roles.stream().anyMatch(rol -> RoleENUM.FISICO.equals(rol.getNombre()))){
                    ctx.redirect("/home");
                }
                if(roles.stream().anyMatch(rol -> RoleENUM.JURIDICO.equals(rol.getNombre()))){
                    ctx.redirect("/home");
                }
                if(roles.stream().anyMatch(rol -> RoleENUM.TECNICO.equals(rol.getNombre()))){
                    ctx.redirect("/home");
                }

                //ACA se sigue haciendo el redirect en caso de que tenga el rol de colaborador y mandarlo a la interface de colaboradores
            }else{
                ctx.redirect("/"); //Mostrar error de contraseña invalida
            }
        }else{
            ctx.status(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public void show(Context context) {
        context.render("/dashboard/forms/ajustes.hbs");
    }

    @Override
    public void index(Context context) {

    }

    //Ejemplo de creación de ADMIN
    @Override
    public void create(Context ctx) {
        String nombre = ctx.queryParam("nombre");
        String password = ctx.queryParam("password");

        if (nombre == null || password == null) {
            ctx.status(400);
            ctx.result("Nombre y password son requeridos");
            return;
        }

        withTransaction(() -> {
            Rol rolAdmin;
            try {
                rolAdmin = repositorioRoles.buscarRolPorNombre(RoleENUM.ADMIN);
            } catch (NoResultException e) {
                rolAdmin = new Rol(RoleENUM.ADMIN);
                repositorioRoles.guardar(rolAdmin);
            }

            // Crear usuario
            Usuario usuario = new Usuario(nombre, password);
            usuario.agregarRol(rolAdmin);

            repositorioUsuarios.guardar(usuario);

            ctx.sessionAttribute("nombre", nombre);
            ctx.sessionAttribute("id_usuario", usuario.getId());
            List<String> rolesString = usuario.getRoles().stream()
                    .map(rol -> rol.getNombre().toString())
                    .collect(Collectors.toList());
            ctx.sessionAttribute("roles", rolesString);

        });

        ctx.redirect("/dashboard");
    }

    @Override
    public void save(Context context) {

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
    public void home(Context ctx){
        Map<String,Object> modal = new HashMap<>();
        List<String> roles = ctx.sessionAttribute("roles");
        boolean esTecnico = roles != null && roles.contains(RoleENUM.TECNICO.toString());
        boolean esFisico = roles != null && roles.contains(RoleENUM.FISICO.toString());
        boolean esJuridico = roles != null && roles.contains(RoleENUM.JURIDICO.toString());

        modal.put("tecnico",esTecnico);
        modal.put("fisico",esFisico);
        modal.put("juridico",esJuridico);
        ctx.render("home/home.hbs",modal);
    }
    public void perfil(Context ctx) {
        Long usuarioId = ctx.sessionAttribute("id_usuario");
        List<String> roles = ctx.sessionAttribute("roles");
        System.out.println(usuarioId);
        System.out.println(roles);
        Optional<Object> usuarioOptional =  repositorioUsuarios.buscarPorID(Usuario.class,usuarioId);
        
        if (usuarioOptional.isPresent()) {
            Map<String,Object> model = new HashMap<>();
            Usuario usuario = (Usuario) usuarioOptional.get();
            model.put("usuario", usuario);
            
            // Determinar qué vista renderizar según el rol
            if (roles.contains(RoleENUM.TECNICO.toString())) {
                Optional<Tecnico> tecnico = repositorioTecnicos.buscarTecPorIdUsuario(usuarioId);
                if(tecnico.isPresent()){
                    Tecnico tecnico1 = tecnico.get();
                    model.put("datos", tecnico1);
                }
                ctx.render("home/perfiles/tecnico.hbs", model);
            } 
            else if (roles.contains(RoleENUM.FISICO.toString())) {
                Optional<Colaborador> fisico = repositorioColaboradores.buscarColaboradorPorIdUsuario(usuarioId);
                if(fisico.isPresent()){
                    ColaboradorFisico colaborador = (ColaboradorFisico) fisico.get();
                    model.put("datos", colaborador);
                    System.out.println(colaborador);
                }
                ctx.render("home/perfiles/fisico.hbs", model);
            }
            else if (roles.contains(RoleENUM.JURIDICO.toString())) {
                Optional<Colaborador> juridico = repositorioColaboradores.buscarColaboradorPorIdUsuario(usuarioId);
                if(juridico.isPresent()){
                    ColaboradorJuridico colaborador = (ColaboradorJuridico) juridico.get();
                    System.out.println(colaborador);
                    model.put("datos", colaborador);
                }
                ctx.render("home/perfiles/juridico.hbs", model);
            }
        } else {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.result("Usuario no encontrado");
        }
    }
}
