package controladores;
import config.ServiceLocator;
import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telegram;
import domain.contacto.Whatsapp;
import domain.donaciones.*;
import domain.formulario.documentos.Documento;
import domain.formulario.documentos.TipoDocumento;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.persona.Persona;
import domain.persona.PersonaVulnerable;
import domain.puntos.CalculadoraPuntos;
import domain.tarjeta.TarjetaVulnerable;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.Rol;
import domain.usuarios.RoleENUM;
import domain.usuarios.Usuario;
import dtos.requests.ColaboradorFisicoInputDTO;
import dtos.responses.ColaboradorFisicoOutputDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.HttpStatus;
import io.javalin.validation.NullableValidator;
import io.javalin.validation.Validation;
import io.javalin.validation.ValidationError;
import org.jetbrains.annotations.NotNull;
import repositorios.Repositorio;
import repositorios.repositoriosBDD.*;
import utils.Broker.ServiceBroker;
import utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.validation.Validator;
import java.time.LocalDate;
import java.util.*;
import java.util.Objects;

public class ControladorColaboradorFisico implements ICrudViewsHandler, WithSimplePersistenceUnit {

    private RepositorioColaboradores repositorioColaboradores;
    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioRoles repositorioRoles;
    private Repositorio repositorio;
    private RepositorioRegistrosVulnerables repositorioRegistrosVulnerables;
    private RepositorioDistribuciones repositorioDistribuciones;
    private RepositorioViandas repositorioViandas;

    public ControladorColaboradorFisico(RepositorioColaboradores repositorioColaboradores, RepositorioUsuarios repositorioUsuarios, RepositorioRoles repositorioRoles,Repositorio repositorio,RepositorioRegistrosVulnerables repositorioRegistrosVulnerables,RepositorioDistribuciones repositorioDistribuciones,RepositorioViandas repositorioViandas) {
        this.repositorioColaboradores = repositorioColaboradores;
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioRoles = repositorioRoles;
        this.repositorio = repositorio;
        this.repositorioRegistrosVulnerables = repositorioRegistrosVulnerables;
        this.repositorioDistribuciones = repositorioDistribuciones;
        this.repositorioViandas = repositorioViandas;
    }

    @Override // funciona correctamente
    public void index(Context context){
        List<ColaboradorFisico> colaboradores = repositorioColaboradores.obtenerColaboradoresFisicos();
        List<ColaboradorFisicoOutputDTO> colaboradoresFisicosOutputDTO = new ArrayList<>();

        for(ColaboradorFisico colaborador : colaboradores){

            ColaboradorFisicoOutputDTO colaboradorFisicoOutputDTO = new ColaboradorFisicoOutputDTO();

            colaboradorFisicoOutputDTO.setNombre(colaborador.getNombre());
            colaboradorFisicoOutputDTO.setApellido(colaborador.getApellido());
            colaboradorFisicoOutputDTO.setId(colaborador.getId());
            colaboradorFisicoOutputDTO.setActivo(colaborador.activo);
            colaboradorFisicoOutputDTO.setPuntosAcumulados(colaborador.puntosAcumulados);
            colaboradorFisicoOutputDTO.setEmail(colaborador.email());

            colaboradoresFisicosOutputDTO.add(colaboradorFisicoOutputDTO);
        }


        Map<String, Object> model = new HashMap<>();
        model.put("colaboradoresFisicos", colaboradoresFisicosOutputDTO);
        context.render("dashboard/fisicos.hbs", model);
    }

