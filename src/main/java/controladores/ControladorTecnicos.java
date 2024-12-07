package controladores;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telegram;
import domain.contacto.Whatsapp;
import domain.formulario.documentos.Cuil;
import domain.formulario.documentos.Documento;
import domain.formulario.documentos.TipoDocumento;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.geografia.area.AreaDeCobertura;
import domain.geografia.area.TamanioArea;
import domain.heladera.Heladera.Heladera;
import domain.usuarios.Tecnico;
import domain.visitas.Visita;
import domain.visitas.VisitaFactory;
import dtos.TecnicoDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UploadedFile;
import io.javalin.validation.NullableValidator;
import io.javalin.validation.Validation;
import io.javalin.validation.ValidationError;
import io.javalin.validation.Validator;
import org.jetbrains.annotations.NotNull;
import repositorios.Repositorio;
import repositorios.repositoriosBDD.RepositorioHeladeras;
import repositorios.repositoriosBDD.RepositorioTecnicos;
import repositorios.repositoriosBDD.RepositorioVisitasTecnicas;
import utils.ICrudViewsHandler;
import utils.uploadImage.ImageUpload;

import javax.swing.text.html.Option;
import java.util.*;

public class ControladorTecnicos implements ICrudViewsHandler, WithSimplePersistenceUnit {
    private RepositorioTecnicos repositorioTecnicos;
    private Repositorio repositorio;
    private RepositorioHeladeras repositorioHeladeras;
    private RepositorioVisitasTecnicas repositorioVisitasTecnicas;

