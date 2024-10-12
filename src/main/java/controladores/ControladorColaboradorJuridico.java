package controladores;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.usuarios.*;
import dtos.ColaboradorJuridicoDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioUsuarios;
import utils.ICrudViewsHandler;
import io.javalin.http.HttpStatus;
import io.javalin.validation.Validation;
import io.javalin.validation.ValidationError;
import io.javalin.validation.Validator;
import java.util.*;

public class ControladorColaboradorJuridico implements ICrudViewsHandler, WithSimplePersistenceUnit {

    private RepositorioColaboradores repositorioColaboradores;
    private RepositorioUsuarios repositorioUsuarios;

    public ControladorColaboradorJuridico(RepositorioColaboradores repositorioColaboradores,RepositorioUsuarios repositorioUsuarios) {
        this.repositorioColaboradores = repositorioColaboradores;
        this.repositorioUsuarios = repositorioUsuarios;
    }

    @Override //LISTO
    public void index(Context context){
        List<ColaboradorJuridico> colaboradoresJuridicos = repositorioColaboradores.obtenerColaboradoresJuridicos();
        List<ColaboradorJuridicoDTO> colaboradoresJuridicosDTO = new ArrayList<>();

        for (ColaboradorJuridico colaboradorJuridico : colaboradoresJuridicos){
            try{
                ColaboradorJuridicoDTO colaboradorJuridicoDTO = new ColaboradorJuridicoDTO();
                colaboradorJuridicoDTO.setId(colaboradorJuridico.getId());
                colaboradorJuridicoDTO.setActivo(colaboradorJuridico.getActivo());
                colaboradorJuridicoDTO.setEmail(colaboradorJuridico.email());
                colaboradorJuridicoDTO.setRazonSocial(colaboradorJuridico.getRazonSocial());
                colaboradorJuridicoDTO.setRubro(String.valueOf(colaboradorJuridico.getTipoDeRubro()));
                colaboradorJuridicoDTO.setPuntosAcumulados(colaboradorJuridico.puntosAcumulados);
                colaboradorJuridicoDTO.setTipoRazonSocial(String.valueOf(colaboradorJuridico.getTipoRazonSocial()));

                colaboradoresJuridicosDTO.add(colaboradorJuridicoDTO);

            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }

        Map<String,List<ColaboradorJuridicoDTO>> model = new HashMap<>();
        model.put("colaboradoresJuridicos",colaboradoresJuridicosDTO);
        context.render("/dashboard/juridicos.hbs", model);

    }

    @Override //LISTO
    public void create(Context context) {
        Validator<String> tipoRazonSocial = context.formParamAsClass("tipoRazonSocial", String.class)
                .check(Objects::nonNull, "El tipo de razon social del colaborador es obligatorio");
        Validator<String> razonSocial = context.formParamAsClass("razonSocial", String.class)
                .check(Objects::nonNull, "La razon social del colaborador es obligatorio");
        Validator<String> email = context.formParamAsClass("email", String.class)
                .check(Objects::nonNull, "El email del colaborador es obligatorio");
        Validator<String> rubro = context.formParamAsClass("rubro", String.class)
                .check(Objects::nonNull, "El rubro del colaborador es obligatorio");

        boolean activo = context.formParam("estadoJuridico")!= null;

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(tipoRazonSocial,razonSocial,email,rubro);

        if(!errors.isEmpty()){
            System.out.println(errors);
            context.redirect("/dashboard/juridicos"); // TODO -> Pantalla del form pero mencionando los errores al usuario
            return;
        }

        ColaboradorJuridicoDTO colaboradorJuridicoDTO = new ColaboradorJuridicoDTO();
        colaboradorJuridicoDTO.setActivo(activo);
        colaboradorJuridicoDTO.setRubro(rubro.get());
        colaboradorJuridicoDTO.setTipoRazonSocial(tipoRazonSocial.get());
        colaboradorJuridicoDTO.setRazonSocial(razonSocial.get());
        colaboradorJuridicoDTO.setEmail(email.get());

        ColaboradorJuridico colaboradorJuridico1 = new ColaboradorJuridico();
        colaboradorJuridico1.setActivo(colaboradorJuridicoDTO.getActivo());
        colaboradorJuridico1.setTipoDeRubro(Rubro.valueOf(colaboradorJuridicoDTO.getRubro()));
        colaboradorJuridico1.setTipoRazonSocial(TipoRazonSocial.valueOf(colaboradorJuridicoDTO.getTipoRazonSocial()));
        colaboradorJuridico1.setRazonSocial(colaboradorJuridicoDTO.getRazonSocial());

        Email email1 = new Email(colaboradorJuridicoDTO.getEmail());
        colaboradorJuridico1.agregarMedioDeContacto(email1);

        colaboradorJuridico1.puntosAcumulados = 0;

        withTransaction(()->{
            repositorioColaboradores.guardar(colaboradorJuridico1);
        });
        context.redirect("/dashboard/juridicos");
    }

    @Override //LISTO
    public void edit(Context context) {

        Optional<Object> posibleJuridico = repositorioColaboradores.buscarPorID(ColaboradorJuridico.class,Long.valueOf(context.pathParam("id")));

        if(posibleJuridico.isEmpty()){
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

            ColaboradorJuridico colaboradorJuridico = (ColaboradorJuridico) posibleJuridico.get();

            ColaboradorJuridicoDTO colaboradorJuridicoDTO = new ColaboradorJuridicoDTO();

            colaboradorJuridicoDTO. setId               (colaboradorJuridico.getId());
            colaboradorJuridicoDTO. setEmail            (colaboradorJuridico.email());
            colaboradorJuridicoDTO. setActivo           (colaboradorJuridico.getActivo());
            colaboradorJuridicoDTO. setRubro            (String.valueOf(colaboradorJuridico.getTipoDeRubro()));
            colaboradorJuridicoDTO. setRazonSocial      (colaboradorJuridico.getRazonSocial());
            colaboradorJuridicoDTO. setTipoRazonSocial  (String.valueOf(colaboradorJuridico.getTipoRazonSocial()));
            colaboradorJuridicoDTO. setPuntosAcumulados (colaboradorJuridico.puntosAcumulados);

            Map<String, Object> model = new HashMap<>();
            model.put("colaboradoresJuridicos",colaboradorJuridicoDTO);
            model.put("action","/dashboard/juridicos/"+ colaboradorJuridicoDTO.getId()+ "/edit");

            context.render("/dashboard/forms/juridico.hbs", model);
    }

    @Override //LISTO
    public void update(Context context) {
        Validator<String> tipoRazonSocial = context.formParamAsClass("tipoRazonSocial", String.class)
                .check(Objects::nonNull, "El tipo de razon social del colaborador es obligatorio");
        Validator<String> razonSocial = context.formParamAsClass("razonSocial", String.class)
                .check(Objects::nonNull, "La razon social del colaborador es obligatorio");
        Validator<String> email = context.formParamAsClass("email", String.class)
                .check(Objects::nonNull, "El email del colaborador es obligatorio");
        Validator<String> rubro = context.formParamAsClass("rubro", String.class)
                .check(Objects::nonNull, "El rubro del colaborador es obligatorio");

        boolean activo = context.formParam("activo")!= null;

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(tipoRazonSocial,razonSocial,email,rubro);

        if(!errors.isEmpty()){
            context.sessionAttribute("errors", errors);
            context.render("/dashboard/forms/juridico.hbs");
            return;
        }

        Optional<Object> posibleJuridico =
                this.repositorioColaboradores.buscarPorID(ColaboradorJuridico.class,Long.valueOf(context.pathParam("id")));

        if (posibleJuridico.isEmpty()) {
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        ColaboradorJuridico colaboradorJuridico = (ColaboradorJuridico) posibleJuridico.get();

        colaboradorJuridico.setActivo(activo);
        colaboradorJuridico.setTipoDeRubro(Rubro.valueOf(rubro.get()));
        colaboradorJuridico.setTipoRazonSocial(TipoRazonSocial.valueOf(tipoRazonSocial.get()));
        colaboradorJuridico.setRazonSocial(razonSocial.get());
        Set<MedioDeContacto> medioDeContactos = colaboradorJuridico.getMediosDeContacto();
        Optional<Email> medioDeContacto = medioDeContactos.stream()
                .filter(v -> v instanceof Email) // Verifica si es instancia de Email
                .map(v -> (Email) v) // Cast a Email
                .findFirst();
        if (medioDeContacto.isPresent()){
            medioDeContacto.get().setEmail(email.get());
        }

        withTransaction(() -> {
            repositorioColaboradores.actualizar(colaboradorJuridico);
        });

        context.redirect("/dashboard/juridicos");

    }

    @Override //LISTO
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

    @Override //LISTO
    public void save(Context context){
//        Validator<String> tipoRazonSocial = context.formParamAsClass("tipoRazonSocial", String.class)
//                .check(Objects::nonNull, "El tipo de razon social del colaborador es obligatorio");
//        Validator<String> razonSocial = context.formParamAsClass("razonSocial", String.class)
//                .check(Objects::nonNull, "La razon social del colaborador es obligatorio");
//        Validator<String> email = context.formParamAsClass("email", String.class)
//                .check(Objects::nonNull, "El email del colaborador es obligatorio");
//        Validator<String> rubro = context.formParamAsClass("rubro", String.class)
//                .check(Objects::nonNull, "El rubro del colaborador es obligatorio");
//
//        boolean activo = context.formParam("estadoColaboradorJuridico")!= null;
//
//        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(tipoRazonSocial,razonSocial,email,rubro);
//
//
//        if(!errors.isEmpty()){
//            System.out.println(errors);
//            context.redirect("/dashboard/juridicos"); // TODO -> Pantalla del form pero mencionando los errores al usuario
//            return;
//        }
//
//        withTransaction(()->{
//            ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico();
//            colaboradorJuridico.setTipoRazonSocial(TipoRazonSocial.valueOf(tipoRazonSocial.get()));
//            colaboradorJuridico.setRazonSocial(razonSocial.get());
//            // OJO !!! colaboradorJuridico.setEmail(email.get());
//            colaboradorJuridico.setTipoDeRubro(Rubro.valueOf(rubro.get()));
//            colaboradorJuridico.setActivo(activo);
//
//            repositorioColaboradores.guardar(colaboradorJuridico);
//
//        });
//        context.redirect("/dashboard/juridicos"); //TODO realizar pantalla de exito de la creacion de colab

        withTransaction(()->{
            String razonSocial = context.formParam("razonSocial");
            String tipoOrganizacion = context.formParam("tipoRazonSocial");
            String rubro = context.formParam("tipoRubro");
            String email = context.formParam("email");
            String contrasenia = context.formParam("password");

            TipoRazonSocial tipoRazonSocial = TipoRazonSocial.valueOf(tipoOrganizacion.toUpperCase());
            Rubro tipoRubro = Rubro.valueOf(rubro.toUpperCase());
            ColaboradorJuridico colaborador = new ColaboradorJuridico(razonSocial,tipoRazonSocial,tipoRubro);
            Usuario usuario = new Usuario(email,contrasenia);

            //Guardado de datos
            repositorioColaboradores.guardar(colaborador);
            repositorioUsuarios.guardar(usuario);
            context.redirect("/");
        });

    }

    @Override
    public void show(Context context) {

    }

}
