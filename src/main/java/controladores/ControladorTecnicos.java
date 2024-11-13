package controladores;

import domain.formulario.documentos.Cuil;
import domain.formulario.documentos.Documento;
import domain.formulario.documentos.TipoDocumento;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.geografia.area.AreaDeCobertura;
import domain.geografia.area.TamanioArea;
import domain.usuarios.Tecnico;
import dtos.TecnicoDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.validation.Validation;
import io.javalin.validation.ValidationError;
import io.javalin.validation.Validator;
import repositorios.repositoriosBDD.RepositorioTecnicos;
import utils.ICrudViewsHandler;

import java.util.*;

public class ControladorTecnicos implements ICrudViewsHandler, WithSimplePersistenceUnit {
    private RepositorioTecnicos repositorioTecnicos;

    public ControladorTecnicos(RepositorioTecnicos repositorioTecnicos) {
        this.repositorioTecnicos = repositorioTecnicos;
    }

    @Override
    public void index(Context context) {
        List<Tecnico> tecnicos = repositorioTecnicos.buscarTodosTecnicos();
        List<TecnicoDTO> tecnicosDTO = new ArrayList<>();
        for (Tecnico tecnico : tecnicos) {
            try {
                TecnicoDTO tecnicoDTO = this.convertToDTO(tecnico);
                tecnicosDTO.add(tecnicoDTO);
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        Map<String,List<TecnicoDTO>> model = new HashMap<>();
        model.put("tecnicos",tecnicosDTO);
        context.render("/dashboard/tecnicos.hbs",model);
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {
        Map<String,Object> modal = new HashMap<>();
        modal.put("action","/dashboard/tecnicos");
        modal.put("edit",false);
        context.render("/dashboard/forms/tecnico.hbs",modal);
    }

    @Override
    public void save(Context context) {
        //Validaciones
        Validator<String> nombreTecnico = context.formParamAsClass("nombreTecnico", String.class)
                .check(v -> !v.isEmpty()  , "El nombre es obligatorio")
                .check(v -> v.chars().noneMatch(Character::isDigit),"No se permite numeros en el nombre");
        Validator<String> apellidoTecnico = context.formParamAsClass("apellidoTecnico", String.class)
                .check(v -> !v.isEmpty()  , "El apellido es obligatorio")
                .check(v -> v.chars().noneMatch(Character::isDigit),"No se permite numeros en el apellido");
        Validator<TipoDocumento> tipoDocumento = context.formParamAsClass("tipoDocumento", TipoDocumento.class);
        Validator<String> nroDocumento = context.formParamAsClass("nroDocumento", String.class)
                .check(v -> !v.isEmpty()  , "El nro de documento  es obligatorio");
        Validator<String> calle = context.formParamAsClass("calle", String.class)
                .check(v -> !v.isEmpty() , "La calle de la heladera es obligatorio");
        Validator<String> altura = context.formParamAsClass("altura", String.class)
                .check(v -> !v.isEmpty(), "La altura es obligatorio"); //Deberia ser integer la altura,tener en cuenta
        Validator<TamanioArea> tamanioArea = context.formParamAsClass("tamanioArea", TamanioArea.class);
        Boolean activo = context.formParam("estadoTecnico")!=null;

        //Errores
        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(nombreTecnico,apellidoTecnico,tipoDocumento,calle,altura,tamanioArea);

        if(!errors.isEmpty()){
            System.out.println(errors);
            context.redirect("/dashboard/tecnicos"); // TODO -> Pantalla del form pero mencionando los errores al usuario
            return;
        }

        //EXITO
        withTransaction(()->{

            Documento documento = new Documento(tipoDocumento.get(),nroDocumento.get());
            //Harcodeo la lat y long, pero no deberia ser obligatorio asi que se debe sacar como nullable=false
            Ubicacion ubicacion = new Ubicacion(1f,2f,new Calle(calle.get(),altura.get()));
            AreaDeCobertura areaDeCobertura = new AreaDeCobertura(ubicacion,tamanioArea.get());
            //Hardcodeo el cuil ya que aun no se define el como lo obtenemos desde el usuario
            Cuil cuil = new Cuil("25",documento.getNumeroDeDocumento(),"9");
            Tecnico tecnico = new Tecnico(nombreTecnico.get(),apellidoTecnico.get(),documento,cuil);
            tecnico.setAreaDeCobertura(areaDeCobertura);
            tecnico.setActivo(activo);
            repositorioTecnicos.guardar(tecnico);
        });
        context.redirect("/dashboard/tecnicos"); //TODO realizar pantalla de exito de la creacion de tecnico
    }

    @Override
    public void edit(Context context) {
        String idParam =context.pathParam("id");
        Optional<Object> posibleTecnico = repositorioTecnicos.buscarPorID(Tecnico.class,Long.parseLong(idParam));
        if(posibleTecnico.isPresent()){
            Tecnico tecnico = (Tecnico) posibleTecnico.get();

            TecnicoDTO tecnicoDTO = this.convertToDTO(tecnico);

            Map<String, Object> model = new HashMap<>();
            model.put("tecnico",tecnicoDTO);
            model.put("edit",true);
            model.put("action","/dashboard/tecnicos/"+idParam+"/edit");
            context.render("/dashboard/forms/tecnico.hbs",model);
        }else{
            context.status(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public void update(Context context) {
        //Validaciones
        Validator<String> nombreTecnico = context.formParamAsClass("nombreTecnico", String.class)
                .check(v -> !v.isEmpty()  , "El nombre es obligatorio")
                .check(v -> v.chars().noneMatch(Character::isDigit),"No se permite numeros en el nombre");
        Validator<String> apellidoTecnico = context.formParamAsClass("apellidoTecnico", String.class)
                .check(v -> !v.isEmpty()  , "El apellido es obligatorio")
                .check(v -> v.chars().noneMatch(Character::isDigit),"No se permite numeros en el apellido");
        Validator<TipoDocumento> tipoDocumento = context.formParamAsClass("tipoDocumento", TipoDocumento.class);
        Validator<String> nroDocumento = context.formParamAsClass("nroDocumento", String.class)
                .check(v -> !v.isEmpty()  , "El nro de documento  es obligatorio");
        Validator<String> calle = context.formParamAsClass("calle", String.class)
                .check(v -> !v.isEmpty() , "La calle de la heladera es obligatorio");
        Validator<String> altura = context.formParamAsClass("altura", String.class)
                .check(v -> !v.isEmpty(), "La altura es obligatorio"); //Deberia ser integer la altura,tener en cuenta
        Validator<TamanioArea> tamanioArea = context.formParamAsClass("tamanioArea", TamanioArea.class);
        Boolean activo = context.formParam("estadoTecnico")!=null;

        //Errores
        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(nombreTecnico,apellidoTecnico,tipoDocumento,calle,altura,tamanioArea);

        if(!errors.isEmpty()){
            System.out.println(errors);
            context.redirect("/dashboard/tecnicos"); // TODO -> Pantalla del form pero mencionando los errores al usuario
            return;
        }
        //EXITO
        String idParam =context.pathParam("id");
        Optional<Object> posibleTecnico = repositorioTecnicos.buscarPorID(Tecnico.class,Long.parseLong(idParam));
        if(posibleTecnico.isPresent()){
            withTransaction(()->{
                Tecnico tecnico = (Tecnico) posibleTecnico.get();

                Documento documento = tecnico.getDocumento();
                Ubicacion ubicacion = tecnico.getArea().getUbicacionPrincipal();
                Calle calleObtenida = ubicacion.getCalle();
                AreaDeCobertura areaDeCobertura = tecnico.getArea();
                //Seteo
                tecnico.setNombre(nombreTecnico.get());
                tecnico.setApellido(apellidoTecnico.get());
                documento.setTipo(tipoDocumento.get());
                documento.setNumeroDeDocumento(nroDocumento.get());
                calleObtenida.setNombre(calle.get());
                calleObtenida.setAltura(altura.get());
                areaDeCobertura.setTamanioArea(tamanioArea.get());
                tecnico.setActivo(activo);
                repositorioTecnicos.actualizar(tecnico);
            });
            context.redirect("/dashboard/tecnicos"); //TODO realizar pantalla de exito de la creacion de tecnico
        }else{
            context.status(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void delete(Context context) {
        String idParam = context.pathParam("id");
        Map<String, Object> model = new HashMap<>();
        model.put("action","/dashboard/tecnicos/"+idParam+"/delete");
        context.render("/dashboard/delete/tecnico.hbs",model);
    }
    @Override
    public void remove(Context context) {
        String idParam =context.pathParam("id");
        Optional<Object> posibleTecnico = repositorioTecnicos.buscarPorID(Tecnico.class,Long.parseLong(idParam));
        if(posibleTecnico.isPresent()){
            withTransaction(()->{
                Tecnico tecnico = (Tecnico) posibleTecnico.get();
                tecnico.setActivo(false);
                repositorioTecnicos.actualizar(tecnico);
            });
            context.redirect("/dashboard/tecnicos"); //TODO realizar pantalla de exito de la creacion de tecnico
        }else{
            context.status(HttpStatus.NOT_FOUND);
        }
    }
    private TecnicoDTO convertToDTO(Tecnico tecnico){
        TecnicoDTO tecnicoDTO = new TecnicoDTO();
        tecnicoDTO.setId(tecnico.getId());
        tecnicoDTO.setActivo(tecnico.getActivo());
        tecnicoDTO.setNombre(tecnico.getNombre());
        tecnicoDTO.setApellido(tecnico.getApellido());
        tecnicoDTO.setNroDocumento(tecnico.getDocumento().getNumeroDeDocumento());
        tecnicoDTO.setTipoDocumento(tecnico.getDocumento().getTipo().toString());
/*        tecnicoDTO.setTamanioArea((tecnico.getArea() != null && tecnico.getArea().getTamanioArea() != null)
                ? tecnico.getArea().getTamanioArea().toString()
                : "");
        if(tecnico.getArea()!= null && tecnico.getArea().getUbicacionPrincipal()!=null && tecnico.getArea().getUbicacionPrincipal().getCalle()!= null){
            tecnicoDTO.setCalle(tecnico.getArea().getUbicacionPrincipal().getCalle().getNombre());
            tecnicoDTO.setAltura(tecnico.getArea().getUbicacionPrincipal().getCalle().getAltura());
        }else{
            tecnicoDTO.setCalle("");
            tecnicoDTO.setAltura("");
        }*/
        return tecnicoDTO;
    }
}
