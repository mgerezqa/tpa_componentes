package controladores;
import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.usuarios.*;
import dtos.requests.ColaboradorJuridicoInputDTO;
import dtos.responses.ColaboradorJuridicoOutputDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.HttpStatus;
import io.javalin.validation.Validation;
import io.javalin.validation.ValidationError;
import io.javalin.validation.Validator;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioUsuarios;
import services.interfaces.IServiceColaboradorJuridico;
import utils.ICrudViewsHandler;

import io.javalin.http.Context;

import java.util.*;

public class ControladorColaboradorJuridico implements ICrudViewsHandler, WithSimplePersistenceUnit {

    private RepositorioColaboradores repositorioColaboradores;
    private RepositorioUsuarios repositorioUsuarios;
    private IServiceColaboradorJuridico serviceColaboradorJuridico;

    public ControladorColaboradorJuridico(RepositorioColaboradores repositorioColaboradores,IServiceColaboradorJuridico serviceColaboradorJuridico,RepositorioUsuarios repositorioUsuarios) {
        this.repositorioColaboradores = repositorioColaboradores;
        this.serviceColaboradorJuridico = serviceColaboradorJuridico;
        this.repositorioUsuarios = repositorioUsuarios;
    }

    @Override //LISTO
    public void index(Context context){
        List<ColaboradorJuridicoOutputDTO> colaboradoresJuridicos = serviceColaboradorJuridico.obtenerTodos();
        Map<String, Object> model = new HashMap<>();
        model.put("colaboradoresJuridicos", colaboradoresJuridicos);
        context.render("/dashboard/juridicos.hbs", model);
    }

    @Override //LISTO
    public void create(Context context) {
        Validator<String> tipoRazonSocial = context.formParamAsClass("tipoRazonSocial", String.class)
                .check(Objects::nonNull, "El tipo razon social del colaborador es obligatorio");
        Validator<String> razonSocial = context.formParamAsClass("razonSocial", String.class)
                .check(Objects::nonNull, "La razon social del colaborador es obligatorio");
        Validator<String> email = context.formParamAsClass("email", String.class)
                .check(Objects::nonNull, "El email del colaborador es obligatorio");
        Validator<String> rubro = context.formParamAsClass("rubro", String.class)
                .check(Objects::nonNull, "El rubro del colaborador es obligatorio");

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(tipoRazonSocial,razonSocial,email,rubro);

        if(!errors.isEmpty()){
            System.out.println(errors);
            context.redirect("/dashboard/juridicos");
            return;
        }

        ColaboradorJuridicoInputDTO colaboradorJuridicoInputDTO = new ColaboradorJuridicoInputDTO();
        colaboradorJuridicoInputDTO.setRazonSocial(context.formParam("razonSocial"));
        colaboradorJuridicoInputDTO.setRubro(context.formParam("rubro"));
        colaboradorJuridicoInputDTO.setTipoRazonSocial(context.formParam("tipoRazonSocial"));
        colaboradorJuridicoInputDTO.setActivo(Boolean.valueOf(context.formParam("activo")));
        colaboradorJuridicoInputDTO.setEmail(context.formParam("email"));

        serviceColaboradorJuridico.crear(colaboradorJuridicoInputDTO);
        context.redirect("/dashboard/juridicos");
    }

