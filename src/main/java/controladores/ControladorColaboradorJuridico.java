package controladores;

import domain.contacto.Email;
import domain.usuarios.ColaboradorJuridico;
import domain.usuarios.Rubro;
import domain.usuarios.TipoRazonSocial;
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
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }

        Map<String,List<ColaboradorJuridicoDTO>> model = new HashMap<>();
        model.put("tecnicos",colaboradoresJuridicosDTO);
        context.render("/dashboard/juridicos.hbs", model);

    }

    @Override //LISTO
    public void create(Context context) {
        Map<String,Object> modal = new HashMap<>();
        modal.put("action","/dashboard/juridicos");
        modal.put("edit",false);
        context.render("/dashboard/forms/juridico.hbs",modal);
    }

    @Override //LISTO
    public void edit(Context context) {
        String idParam =context.pathParam("id");

        Optional<Object> posibleJuridico = repositorioColaboradores.buscarPorID(ColaboradorJuridico.class,Long.parseLong(idParam));

        if(posibleJuridico.isPresent()){
            ColaboradorJuridico colaboradorJuridico = (ColaboradorJuridico) posibleJuridico.get();

            ColaboradorJuridicoDTO colaboradorJuridicoDTO = new ColaboradorJuridicoDTO();

            colaboradorJuridicoDTO.setId(colaboradorJuridico.getId());
            colaboradorJuridicoDTO.setEmail(colaboradorJuridico.email());
            colaboradorJuridicoDTO.setActivo(colaboradorJuridico.getActivo());
            colaboradorJuridicoDTO.setRubro(String.valueOf(colaboradorJuridico.getTipoDeRubro()));
            colaboradorJuridicoDTO.setRazonSocial(colaboradorJuridico.getRazonSocial());
            colaboradorJuridicoDTO.setTipoRazonSocial(String.valueOf(colaboradorJuridico.getTipoRazonSocial()));
            colaboradorJuridicoDTO.setPuntosAcumulados(colaboradorJuridico.puntosAcumulados);

            Map<String, Object> model = new HashMap<>();
            model.put("colaboradorJuridico",colaboradorJuridicoDTO);
            model.put("edit",true);
            model.put("action","/dashboard/juridicos/"+idParam+"/edit");

            context.render("/dashboard/forms/juridico.hbs",model);
        }

        else{
            context.status(HttpStatus.NOT_FOUND);
        }
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

        boolean activo = context.formParam("estadoColaboradorJuridico")!= null;

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(tipoRazonSocial,razonSocial,email,rubro);

        if(!errors.isEmpty()){
            System.out.println(errors);
            context.redirect("/dashboard/juridicos"); // TODO -> Pantalla del form pero mencionando los errores al usuario
            return;
        }

        String idParam = context.pathParam("id");
        Optional<Object> posibleJuridico = repositorioColaboradores.buscarPorID(ColaboradorJuridico.class,Long.parseLong(idParam));

        if(posibleJuridico.isPresent()) {
            withTransaction(() -> {

                ColaboradorJuridico colaboradorJuridico = (ColaboradorJuridico) posibleJuridico.get();

                colaboradorJuridico.setActivo(activo);
                colaboradorJuridico.setTipoDeRubro(Rubro.valueOf(rubro.get()));
                colaboradorJuridico.setTipoRazonSocial(TipoRazonSocial.valueOf(tipoRazonSocial.get()));
                colaboradorJuridico.setRazonSocial(razonSocial.get());
                //colaboradorJuridico.setEmail(email.get());

                repositorioColaboradores.actualizar(colaboradorJuridico);

            });

            context.redirect("/dashboard/juridicos"); //TODO realizar pantalla de exito de la creacion de juridicos
        }

        else{
            context.status(HttpStatus.NOT_FOUND);
        }

    }

    @Override //LISTO
    public void delete(Context context) {
        String idParam = context.pathParam("id");
        Map<String, Object> model = new HashMap<>();
        model.put("action","/dashboard/juridicos/"+idParam+"/delete");
        context.render("/dashboard/delete/juridico.hbs",model);
    }

    @Override //LISTO
    public void save(Context context){
        Validator<String> tipoRazonSocial = context.formParamAsClass("tipoRazonSocial", String.class)
                .check(Objects::nonNull, "El tipo de razon social del colaborador es obligatorio");
        Validator<String> razonSocial = context.formParamAsClass("razonSocial", String.class)
                .check(Objects::nonNull, "La razon social del colaborador es obligatorio");
        Validator<String> email = context.formParamAsClass("email", String.class)
                .check(Objects::nonNull, "El email del colaborador es obligatorio");
        Validator<String> rubro = context.formParamAsClass("rubro", String.class)
                .check(Objects::nonNull, "El rubro del colaborador es obligatorio");

        boolean activo = context.formParam("estadoColaboradorJuridico")!= null;

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(tipoRazonSocial,razonSocial,email,rubro);


        if(!errors.isEmpty()){
            System.out.println(errors);
            context.redirect("/dashboard/juridicos"); // TODO -> Pantalla del form pero mencionando los errores al usuario
            return;
        }

        withTransaction(()->{
            ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico();
            colaboradorJuridico.setTipoRazonSocial(TipoRazonSocial.valueOf(tipoRazonSocial.get()));
            colaboradorJuridico.setRazonSocial(razonSocial.get());
            // OJO !!! colaboradorJuridico.setEmail(email.get());
            colaboradorJuridico.setTipoDeRubro(Rubro.valueOf(rubro.get()));
            colaboradorJuridico.setActivo(activo);

            repositorioColaboradores.guardar(colaboradorJuridico);

        });
        context.redirect("/dashboard/juridicos"); //TODO realizar pantalla de exito de la creacion de colab

    }

    @Override
    public void show(Context context) {

    }

}
