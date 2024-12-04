package controladores;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telegram;
import domain.contacto.Whatsapp;
import domain.donaciones.MantenerHeladera;
import domain.geografia.*;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.puntos.CalculadoraPuntos;
import domain.puntos.CategoriaOferta;
import domain.puntos.Oferta;
import domain.puntos.TipoDeOferta;
import domain.usuarios.*;
import dtos.ColaboradorJuridicoDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.validation.NullableValidator;
import mappers.HeladeraMapper;
import mappers.dtos.HeladeraDTO;
import repositorios.Repositorio;
import repositorios.repositoriosBDD.*;
import utils.ICrudViewsHandler;
import io.javalin.http.HttpStatus;
import io.javalin.validation.Validation;
import io.javalin.validation.ValidationError;
import io.javalin.validation.Validator;

import java.util.*;
import java.util.stream.Collectors;

public class ControladorColaboradorJuridico implements ICrudViewsHandler, WithSimplePersistenceUnit {

    private RepositorioColaboradores repositorioColaboradores;
    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioRoles repositorioRoles;
    private RepositorioModeloHeladeras repositorioModeloHeladeras;
    private RepositorioProvincias repositorioProvincias;
    private RepositorioLocalidades repositorioLocalidades;
    private RepositorioBarrios repositorioBarrios;
    private Repositorio repositorio;
    private CalculadoraPuntos calculadoraPuntos;
    private RepositorioMantenciones repositorioMantenciones;
    public ControladorColaboradorJuridico(RepositorioColaboradores repositorioColaboradores,RepositorioUsuarios repositorioUsuarios, RepositorioRoles repositorioRoles,RepositorioModeloHeladeras repositorioModeloHeladeras,RepositorioProvincias repositorioProvincias,RepositorioLocalidades repositorioLocalidades,RepositorioBarrios repositorioBarrios, Repositorio repositorio,CalculadoraPuntos calculadoraPuntos,RepositorioMantenciones repositorioMantenciones) {
        this.repositorioColaboradores = repositorioColaboradores;
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioRoles = repositorioRoles;
        this.repositorioModeloHeladeras = repositorioModeloHeladeras;
        this.repositorioProvincias = repositorioProvincias;
        this.repositorioLocalidades = repositorioLocalidades;
        this.repositorioBarrios = repositorioBarrios;
        this.repositorio = repositorio;
        this.calculadoraPuntos = calculadoraPuntos;
        this.repositorioMantenciones = repositorioMantenciones;
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

    @Override
    public void remove(Context context) {
        Optional<Object> posibleJuridico = repositorioColaboradores.buscarPorID(ColaboradorJuridico.class, Long.valueOf(context.pathParam("id")));

        if(posibleJuridico.isPresent()){
            withTransaction(()->{
                ColaboradorJuridico colaboradorJuridico = (ColaboradorJuridico) posibleJuridico.get();
                colaboradorJuridico.setActivo(false);
                repositorioColaboradores.actualizar(colaboradorJuridico);
            });

            context.redirect("/dashboard/juridicos");
        }else{
            context.status(HttpStatus.NOT_FOUND);
        }
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

    public void signup(Context context) {
        // Validación de campos del formulario
        Validator<String> razonSocial = context.formParamAsClass("razonSocial", String.class)
            .check(v -> !v.isEmpty(), "La razón social es obligatoria");

        Validator<TipoRazonSocial> tipoOrganizacion = context.formParamAsClass("tipoOrganizacion", TipoRazonSocial.class)
            .check(Objects::nonNull, "El tipo de organización es obligatorio");

        Validator<Rubro> tipoRubro = context.formParamAsClass("tipoRubro", Rubro.class)
            .check(Objects::nonNull, "El rubro es obligatorio");

        NullableValidator<String> email = context.formParamAsClass("emailInput", String.class)
                .allowNullable();
        NullableValidator<String> wsp = context.formParamAsClass("nro_whatsapp", String.class)
                .allowNullable();
        NullableValidator<String> telegram = context.formParamAsClass("telegramInput", String.class)
                .allowNullable();
        //Opcionales (Opcional)
        NullableValidator<String> domicilio = context.formParamAsClass("domicilio", String.class)
                .allowNullable();

        //Datos para el usuario
        Validator<String> usuario = context.formParamAsClass("emailUsuario", String.class)
                .check(v -> !v.isEmpty()  , "El nombre de usuario o email del colaborador es obligatorio");
        //Falta agregar que no se pueda crear 2 usuarios con el mismo username.
        Validator<String> contrasenia = context.formParamAsClass("password", String.class)
                .check(v -> !v.isEmpty()  , "La contraseña del colaborador es obligatorio");

        Validator<String> repContrasenia = context.formParamAsClass("repeatPassword", String.class)
                .check(v -> !v.isEmpty()  , "La repetción de contraseña del colaborador es obligatorio")
                .check(rp -> rp.equals(contrasenia.get()),"La contraseñas no son las mismas!");

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(razonSocial,tipoOrganizacion,tipoRubro,email,wsp,telegram,domicilio,usuario,contrasenia,repContrasenia);

        if(!errors.isEmpty()){
            System.out.println(errors);
            context.redirect("/");
            return;
        }
        ColaboradorJuridico colaborador = new ColaboradorJuridico(
                razonSocial.get(),tipoOrganizacion.get(),
                tipoRubro.get()
        );
        if(email.get() != null && !email.get().trim().isEmpty()){
            Email email1 = new Email(email.get());
            colaborador.agregarMedioDeContacto(email1);
        }
        if(wsp.get() != null && !wsp.get().trim().isEmpty()){
            Whatsapp whatsapp = new Whatsapp(wsp.get());
            colaborador.agregarMedioDeContacto(whatsapp);
        }
        if(telegram.get() != null && !telegram.get().trim().isEmpty()){
            Telegram userTelegram = new Telegram(telegram.get());
            colaborador.agregarMedioDeContacto(userTelegram);
        }
        //FALTA agregar la validación de que almenos uno deba ser obligatorio, por ahora se permite la nada de los 3
        if(domicilio.get() != null && !domicilio.get().trim().isEmpty()){
            Calle calle = new Calle();
            calle.setNombre(domicilio.get());
            Ubicacion direccion = new Ubicacion();
            direccion.setCalle(calle);
            colaborador.agregarDireccion(direccion);
        }
        Usuario nuevoUsuario = new Usuario(usuario.get(), contrasenia.get());

        withTransaction(() -> {
            // Crear y guardar el colaborador jurídico
            Rol rolColaborador = repositorioRoles.buscarRolPorNombre(RoleENUM.JURIDICO);
            colaborador.setUsuario(nuevoUsuario);
            nuevoUsuario.agregarRol(rolColaborador);
            // Persistir ambas entidades
            repositorioColaboradores.guardar(colaborador);
        });

        context.json(Map.of("success", true));
    }

    public void mantenerHeladera(Context context){
        Validator<String> nombreHeladera = context.formParamAsClass("nombreHeladera", String.class)
                .check(v -> !v.isEmpty()  , "El nombre de la heladera es obligatorio");
        Validator<String> modeloHeladera = context.formParamAsClass("refrigeratorModel",String.class)
                .check(v -> !v.isEmpty()  , "El modelo de la heladera es obligatorio");
        Validator<Integer> capacidadHeladera = context.formParamAsClass("refrigeratorCapacity",Integer.class)
                .check(Objects::nonNull, "La capacidad de la heladera es obligatorio");
        Validator<String> direccionHeladera = context.formParamAsClass("donationLocation", String.class)
                .check(v -> !v.isEmpty()  , "La dirección de la heladera es obligatorio");
        Validator<String> barrioHeladera = context.formParamAsClass("donationNeighborhood", String.class)
                .check(v -> !v.isEmpty()  , "El barrio de la heladera es obligatorio")
                .check(v -> v.chars().noneMatch(Character::isDigit),"No se permite numeros en el nombre");
        Validator<String> localidadHeladera = context.formParamAsClass("donationCity", String.class)
                .check(v -> !v.isEmpty()  , "La localidad de la heladera es obligatorio")
                .check(v -> v.chars().noneMatch(Character::isDigit),"No se permite numeros en el nombre");
        Validator<String> provinciaHeladera = context.formParamAsClass("donationState", String.class)
                .check(v -> !v.isEmpty()  , "La provincia de la heladera es obligatorio")
                .check(v -> v.chars().noneMatch(Character::isDigit),"No se permite numeros en el nombre");
        Validator<Float> longitudHeladera = context.formParamAsClass("longitud",Float.class)
                .check(Objects::nonNull, "La latiud de la heladera es obligatorio");
        Validator<Float> latitudHeladera = context.formParamAsClass("latitud",Float.class)
                .check(Objects::nonNull, "La longitud de la heladera es obligatorio");

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(nombreHeladera,modeloHeladera,capacidadHeladera,direccionHeladera,barrioHeladera,localidadHeladera,provinciaHeladera);

        if(!errors.isEmpty()){
            System.out.println(errors);
            context.redirect("/dashboard/tecnicos"); // TODO -> Pantalla del form pero mencionando los errores al usuario
        }

        Optional<ModeloDeHeladera> modeloDeHeladera = repositorioModeloHeladeras.buscarModeloPorNombre(modeloHeladera.get());
        Provincia provincia = repositorioProvincias.buscarProvincia(provinciaHeladera.get());
        Localidad localidad = repositorioLocalidades.buscarLocalidad(localidadHeladera.get());
        Barrio barrio = repositorioBarrios.buscarBarrioPorNombre(barrioHeladera.get());
        Calle calle = new Calle(direccionHeladera.get());
        Ubicacion ubicacion = new Ubicacion(latitudHeladera.get(),longitudHeladera.get(),calle,provincia,localidad,barrio);
        Heladera heladera = new Heladera(modeloDeHeladera.get(),nombreHeladera.get(),ubicacion);
        heladera.setCapacidadMax(capacidadHeladera.get());
        Optional<Object> colaboradorJuridicoPosible = repositorioColaboradores.buscarPorID(ColaboradorJuridico.class,context.sessionAttribute("id_colaborador"));

        withTransaction(()->{
            ColaboradorJuridico colaboradorJuridico = (ColaboradorJuridico) colaboradorJuridicoPosible.get();
            MantenerHeladera mantenerHeladera = new MantenerHeladera(heladera,(ColaboradorJuridico) colaboradorJuridicoPosible.get());
            //Calculo de puntos a dar al colaborador por mantener/donar la heladera
            List<MantenerHeladera> mantencionesAnteriores = repositorio.buscarTodos(MantenerHeladera.class)
                    .stream()
                    .map(o -> (MantenerHeladera) o)
                    .collect(Collectors.toList());
            mantencionesAnteriores.add(mantenerHeladera);
            int puntosOtorgados = calculadoraPuntos.puntosHeladerasActivas(mantencionesAnteriores);
            mantenerHeladera.setPuntosOtorgados(puntosOtorgados);
            colaboradorJuridico.sumarPuntos(puntosOtorgados);
            mantenerHeladera.completar();
            repositorio.guardar(mantenerHeladera);
        });

        context.redirect("/estaciones");
    }
    public void ofrecerOferta(Context context){
        context.formParamMap().forEach((key, value) -> {
            System.out.println(key + ": " + value);
        });
        String nombreOferta = context.formParam("nombre_oferta");
        String descripcion = context.formParam("descripcion_producto_servicio");
        TipoDeOferta tipoDeOferta = TipoDeOferta.valueOf(context.formParam("tipo_producto_servicio"));
        CategoriaOferta categoriaOferta = CategoriaOferta.valueOf(context.formParam("categoria_producto_servicio"));
        Integer costoPuntos = Integer.parseInt(Objects.requireNonNull(context.formParam("campo_costo_puntos")));
        Optional<Object> colaboradorJuridicoPosible = repositorioColaboradores.buscarPorID(ColaboradorJuridico.class,context.sessionAttribute("id_colaborador"));
        ColaboradorJuridico ofertante = (ColaboradorJuridico) colaboradorJuridicoPosible.get();
        Oferta oferta = new Oferta(nombreOferta,descripcion,tipoDeOferta,categoriaOferta,ofertante,costoPuntos);
        oferta.completar();

        withTransaction(()->{
           repositorio.guardar(oferta);
        });
        context.redirect("/donaciones");
    }

    public void misEstaciones( Context context) {
        Map<String, Object> model = new HashMap<>();

        List<Heladera> heladeras = repositorioMantenciones
                .buscarPorColaboradorId(context.sessionAttribute("id_colaborador"))
                .stream()
                .map(MantenerHeladera::getHeladera)
                .collect(Collectors.toList());

        List<HeladeraDTO> heladerasDTO = heladeras.stream()
                .map(HeladeraMapper::toDTO)
                .collect(Collectors.toList());


        model.put("heladeras", heladerasDTO);

        context.render("home/estaciones/misEstaciones.hbs", model);
    }
}