    @Override // funciona correctamente
    public void create(Context context) {
        Validator<String> nombre = context.formParamAsClass("nombre", String.class)
                .check(Objects::nonNull, "El nombre del colaborador es obligatorio");
        Validator<String> apellido = context.formParamAsClass("apellido", String.class)
                .check(Objects::nonNull, "El apellido del colaborador es obligatorio");
        Validator<String> email = context.formParamAsClass("email", String.class)
                .check(Objects::nonNull, "El email del colaborador es obligatorio");

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(nombre,apellido,email);

        if(!errors.isEmpty()){
            System.out.println(errors);
            context.redirect("/dashboard/fisicos");
            return;
        }

        ColaboradorFisicoInputDTO colaboradorFisicoInputDTO = new ColaboradorFisicoInputDTO();
        colaboradorFisicoInputDTO.setApellido(context.formParam("apellido"));
        colaboradorFisicoInputDTO.setNombre(context.formParam("nombre"));
        colaboradorFisicoInputDTO.setActivo(Boolean.valueOf(context.formParam("activo")));
        colaboradorFisicoInputDTO.setEmail(context.formParam("email"));

        ColaboradorFisico colaboradorFisico = new ColaboradorFisico();

        colaboradorFisico.setNombre(colaboradorFisicoInputDTO.getNombre());
        colaboradorFisico.setApellido(colaboradorFisicoInputDTO.getApellido());
        colaboradorFisico.setId(colaboradorFisicoInputDTO.getId());
        colaboradorFisico.setActivo(colaboradorFisicoInputDTO.getActivo());

        Email email1 = new Email(colaboradorFisicoInputDTO.getEmail());
        colaboradorFisico.agregarMedioDeContacto(email1);

        colaboradorFisico.puntosAcumulados = 0;

        withTransaction(()->{
            repositorioColaboradores.guardar(colaboradorFisico);
        });

        context.redirect("/dashboard/fisicos");
    }

