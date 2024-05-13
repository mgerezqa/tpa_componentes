## Pendientes / A mejorar / A definir

- Pendiente
  - Los campos de los formularios deben admitir respuesta múltiple.
  - Logica colaborador jurídico.
- A definir
  - Una persona vulnerable puede convertirse en colaborador ? Pensarlo.
  - Las propiedades de los colaboradores (nombre,apellido,medio de contacto)y su interacción con los formularios.
  - En las clases Colaboradores definir un state machine para los cambios de estado del perfil.
  Es decir un nuevo colaborador se inicia en estado _"Nuevo"_, cuando completa el formulario pasa a _"EnVerificacion"_, luego a _"Verificado"_, y por ultimo a _"Baja"_.

- A mejorar
  - Ver reduncancia en el registro de los colaboradores juridicos y no juridicos.

- ### Cambios
  - Todos los tipos DataLocalTime (Fecha y Hora) se sustituyeron por LocalDate (Fecha).
  - Agregué constructores en los Object Value, Calle y Ubicación.
  - Los setters del lombok me los marca en rojo en el método setearSensorDeTemperalura().
  