    public ControladorTecnicos(RepositorioTecnicos repositorioTecnicos,Repositorio repositorio, RepositorioHeladeras repositorioHeladeras,RepositorioVisitasTecnicas repositorioVisitasTecnicas) {
        this.repositorioTecnicos = repositorioTecnicos;
        this.repositorio = repositorio;
        this.repositorioHeladeras = repositorioHeladeras;
        this.repositorioVisitasTecnicas= repositorioVisitasTecnicas;
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

    public void actualizar(Context context) {
        Validator<String> nombreTecnico = context.formParamAsClass("campo_nombre_tecnico", String.class)
                .check(v -> !v.isEmpty()  , "El nombre es obligatorio")
                .check(v -> v.chars().noneMatch(Character::isDigit),"No se permite numeros en el nombre");
        Validator<String> apellidoTecnico = context.formParamAsClass("campo_apellido_tecnico", String.class)
                .check(v -> !v.isEmpty()  , "El apellido es obligatorio")
                .check(v -> v.chars().noneMatch(Character::isDigit),"No se permite numeros en el apellido");
        Validator<TipoDocumento> tipoDocumento = context.formParamAsClass("campo_tipo_documento_tecnico", TipoDocumento.class);
        Validator<String> nroDocumento = context.formParamAsClass("campo_nro_documento_tecnico", String.class)
                .check(v -> !v.isEmpty()  , "El nro de documento  es obligatorio");
        Cuil cuil = new Cuil(Objects.requireNonNull(context.formParam("campo_cuil_tecnico")));
        NullableValidator<String> whatsappTecnico = context.formParamAsClass("campo_whatsapp_tecnico",String.class).allowNullable();
        NullableValidator<String> telegramTecnico = context.formParamAsClass("campo_telegram_tecnico",String.class).allowNullable();
        NullableValidator<String> emailTecnico = context.formParamAsClass("campo_email_tecnico",String.class).allowNullable();
        Validator<String> notificacionPreferida = context.formParamAsClass("campo_contacto_preferido_tecnico",String.class);

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(nombreTecnico,apellidoTecnico,tipoDocumento,nroDocumento,whatsappTecnico,telegramTecnico,emailTecnico,notificacionPreferida);

        if(!errors.isEmpty()){
            System.out.println(errors);
            context.redirect("/dashboard/tecnicos"); // TODO -> Pantalla del form pero mencionando los errores al usuario
        }

        //Caso exito
        Long tecnicoId = context.sessionAttribute("id_colaborador");
        Optional<Object> posibleTecnico = repositorioTecnicos.buscarPorID(Tecnico.class, tecnicoId);

        if (posibleTecnico.isPresent()) {
            withTransaction(() -> {
                Tecnico tecnico = (Tecnico) posibleTecnico.get();
                Documento documento = tecnico.getDocumento();

                // Actualizar datos básicos del técnico
                tecnico.setNombre(nombreTecnico.get());
                tecnico.setApellido(apellidoTecnico.get());
                documento.setTipo(tipoDocumento.get());
                documento.setNumeroDeDocumento(nroDocumento.get());
                tecnico.setCuil(cuil);

                // Obtener el medio de contacto preferido
                String preferido = notificacionPreferida.get();

                // Actualizar medios de contacto
                for (MedioDeContacto medio : tecnico.getMediosDeContacto()) {
                    medio.setNotificar(false); //Reinicio para que se elija al nuevo preferido
                    if (medio instanceof Whatsapp) {
                        String numero = whatsappTecnico.get();
                        if (numero != null && !numero.trim().isEmpty()) {
                            ((Whatsapp) medio).setNumero(numero);
                        }
                        if ("notificarWhatsapp".equals(preferido)) {
                            medio.setNotificar(true);
                        }
                    } else if (medio instanceof Telegram) {
                        String username = telegramTecnico.get();
                        if (username != null && !username.trim().isEmpty()) {
                            ((Telegram) medio).setUsername(username);
                        }
                        if ("notificarTelegram".equals(preferido)) {
                            medio.setNotificar(true);
                        }
                    } else if (medio instanceof Email) {
                        String email = emailTecnico.get();
                        if (email != null && !email.trim().isEmpty()) {
                            ((Email) medio).setEmail(email);
                        }
                        if ("notificarEmail".equals(preferido)) {
                            medio.setNotificar(true);
                        }
                    }
                }
                repositorioTecnicos.actualizar(tecnico);
            });
            context.redirect("/profile");
        } else {
            context.status(HttpStatus.NOT_FOUND);
        }
    }
    public void notificaciones(@NotNull Context context) {
        context.render("/home/notificaciones/notificaciones.hbs");
    }
    public void visitas(@NotNull Context context) {
        Map<String, Object> model = new HashMap<>();
        Long idTecnico = context.sessionAttribute("id_colaborador");
        List<Visita> visitas = repositorioVisitasTecnicas.buscarVisitasPorTecnicoId(idTecnico);
        Optional<Object> posibleTecnico = repositorioTecnicos.buscarPorID(Tecnico.class, idTecnico);
        Tecnico tecnico = (Tecnico) posibleTecnico.get();

        model.put("visitas",visitas);
        context.render("/home/visitas/visitas.hbs",model);
    }

    public void repararHeladera(Context context) {
        try {
            String imagePath = null;
            UploadedFile imagen = context.uploadedFile("imagen-falla");
            
            // Solo procesar la imagen si se subió un archivo y no está vacío
            if (imagen != null && imagen.size() > 0) {
                imagePath = ImageUpload.saveImage(imagen);
            }

            // Obtener otros parámetros del formulario
            String nombreEstacion = context.formParam("nombre-estacion-modal");
            String fechaAsistencia = context.formParam("fecha-asistencia");
            String descripcionFalla = context.formParam("descripcion-falla");
            boolean reparada = Boolean.parseBoolean(context.formParam("reparada"));
            Long tecnicoId = context.sessionAttribute("id_colaborador");
            Optional<Object> posibleTecnico = repositorioTecnicos.buscarPorID(Tecnico.class, tecnicoId);
            Optional<Heladera> posibleHeladera = repositorioHeladeras.obtenerHeladeraPorNombre(nombreEstacion);
            if(posibleTecnico.isEmpty()){
                System.out.println("No existe el tecnico");
                context.redirect("/visitas");
            }
            if(posibleHeladera.isEmpty()){
                System.out.println("No existe la heladera");
                context.redirect("/visitas");
            }

            Tecnico tecnico = (Tecnico) posibleTecnico.get();
            Heladera heladera = posibleHeladera.get();
            Visita visitaTecnica = VisitaFactory.crearVisita(tecnico, heladera, descripcionFalla, imagePath, reparada);

            withTransaction(()->{
                repositorio.guardar(visitaTecnica);
            });
            context.redirect("/visitas");
        } catch (Exception e) {
            context.status(500);
            context.result("Error al procesar la solicitud");
        }
    }
}
