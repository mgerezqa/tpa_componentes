package controladores;
import domain.formulario.documentos.Documento;
import domain.formulario.documentos.TipoDocumento;
import domain.persona.Persona;
import domain.persona.PersonaVulnerable;
import domain.usuarios.ColaboradorJuridico;
import domain.usuarios.Rubro;
import domain.usuarios.TipoRazonSocial;
import domain.usuarios.Usuario;
import dtos.ColaboradorJuridicoDTO;
import dtos.PersonaVulnerableDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.validation.Validation;
import io.javalin.validation.ValidationError;
import io.javalin.validation.Validator;
import lombok.AllArgsConstructor;
import repositorios.repositoriosBDD.RepositorioVulnerables;
import utils.ICrudViewsHandler;

import java.time.LocalDate;
import java.util.*;

public class ControladorBeneficiarios implements ICrudViewsHandler, WithSimplePersistenceUnit {

    private RepositorioVulnerables repositorioVulnerables;

    public ControladorBeneficiarios(RepositorioVulnerables repositorioVulnerables) {
        this.repositorioVulnerables = repositorioVulnerables;
    }

    @Override // LISTO !!
    public void index(Context context) {
        List<PersonaVulnerable> personasVulnerables = repositorioVulnerables.obtenerPersonasVulnerables();
        List<PersonaVulnerableDTO> personasVulnerablesDTO = new ArrayList<>();

        for (PersonaVulnerable personaVulnerable : personasVulnerables){
            try {
                PersonaVulnerableDTO personaVulnerableDTO = new PersonaVulnerableDTO();

                personaVulnerableDTO.setId(personaVulnerable.getId());
                personaVulnerableDTO.setNombre(personaVulnerable.getNombre());
                personaVulnerableDTO.setApellido(personaVulnerable.getApellido());
                personaVulnerableDTO.setActivo(personaVulnerable.getActivo());
                personaVulnerableDTO.setCantidadHijos(personaVulnerable.cantidadDeMenoresACargo());
                personaVulnerableDTO.setFechaNacimiento(personaVulnerable.getFechaNacimiento());
                personaVulnerableDTO.setFechaRegistro(personaVulnerable.getFechaRegitrado());
                personaVulnerableDTO.setNumeroDocumento(personaVulnerable.getDocumento().getNumeroDeDocumento());
                personaVulnerableDTO.setTipoDocumento(String.valueOf(personaVulnerable.getDocumento().getTipo()));

                personasVulnerablesDTO.add(personaVulnerableDTO);

            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }

        Map<String,List<PersonaVulnerableDTO>> model = new HashMap<>();
        model.put("beneficiarios",personasVulnerablesDTO);
        context.render("/dashboard/beneficiarios.hbs", model);
    }

    @Override // LISTO !!
    public void create(Context context) {
        // Validaciones existentes
        Validator<String> nombreBeneficiario = context.formParamAsClass("nombreBeneficiario", String.class)
                .check(Objects::nonNull, "El nombre es obligatorio");
        Validator<String> apellidoBeneficiario = context.formParamAsClass("apellidoBeneficiario", String.class)
                .check(Objects::nonNull, "El apellido es obligatorio");
        Validator<String> tipoDocumentoBeneficiario = context.formParamAsClass("tipoDocumentoBeneficiario", String.class)
                .check(Objects::nonNull, "El tipo de documento es obligatorio");
        Validator<String> numeroDocumentoBeneficiario = context.formParamAsClass("numeroDocumentoBeneficiario", String.class)
                .check(Objects::nonNull, "El número de documento es obligatorio");
        Validator<String> fechaNacimientoBeneficiario = context.formParamAsClass("fechaNacimientoBeneficiario", String.class)
                .check(Objects::nonNull, "La fecha de nacimiento es obligatoria");
        Validator<String> fechaRegistroBeneficiario = context.formParamAsClass("fechaRegistroBeneficiario", String.class)
                .check(Objects::nonNull, "La fecha de registro es obligatoria");

        boolean activo = context.formParam("estadoBeneficiario") != null;

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(nombreBeneficiario, apellidoBeneficiario,
                tipoDocumentoBeneficiario, numeroDocumentoBeneficiario, fechaNacimientoBeneficiario, fechaRegistroBeneficiario);

        if (!errors.isEmpty()) {
            System.out.println(errors);
            context.redirect("/dashboard/beneficiarios");
            return;
        }

        PersonaVulnerableDTO personaVulnerableDTO = new PersonaVulnerableDTO();
        personaVulnerableDTO.setActivo(activo);
        personaVulnerableDTO.setNombre(nombreBeneficiario.get());
        personaVulnerableDTO.setApellido(apellidoBeneficiario.get());
        personaVulnerableDTO.setTipoDocumento(tipoDocumentoBeneficiario.get());
        personaVulnerableDTO.setNumeroDocumento(numeroDocumentoBeneficiario.get());
        personaVulnerableDTO.setFechaNacimiento(LocalDate.parse(fechaNacimientoBeneficiario.get()));
        personaVulnerableDTO.setFechaRegistro(LocalDate.parse(fechaRegistroBeneficiario.get()));

        // Crear PersonaVulnerable y asignar propiedades
        PersonaVulnerable personaVulnerable = new PersonaVulnerable();
        personaVulnerable.setActivo(personaVulnerableDTO.getActivo());
        personaVulnerable.setNombre(personaVulnerableDTO.getNombre());
        personaVulnerable.setApellido(personaVulnerableDTO.getApellido());

        Documento doc = new Documento();
        doc.setTipo(TipoDocumento.valueOf(personaVulnerableDTO.getTipoDocumento()));
        doc.setNumeroDeDocumento(personaVulnerableDTO.getNumeroDocumento());

        personaVulnerable.setDocumento(doc);
        personaVulnerable.setFechaNacimiento(personaVulnerableDTO.getFechaNacimiento());
        personaVulnerable.setFechaRegitrado(personaVulnerableDTO.getFechaRegistro());

        List<Persona> menoresACargo = new ArrayList<>();
        personaVulnerable.setMenoresACargo(menoresACargo);

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

        withTransaction(() -> {
            repositorioVulnerables.guardar(personaVulnerable);
        });
        context.redirect("/dashboard/beneficiarios");
    }

    @Override // LISTO !!
    public void edit(Context context) {
        Optional<Object> posibleVulnerable = repositorioVulnerables.buscarPorID(PersonaVulnerable.class, Long.valueOf(context.pathParam("id")));

        if(posibleVulnerable.isEmpty()){
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        PersonaVulnerable personaVulnerable = (PersonaVulnerable) posibleVulnerable.get();
        PersonaVulnerableDTO personaVulnerableDTO = new PersonaVulnerableDTO();

        personaVulnerableDTO.setId(personaVulnerable.getId());
        personaVulnerableDTO.setActivo(personaVulnerable.getActivo());
        personaVulnerableDTO.setNombre(personaVulnerable.getNombre());
        personaVulnerableDTO.setApellido(personaVulnerable.getApellido());
        personaVulnerableDTO.setFechaNacimiento(personaVulnerable.getFechaNacimiento());
        personaVulnerableDTO.setFechaRegistro(personaVulnerable.getFechaRegitrado());
        personaVulnerableDTO.setNumeroDocumento(personaVulnerable.getDocumento().getNumeroDeDocumento());
        personaVulnerableDTO.setTipoDocumento(String.valueOf(personaVulnerable.getDocumento().getTipo()));
        personaVulnerableDTO.setCantidadHijos(personaVulnerable.cantidadDeMenoresACargo());

        Map<String, Object> model = new HashMap<>();
        model.put("beneficiarios",personaVulnerableDTO);
        model.put("action","/dashboard/beneficiarios/"+ personaVulnerableDTO.getId()+ "/edit");

        context.render("/dashboard/forms/beneficiario.hbs", model);

    }

    @Override // LISTO !!
    public void update(Context context) {
        // Validaciones existentes
        Validator<String> nombreBeneficiario = context.formParamAsClass("nombreBeneficiario", String.class)
                .check(Objects::nonNull, "El nombre es obligatorio");
        Validator<String> apellidoBeneficiario = context.formParamAsClass("apellidoBeneficiario", String.class)
                .check(Objects::nonNull, "El apellido es obligatorio");
        Validator<String> tipoDocumentoBeneficiario = context.formParamAsClass("tipoDocumentoBeneficiario", String.class)
                .check(Objects::nonNull, "El tipo de documento es obligatorio");
        Validator<String> numeroDocumentoBeneficiario = context.formParamAsClass("numeroDocumentoBeneficiario", String.class)
                .check(Objects::nonNull, "El número de documento es obligatorio");
        Validator<String> fechaNacimientoBeneficiario = context.formParamAsClass("fechaNacimientoBeneficiario", String.class)
                .check(Objects::nonNull, "La fecha de nacimiento es obligatoria");
        Validator<String> fechaRegistroBeneficiario = context.formParamAsClass("fechaRegistroBeneficiario", String.class)
                .check(Objects::nonNull, "La fecha de registro es obligatoria");

        boolean activo = context.formParam("estadoBeneficiario") != null;

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(nombreBeneficiario, apellidoBeneficiario,
                tipoDocumentoBeneficiario, numeroDocumentoBeneficiario, fechaNacimientoBeneficiario, fechaRegistroBeneficiario);

        if (!errors.isEmpty()) {
            System.out.println(errors);
            context.redirect("/dashboard/beneficiarios");
            return;
        }

        Optional<Object> posibleVulnerable =
                this.repositorioVulnerables.buscarPorID(PersonaVulnerable.class, Long.valueOf(context.pathParam("id")));

        if (posibleVulnerable.isEmpty()) {
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        PersonaVulnerable personaVulnerable = (PersonaVulnerable) posibleVulnerable.get();

        personaVulnerable.setActivo(activo);
        personaVulnerable.setNombre(nombreBeneficiario.get());
        personaVulnerable.setApellido(apellidoBeneficiario.get());

        Documento doc = new Documento();
        doc.setNumeroDeDocumento(numeroDocumentoBeneficiario.get());
        doc.setTipo(TipoDocumento.valueOf(tipoDocumentoBeneficiario.get()));
        personaVulnerable.setDocumento(doc);

        personaVulnerable.setFechaNacimiento(LocalDate.parse(fechaNacimientoBeneficiario.get()));
        personaVulnerable.setFechaRegitrado(LocalDate.parse(fechaRegistroBeneficiario.get()));

        // Manejo de los hijos
        List<String> nombresHijos = context.formParams("nombreHijo[]");
        List<String> apellidosHijos = context.formParams("apellidoHijo[]");
        List<String> fechasNacimientoHijos = context.formParams("fechaNacimientoHijo[]");

        for (int i = 0; i < nombresHijos.size(); i++) {
            Persona hijo = new Persona();
            hijo.setNombre(nombresHijos.get(i));
            hijo.setApellido(apellidosHijos.get(i));
            hijo.setFechaNacimiento(LocalDate.parse(fechasNacimientoHijos.get(i)));
            personaVulnerable.agregarMenorACargo(hijo);
        }


        withTransaction(() -> {
            repositorioVulnerables.actualizar(personaVulnerable);
        });

        context.redirect("/dashboard/beneficiarios");
    }

    @Override // LISTO !!
    public void save(Context context) {
        withTransaction(()->{
            String nombreBeneficiario = context.formParam("nombreBeneficiario");
            String apellidoBeneficiario = context.formParam("apellidoBeneficiario");
            String tipoDocumentoBeneficiario = context.formParam("tipoDocumentoBeneficiario");
            String numeroDocumentoBeneficiario = context.formParam("numeroDocumentoBeneficiario");
            String fechaNacimientoBeneficiario = context.formParam("fechaNacimientoBeneficiario");
            String fechaRegistroBeneficiario = context.formParam("fechaRegistroBeneficiario");
            String estadoBeneficiario = context.formParam("estadoBeneficiario");

            PersonaVulnerable personaVulnerable = new PersonaVulnerable();
            personaVulnerable.setNombre(nombreBeneficiario);
            personaVulnerable.setApellido(apellidoBeneficiario);
            personaVulnerable.setActivo(Boolean.valueOf(estadoBeneficiario));

            Documento doc = new Documento();
            doc.setTipo(TipoDocumento.valueOf(tipoDocumentoBeneficiario));
            doc.setNumeroDeDocumento(numeroDocumentoBeneficiario);

            personaVulnerable.setDocumento(doc);
            personaVulnerable.setFechaRegitrado(LocalDate.parse(fechaRegistroBeneficiario));
            personaVulnerable.setFechaNacimiento(LocalDate.parse(fechaNacimientoBeneficiario));

            //Guardado de datos
            repositorioVulnerables.guardar(personaVulnerable);

            context.redirect("/");
        });
    }

    @Override // LISTO !!
    public void delete(Context context) {
        Optional<Object> posibleVulnerable =
                this.repositorioVulnerables.buscarPorID(PersonaVulnerable.class, Long.valueOf(context.pathParam("id")));

        if (posibleVulnerable.isEmpty()) {
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        PersonaVulnerable personaVulnerable = (PersonaVulnerable) posibleVulnerable.get();

        withTransaction(() -> {
            repositorioVulnerables.eliminarTodosLosMenoresACargo(personaVulnerable);
            repositorioVulnerables.eliminar(personaVulnerable);
            System.out.println("Colaborador eliminado: " + personaVulnerable.getId());
        });

        context.redirect("/dashboard/beneficiarios");

    }

    @Override
    public void remove(Context context) {
        Optional<Object> posibleBeneficiario = repositorioVulnerables.buscarPorID(PersonaVulnerable.class, Long.valueOf(context.pathParam("id")));

        if(posibleBeneficiario.isPresent()){
            withTransaction(()->{
                PersonaVulnerable personaVulnerable = (PersonaVulnerable) posibleBeneficiario.get();
                personaVulnerable.setActivo(false);
                repositorioVulnerables.actualizar(personaVulnerable);
            });

            context.redirect("/dashboard/beneficiarios");
        }else{
            context.status(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void show(Context context) {

    }
}
