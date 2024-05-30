Cambios:

Colaborador:
- activo pasa a privado, no es necesario conocer el atributo si seteamos su valor con metodos y un getter basta para conocerlo
- ColaboradorJuridico se le agregan el tipoDNI y numeroDNI por ser necesarios para la carga masiva de colaboradores (TODO agregar eso al formulario)
- el enunciado menciona que las personas fisicas y juridicas pueden elegir de que forma contribuyen, asi que agrego una lista de las contribuciones que piensa hacer un colaborador que se setea por formulario
- la clase Colaborador se le agregan 2 metodos:
  - colaboracionesDisponibles(abstracto): devuelve una lista con las colaboraciones que puede realizar el colaborador (OJO, no las que decidio contribuir)
  - puedeContribuir: se ingresa una contribucion, y chequea si el tipo de donacion esta dentro de las contribuciones que el colaborador decidio contribuir
- !!! Martin necesito conversar con vos sobre como funciona mi formulario, o fijate mi rama vieja para que lo entiendas, asi se puede usar el formulario no solo para construir un colaborador, sino que para modificarlo


Contribucion:
- creo una clase abstracta padre paraa todas las formas de contribucion dado que comparten un colaborador que la realiza, y en esa clase se incluye controles sobre el colaborador que la realice
- esta clase nueva permite llamar alguna contribucion de forma generica cuando se necesite hacer algo con distintos tipos de contribuciones, y se puede asignar metodos en comun a futuro
- agrego en esa clase un atributo tipo de contribucion por un enum el cual se setea en el constructor de una contribucion hija
- saco al colaborador de cada uno de las contribuciones hijas dado que ahora estan en la clase padre, y se setean con un metodo el cual hace la verificacion si el colaborador puede hacer esa contribucion
- por el tema de la carga masiva de contribuciones, cada contribucion hija le agrego un constructor vacio (porque la carga masiva no contiene datos de la contribucion)

No toque ninguna de las funcionalidades ya existentes(excepto colaboradores que deberia hablar con martin para ver eso)
Si ven que les parece OK, por favor ajusten sus tests para que reflejen estos cambios