    @Override //no funciona el "guardar cambios" ...
    public void edit(Context context) {
        Optional<Object> posibleColaborador =
                this.repositorioColaboradores.buscarPorID(ColaboradorJuridico.class, Long.valueOf(context.pathParam("id")));

        if(posibleColaborador.isEmpty()){
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        ColaboradorJuridico colaboradorJuridico = (ColaboradorJuridico) posibleColaborador.get();

        ColaboradorJuridicoInputDTO colaboradorJuridicoInputDTO = new ColaboradorJuridicoInputDTO();

        colaboradorJuridicoInputDTO.setId(colaboradorJuridico.getId());
        colaboradorJuridicoInputDTO.setTipoRazonSocial(String.valueOf(colaboradorJuridico.getTipoRazonSocial()));
        colaboradorJuridicoInputDTO.setRazonSocial(colaboradorJuridico.getRazonSocial());
        colaboradorJuridicoInputDTO.setRubro(String.valueOf(colaboradorJuridico.getTipoDeRubro()));
        colaboradorJuridicoInputDTO.setActivo(colaboradorJuridico.getActivo());
        colaboradorJuridicoInputDTO.setEmail(colaboradorJuridico.email());

        Map<String, Object> model = new HashMap<>();
        model.put("colaboradoresJuridicos", colaboradorJuridicoInputDTO);
        model.put("action","/dashboard/juridicos/" + colaboradorJuridicoInputDTO.getId()+ "/edit");

        context.render("dashboard/forms/juridico.hbs", model);
    }

    @Override //LISTO
    public void update(Context context) {
        Long colaboradorId = Long.valueOf(context.pathParam("id"));

        Validator<String> tipoRazonSocial = context.formParamAsClass("tipoRazonSocial", String.class)
                .check(Objects::nonNull, "El tipo razon social del colaborador es obligatorio");
        Validator<String> razonSocial = context.formParamAsClass("razonSocial", String.class)
                .check(Objects::nonNull, "La razon social del colaborador es obligatorio");
        Validator<String> email = context.formParamAsClass("email", String.class)
                .check(Objects::nonNull, "El email del colaborador es obligatorio");
        Validator<String> tipoDeRubro = context.formParamAsClass("tipoDeRubro", String.class)
                .check(Objects::nonNull, "El tipo de rubro del colaborador es obligatorio");

        boolean estado = context.formParam("activo") != null;

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(tipoRazonSocial,razonSocial,email,tipoDeRubro);

        if (!errors.isEmpty()) {
            context.sessionAttribute("errors", errors);
            Map<String, Object> model = new HashMap<>();
            model.put("errors", errors);
            context.render("/dashboard/forms/juridico.hbs", model);
            return;
        }

        // Buscar colaborador y actualizar
        Optional<Object> posibleColaborador =
                this.repositorioColaboradores.buscarPorID(ColaboradorJuridico.class, colaboradorId);

        if (posibleColaborador.isEmpty()) {
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        ColaboradorJuridico colaboradorJuridico = (ColaboradorJuridico) posibleColaborador.get();
        colaboradorJuridico.setActivo(estado);
        colaboradorJuridico.setRazonSocial(razonSocial.get());
        colaboradorJuridico.setTipoDeRubro(Rubro.valueOf(tipoDeRubro.get()));
        colaboradorJuridico.setTipoRazonSocial(TipoRazonSocial.valueOf(tipoRazonSocial.get()));

        Set<MedioDeContacto> medioDeContactos = colaboradorJuridico.getMediosDeContacto();
        Optional<Email> medioDeContacto = medioDeContactos.stream()
                .filter(v -> v instanceof Email) // Verifica si es instancia de Email
                .map(v -> (Email) v) // Cast a Email
                .findFirst();
        if (medioDeContacto.isPresent()){
            medioDeContacto.get().setEmail(email.get());
        }

        // Actualizar en transacción
        withTransaction(() -> {
            repositorioColaboradores.actualizar(colaboradorJuridico);
        });

        // Redireccionar después de la actualización
        context.redirect("/dashboard/juridicos");
    }

    @Override
    public void delete(Context context) {
        Optional<Object> posibleColaborador =
                this.repositorioColaboradores.buscarPorID(ColaboradorJuridico.class, Long.valueOf(context.pathParam("id")));

        if (posibleColaborador.isEmpty()) {
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        ColaboradorJuridico colaboradorJuridico = (ColaboradorJuridico) posibleColaborador.get();

        withTransaction(() -> {
            repositorioColaboradores.eliminar(colaboradorJuridico);
            System.out.println("Colaborador eliminado: " + colaboradorJuridico.getId());
        });

        context.redirect("/dashboard/juridicos");

    }

    @Override
    public void save(Context context){
//        withTransaction(()->{
//            String razonSocial = context.formParam("razonSocial");
//            String tipoOrganizacion = context.formParam("tipoOrganizacion");
//            String rubro = context.formParam("tipoRubro");
//            String email = context.formParam("emailUsuario");
//            String contrasenia = context.formParam("password");
//
//            TipoRazonSocial tipoRazonSocial = TipoRazonSocial.valueOf(tipoOrganizacion.toUpperCase());
//            Rubro tipoRubro = Rubro.valueOf(rubro.toUpperCase());
//            ColaboradorJuridico colaborador = new ColaboradorJuridico(razonSocial,tipoRazonSocial,tipoRubro);
//            Usuario usuario = new Usuario(email,contrasenia);
//
//            //Guardado de datos
//            repositorioColaboradores.guardar(colaborador);
//            repositorioUsuarios.guardar(usuario);
//            context.redirect("/"); //Sugerencia -> Redirección a una pantalla de exito del registro.
//        });
    }

    @Override
    public void show(Context context) {

    }

}