    @Override //
    public void edit(Context context) {
        Optional<Object> posibleColaborador =
                this.repositorioColaboradores.buscarPorID(ColaboradorFisico.class, Long.valueOf(context.pathParam("id")));

        if(posibleColaborador.isEmpty()){
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        ColaboradorFisico colaboradorFisico = (ColaboradorFisico) posibleColaborador.get();

        ColaboradorFisicoInputDTO colaboradorFisicoInputDTO = new ColaboradorFisicoInputDTO();
        colaboradorFisicoInputDTO.setId(colaboradorFisico.getId());
        colaboradorFisicoInputDTO.setNombre(colaboradorFisico.getNombre());
        colaboradorFisicoInputDTO.setApellido(colaboradorFisico.getApellido());
        colaboradorFisicoInputDTO.setActivo(colaboradorFisico.getActivo());
        colaboradorFisicoInputDTO.setEmail(colaboradorFisico.email());

        Map<String, Object> model = new HashMap<>();
        model.put("colaboradoresFisicos", colaboradorFisicoInputDTO);
        model.put("action","/dashboard/fisicos/" + colaboradorFisicoInputDTO.getId()+ "/edit");

        context.render("dashboard/forms/fisico.hbs", model);
    }

    @Override // hacer
    public void update(Context context) {
        Long colaboradorId = Long.valueOf(context.pathParam("id"));

        // Validaciones
        Validator<String> nombre = context.formParamAsClass("nombre", String.class)
                .check(Objects::nonNull, "El nombre del colaborador es obligatorio");
        Validator<String> apellido = context.formParamAsClass("apellido", String.class)
                .check(Objects::nonNull, "El apellido del colaborador es obligatorio");
        Validator<String> email = context.formParamAsClass("email", String.class)
                .check(Objects::nonNull, "El email del colaborador es obligatorio");

        boolean estado = context.formParam("activo") != null;

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(nombre, apellido, email);

        if (!errors.isEmpty()) {
            context.sessionAttribute("errors", errors);

            // Renderizar el modal nuevamente con los errores en lugar de redirigir
            context.render("dashboard/forms/fisico.hbs");
            return;
        }

        // Buscar colaborador y actualizar
        Optional<Object> posibleColaborador =
                this.repositorioColaboradores.buscarPorID(ColaboradorFisico.class, colaboradorId);

        if (posibleColaborador.isEmpty()) {
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        ColaboradorFisico colaboradorFisico = (ColaboradorFisico) posibleColaborador.get();
        colaboradorFisico.setNombre(nombre.get());
        colaboradorFisico.setApellido(apellido.get());

        colaboradorFisico.setActivo(estado);
        Set<MedioDeContacto> medioDeContactos = colaboradorFisico.getMediosDeContacto();
        Optional<Email> medioDeContacto = medioDeContactos.stream()
                .filter(v -> v instanceof Email) // Verifica si es instancia de Email
                .map(v -> (Email) v) // Cast a Email
                .findFirst();
        if (medioDeContacto.isPresent()){
            medioDeContacto.get().setEmail(email.get());
        }

        // Actualizar en transacción
        withTransaction(() -> {
            repositorioColaboradores.actualizar(colaboradorFisico);
        });

        // Redireccionar después de la actualización
        context.redirect("/dashboard/fisicos");
    }

    @Override // hacer
    public void delete(Context context) {
        Optional<Object> posibleColaborador =
                this.repositorioColaboradores.buscarPorID(ColaboradorFisico.class, Long.valueOf(context.pathParam("id")));

        if (posibleColaborador.isEmpty()) {
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        ColaboradorFisico colaboradorFisico = (ColaboradorFisico) posibleColaborador.get();

        withTransaction(() -> {
            repositorioColaboradores.eliminar(colaboradorFisico);
            System.out.println("Colaborador eliminado: " + colaboradorFisico.getId());
        });

        context.redirect("/dashboard/fisicos");

    }

    @Override
    public void remove(Context context) {
        Optional<Object> posibleFisico = repositorioColaboradores.buscarPorID(ColaboradorFisico.class, Long.valueOf(context.pathParam("id")));

        if(posibleFisico.isPresent()){
            withTransaction(()->{
                ColaboradorFisico colaboradorFisico = (ColaboradorFisico) posibleFisico.get();
                colaboradorFisico.setActivo(false);
                repositorioColaboradores.actualizar(colaboradorFisico);
            });

            context.redirect("/dashboard/fisicos");
        }else{
            context.status(HttpStatus.NOT_FOUND);
        }
    }

    @Override // . . .
    public void save(Context context){
        withTransaction(()->{
            //Instancias de entidades relacionadas al endpoint
            String nombre = context.formParam("nombre");
            String apellido = context.formParam("apellido");
            ColaboradorFisico colaborador = new ColaboradorFisico(nombre,apellido);
            //Guardado de datos
            repositorioColaboradores.guardar(colaborador);
            context.redirect("/"); //Sugerencia -> Redirección a una pantalla de exito del registro.
        });
    }

    @Override // . . .
    public void show(Context context) {

    }
    public void signup(Context ctx){
        Validator<String> nombre = ctx.formParamAsClass("nombre", String.class)
                .check(v -> !v.isEmpty()  , "El nombre del colaborador es obligatorio")
                .check(v -> v.chars().noneMatch(Character::isDigit),"No se permite numeros en el nombre");
        Validator<String> apellido = ctx.formParamAsClass("apellido", String.class)
                .check(v -> !v.isEmpty()  , "El apellido del colaborador es obligatorio")
                .check(v -> v.chars().noneMatch(Character::isDigit),"No se permite numeros en el apellido");
        //Opcionales, pueden llegar null, al menos uno
        NullableValidator<String> email = ctx.formParamAsClass("user_email", String.class)
                .allowNullable();
        NullableValidator<String> wsp = ctx.formParamAsClass("nro_whatsapp", String.class)
                .allowNullable();
        NullableValidator<String> telegram = ctx.formParamAsClass("user_telegram", String.class)
                .allowNullable();

        //Opcionales (Opcional)
        NullableValidator<String> domicilio = ctx.formParamAsClass("domicilio", String.class)
                .allowNullable();

        NullableValidator<String> nacimiento = ctx.formParamAsClass("fechaNacimiento", String.class)
                .allowNullable();

        //Datos para el usuario
        Validator<String> usuario = ctx.formParamAsClass("emailUsuario", String.class)
                .check(v -> !v.isEmpty()  , "El nombre de usuario o email del colaborador es obligatorio");
        //Falta agregar que no se pueda crear 2 usuarios con el mismo username.
        Validator<String> contrasenia = ctx.formParamAsClass("password", String.class)
                .check(v -> !v.isEmpty()  , "La contraseña del colaborador es obligatorio");

        Validator<String> repContrasenia = ctx.formParamAsClass("repeatPassword", String.class)
                .check(v -> !v.isEmpty()  , "La repetción de contraseña del colaborador es obligatorio")
                .check(rp -> rp.equals(contrasenia.get()),"La contraseñas no son las mismas!");

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(nombre,apellido,email,wsp,telegram,domicilio,nacimiento,usuario,contrasenia,repContrasenia);

        if(!errors.isEmpty()){
            System.out.println(errors);
            ctx.redirect("/");
            return;
        }

        //Creación de las instacias colaborador fisico y usuario correspondiente
        ColaboradorFisico colaboradorFisico = new ColaboradorFisico(nombre.get(),apellido.get());

        if(email.get() != null && !email.get().trim().isEmpty()){
            Email email1 = new Email(email.get());
            colaboradorFisico.agregarMedioDeContacto(email1);
        }
        if(wsp.get() != null && !wsp.get().trim().isEmpty()){
            Whatsapp whatsapp = new Whatsapp(wsp.get());
            colaboradorFisico.agregarMedioDeContacto(whatsapp);
        }
        if(telegram.get() != null && !telegram.get().trim().isEmpty()){
            Telegram userTelegram = new Telegram(telegram.get());
            colaboradorFisico.agregarMedioDeContacto(userTelegram);
        }
        //FALTA agregar la validación de que almenos uno deba ser obligatorio, por ahora se permite la nada de los 3
        if(domicilio.get() != null && !domicilio.get().trim().isEmpty()){
            //Falta agregar a la interface que se particione el campo en 2 para separar el nombre y el numero
            Calle calle = new Calle();
            calle.setNombre(domicilio.get());
            Ubicacion ubicacion = new Ubicacion();
            ubicacion.setCalle(calle);
            colaboradorFisico.agregarDireccion(ubicacion);
        }
        if(nacimiento.get() != null && !nacimiento.get().isEmpty()){
            LocalDate fechaNacimiento = LocalDate.parse(nacimiento.get());
            colaboradorFisico.setNacimiento(fechaNacimiento);
        }
        Usuario nuevoUsuario = new Usuario(usuario.get(), contrasenia.get());
        colaboradorFisico.setUsuario(nuevoUsuario);

        withTransaction(()->{
            Rol rolFisico = repositorioRoles.buscarRolPorNombre(RoleENUM.FISICO);
            nuevoUsuario.agregarRol(rolFisico);
            repositorioColaboradores.guardar(colaboradorFisico);
        });
        ctx.redirect("/");
    }
    public void registroPersonaVulnerable(Context context) {
        String nombreBeneficiario = context.formParam("campo_nombre_pv");
        String apellidoBeneficiario = context.formParam("campo_apellido_pv");
        String direccionBeneficiario = context.formParam("campo_direccion_pv");
        TipoDocumento tipoDocumentoBeneficiario = TipoDocumento.valueOf(context.formParam("campo_tipo_documento_pv"));
        String numeroDocumentoBeneficiario = context.formParam("campo_nro_documento_pv");
        LocalDate fechaNacimientoBeneficiario = LocalDate.parse(Objects.requireNonNull(context.formParam("campo_nacimiento_pv")));


        PersonaVulnerable personaVulnerable = new PersonaVulnerable(nombreBeneficiario,fechaNacimientoBeneficiario);
        personaVulnerable.setApellido(apellidoBeneficiario);

        Documento doc = new Documento();
        doc.setTipo(tipoDocumentoBeneficiario);
        assert numeroDocumentoBeneficiario != null;
        doc.setNumeroDeDocumento(numeroDocumentoBeneficiario);
        assert direccionBeneficiario != null;
        Calle direccion = new Calle();

        direccion.setNombre(direccionBeneficiario);
        personaVulnerable.setDocumento(doc);

        int index = 1;
        // Continuar mientras haya un nombre de hijo en el formulario
        String nombreHijo;
        while ((nombreHijo = context.formParam("nombreHijo" + index)) != null) {
            String apellidoHijo = context.formParam("apellidoHijo" + index);
            LocalDate fechaNacimientoHijo = LocalDate.parse(Objects.requireNonNull(context.formParam("fechaNacimientoHijo" + index)));

            // Crear un nuevo objeto hijo y establecer sus atributos
            Persona hijo = new Persona();
            hijo.setNombre(nombreHijo);
            hijo.setApellido(apellidoHijo);
            hijo.setFechaNacimiento(fechaNacimientoHijo);

            // Agregar el hijo a la lista
            personaVulnerable.agregarMenorACargo(hijo);
            index++;
        }
        Optional<Object> colaborador = repositorioColaboradores.buscarPorID(ColaboradorFisico.class,context.sessionAttribute("id_colaborador"));
        TarjetaVulnerable tarjeta= new TarjetaVulnerable();
        tarjeta.setVulnerable(personaVulnerable);
        RegistroDePersonaVulnerable registroDePersonaVulnerable = new RegistroDePersonaVulnerable((ColaboradorFisico) colaborador.get(),tarjeta,personaVulnerable);
        //Cuantas tarjetas repartió.
        withTransaction(() -> {
            repositorio.guardar(registroDePersonaVulnerable);
        });
        List<RegistroDePersonaVulnerable> tarjetasRepartidas = repositorioRegistrosVulnerables.buscarPorColaboradorId(((ColaboradorFisico) colaborador.get()).getId());

        int cantidadTarjetasRepartidas = tarjetasRepartidas.size();
        int puntos = ServiceLocator.instanceOf(CalculadoraPuntos.class).puntosTarjetasRepatidas(cantidadTarjetasRepartidas);
        ((ColaboradorFisico) colaborador.get()).sumarPuntos(puntos);
        registroDePersonaVulnerable.setPuntosOtorgados(puntos);
        withTransaction(()->{
            repositorio.actualizar(colaborador.get());
            repositorio.actualizar(registroDePersonaVulnerable);
        });
        context.redirect("/donaciones");
    }
    public void donacionDinero(Context context){
        Integer monto = Integer.parseInt(Objects.requireNonNull(context.formParam("campo_monto_dinero")));
        FrecuenciaDeDonacion frecuenciaDeDonacion = FrecuenciaDeDonacion.valueOf(context.formParam("campo_frecuencia_dinero"));
        Optional<Object> colaborador = repositorioColaboradores.buscarPorID(ColaboradorFisico.class,context.sessionAttribute("id_colaborador"));

        Dinero donacion = new Dinero(monto,frecuenciaDeDonacion,(ColaboradorFisico) colaborador.get());
        var puntos = ServiceLocator.instanceOf(CalculadoraPuntos.class).puntosPesosDonados(donacion);

        ((ColaboradorFisico) colaborador.get()).sumarPuntos(puntos);
        donacion.setPuntosOtorgados(puntos);
        withTransaction(()->{
            repositorio.guardar(donacion);
        });
        context.redirect("/donaciones");
    }
    public void distrubuirViandas(Context context){
        Long origenReparto = Long.valueOf(Objects.requireNonNull(context.formParam("campo_origen_reparto")));
        Long destinoReparto = Long.valueOf(Objects.requireNonNull(context.formParam("campo_destino_reparto")));
        Integer cantidadReparto = Integer.parseInt(Objects.requireNonNull(context.formParam("campo_cantidad_reparto")));
        Motivo motivoReparto = Motivo.valueOf(context.formParam("campo_motivo_reparto"));

        Optional<Object> heladeraOrigen = repositorio.buscarPorID(Heladera.class,origenReparto);
        Optional<Object> heladeraDestino = repositorio.buscarPorID(Heladera.class,destinoReparto);
        Optional<Object> colaborador = repositorio.buscarPorID(ColaboradorFisico.class,context.sessionAttribute("id_colaborador"));

        Distribuir nuevaDistribucion = new Distribuir((Heladera) heladeraOrigen.get(),(Heladera) heladeraDestino.get(),cantidadReparto,(ColaboradorFisico) colaborador.get());
        nuevaDistribucion.setMotivo(motivoReparto);

        int viandasDistribuidas = repositorioDistribuciones
                .buscarPorColaboradorId(((ColaboradorFisico) colaborador.get()).getId())
                .stream()
                .mapToInt(Distribuir::getCantidad) // Mapea cada elemento a su cantidad como un int
                .sum();

        int puntos = ServiceLocator.instanceOf(CalculadoraPuntos.class).puntosViandasDistribuidas(viandasDistribuidas + nuevaDistribucion.getCantidad());
        nuevaDistribucion.setPuntosOtorgados(puntos);
        ((ColaboradorFisico) colaborador.get()).sumarPuntos(puntos);
        withTransaction(()->{
            repositorio.guardar(nuevaDistribucion);
        });
        context.redirect("/donaciones");
    }

    public void donarViandas(Context context) {
        String descripcion = context.formParam("descripcion");
        LocalDate fechaVencimiento = LocalDate.parse(Objects.requireNonNull(context.formParam("campo_vencimiento_vianda")));
        Long calorias = Long.parseLong(Objects.requireNonNull(context.formParam("campo_calorias_vianda")));
        Long peso = Long.parseLong(Objects.requireNonNull(context.formParam("campo_peso_vianda")));
        Optional<Object> colaborador = repositorioColaboradores.buscarPorID(ColaboradorFisico.class,
                context.sessionAttribute("id_colaborador"));

        ColaboradorFisico colaboradorFisico = (ColaboradorFisico) colaborador.get();

        Vianda donacion = new Vianda(descripcion, fechaVencimiento,
                calorias, peso, colaboradorFisico);
        if(!Objects.equals(context.formParam("heladera"), "") || context.formParam("heladera") !=null){
            withTransaction(()->{
                repositorio.guardar(donacion);
            });
            Long idHeladera = Long.parseLong(context.formParam("heladera"));
            Optional<Object> heladera = repositorio.buscarPorID(Heladera.class,idHeladera);
            ServiceBroker serviceBroker = ServiceLocator.instanceOf(ServiceBroker.class);
            String topic = "dds2024/heladera/autorizacion";
            String msg = String.format("{'idH': %d,'idD': %d}", idHeladera,donacion.getId());
            serviceBroker.publishMessage(topic, msg);
            donacion.setHeladeraActual((Heladera) heladera.get());
        }


        //Esta logica de otorgale puntos solo se deberia hacer cuando realmente abrio la heladera
        //int viandasDonadas = repositorioViandas
        //        .buscarPorColaboradorId(colaboradorFisico.getId())
        //        .size();
//
        //int puntos = ServiceLocator.instanceOf(CalculadoraPuntos.class)
        //        .puntosViandasDonadas(viandasDonadas + 1);
//
        //donacion.setPuntosOtorgados(puntos);
        //colaboradorFisico.sumarPuntos(puntos);

        //Mandar la autorización para solicitud de apertura


        withTransaction(() -> {
            repositorio.guardar(donacion);
        });
        
        context.redirect("/donaciones");
    }
    public void recomendarComunidades(Context context) {
        Float latitud = Float.valueOf(Objects.requireNonNull(context.formParam("latitud")));
        Float longitud = Float.valueOf(Objects.requireNonNull(context.formParam("longitud")));

    }
}
