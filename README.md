# Comentarios iniciales

He preferido crear un documento en el que explico lo más importante de la aplicación en lugar de poner comentarios en el código. La razón es que pienso que los comentarios suelen quedarse obsoletos fácilmente y suelen llevar a confusión. Creo que el propio código (así como los tests unitarios), deberían ser suficientemente explicatorios y claros como para dar una idea de la funcionalidad (al menos, ese ha sido mi objetivo). A lo largo de este documento, resaltaré puntos interesantes de la aplicación y el por qué de algunas decisiones tomadas.

# Instalación

La aplicación ha sido creada a partir de un maven archetype. Este en concreto es de Adam Bien para Java EE 8. Viene con un fichero Docker y un script para crear el contenedor y deployar la aplicación (muy cómodo y fácil de usar). Si teneis Docker instalado y sois usuarios de Linux, con dar permisos de ejecución y ejecutar el script "buildAndRun.sh" es suficiente para tener la aplicación lista para su uso (http://localhost:8080/bruuser/).

Como servidor de aplicaciones he utilizado Wildfly 11. Los motivos han sido los siguientes:
- Viene con una base de datos H2 built-in (que era uno de los requerimientos de la prueba)
- En Odigeo usamos JBoss con lo que estoy más familizarado con Wildfly que con Glassfish (que venía en el Dockerfile de Adam Bien).

# Pom.xml

Se ha intentado usar el mínimo número de dependecias. De hecho, a parte de Java ee 8 api no hay ninguna otra (excepto para el scope de test).

# Arquitectura parte de Java

He decidido seguir el patrón BCE (boundary-controller-entity). Así pues, bajo el paquete de "business" se aglutina la lógica de la aplicación estructurada por paquetes con nombres semánticos. Estos a su vez se subdividen en boundary-controller-entity:

- Boundary: lógica de negocio
- Controller (opcional): su responsabilidad radica en "aligerar" el boundary cuando este se va de manos. En este caso, no ha sido necesario crear ningún controller.
- Entity: como su nombre indica, contiene las entidades que persistiremos en la base de datos.
 
# Persistencia

Como he comentado más arriba, se hace uso de H2. Sigo el patrón CoC (convention over configuration) y delego la gestión de las transacciones al servidor de aplicaciones. Creo que es suficiente para una aplicación de juguete como viene a ser esta. Podría haber sido interesante utilizar "optimistic locking" para gestionar las transacciones y evitar problemas de "stale entities" derivadas de la concurrencia (creo que es tan sencillo como añadir un campo en las entities y anotarlo con @Version). Lo dicho, al tratarse de una aplicación de juguete, he preferido seguir el patrón CoC y no complicarme más.

# Inyección de dependencias

Por comodidad, en "beans.xml", "bean-discovery-mode" está seteado a "all" (así evito tener que anotar explícitamente los beans que quiero que se puedan inyectar).

# Tests

- Se han añadido tests unitarios a todas las clases. JaCoCo da una cobertura de un 96%, aunque me aventuraría a decir que es de casi el 100% por que se están teniendo en cuenta clases sin código o clases con métodos estáticos que no se construyen. No se han utilizado mutability-tests (aunque me gustaría probarlo algún día).
- He añadido un proyecto de maven nuevo (llamado "system-tests") en el que se crea un cliente que testea el servicio REST (sólo hay dos tests, lo he creado a modo ejemplo). Para probarlo, es importante tener la aplicación en marcha y limpia antes de ejecutar los tests (si se han añadido usuarios, los tests fallarán).
- He añadido también "functional-test" con Selenium y phantomjs (headless browser). Sólamente contiene una feature que testea la creación de usuarios (escrita en Gherkin). El framework creado es funcional (he reutilizado parte de otro proyecto que tenía) y podría ampliarse fácilmente con más funcionalidad para dar cobertura a todo tipo de tests funcionales. Si se quiere probar, ir a la carpeta functional-tests, ejectuar npm install y luego npm test. La aplicación tiene que estar levantada para que el test funcione y se necesita node (el framework está desarrollado en node) y npm (para descargar dependencias y lanzar los tests).

Con esto la aplicación quedaría cubierta a nivel de tests en las tres capas de la pirámide: unit testing, servicios (system-tests) y UI (functional-tests). Obviamente, se pueden añadir más tipos de tests: por ejemplo, los componentes del frontal, que no llevan tests. Aún así, creo que está bastante bien para dejar clara la idea.

# Arquitectura del frontal

Tomé la decisión de no utilizar ningún framework (tipo angular o backbone) siguiendo con la idea de mantener las cosas simples y por darme el gusto de desarrollar un pequeño framework con lo esencial. Este mini-framework está basado en el patrón MVC con influencias de REDUX.

El framework consiste en una serie de componentes que se comunican entre sí mediante eventos. Estos eventos los escucha un controlador (llamado bus) que actúa simplemente como orquestador de acciones. Este controlador contiene poco código: la idea es que actúe como bus de eventos y este desprovisto de lógica en la medida de lo posible.

Los componentes se encargan de la presentación/acciones de las diferentes partes de la página (en este caso sólo dos: el formulario de creación/modificación de usuarios y la lista de usuarios). Los datos se pintan en pantalla usando templates de Lodash.

El frontal consume el servicio REST del backend y, en cuanto a css, se utiliza bootstrap. Aunque la página sea index.jsp, la única directiva que se ha utilizado es include.

# Monitoring/logging

No se ha añadido nada de monitoring/logging aunque se podría haber hecho fácilmente a través de interceptors.

# Exception mappers

Se ha añadido un EJBExceptionMapper para mappear ConstraintViolationException y evitar un 500. Se transforman en una response con error 400 (bad request) que es más apropiado y que se trata en el frontal alertando al usuario de que los campos que ha introducido no son correctos.

# Campos del usuario

El tema de los contraints sobre los campos de usuario se ha hecho a través de anotaciones de javax (en la medida de lo posible). La validación de los campos password, username y fullname se ha hecho a través de un ConstraintValidator (CrossCheckConstraintValidator).

# Last update

Este campo se calcula y se sobreescribe en la entidad antes de ser persistida en la base de datos mediante un método anotado con @PreUpdate y @PrePersist.

# Password

Antes de ser persistido en la base de datos, al password recibido desde el frontal se le añade una cadena generada (salt) y se encripta, pasando a persistirlo a continuación. Obviamente, este hash no pasa las constraints del password, con lo que una nueva variable booleana ha tenido que ser creada en la entidad para poder controlar este hecho. Tanto el password como esta nueva variable han sido marcados como @XmlTransient y no se mandan al frontal.

El hecho de que el password se pueda mandar desde el frontal para ser modificado pero no se quiera enviar al frontal (por motivos de seguridad), ha hecho necesario crear un nuevo adapter que de solución a esta casuística (HalfDuplexXmlAdapter). Este adapter permite recibir datos pero a la hora de mandarlos, envía cadena vacía.

Hay que decir que quizás hubiese podido ser interesante haber desarrollado la aplicación en https (al menos para que el envío de nuevos passwords hubiese ido encriptado). O también haber permitido acceso a la aplicación sólo a un usuario "admin", por ejemplo. Tratándose de una aplicación de juguete, era añadir todavía más complejidad a algo que no había sido pedido y, que si nos podemos a pensar, puede ser infinito, así que decidí no hacerlo (YAGNI, you're not gonna need it).

# Servicio REST

AppUsersResource.java (JAX-RS) gestiona las peticiones y delega la lógica a AppUsersManager (single responsability principle) y aumentamos la cohesión de las clases. También conseguimos evitar que AppUsersManager quede acoplado a JAX-RS y pueda emplearse también, por ejemplo, para gestionar JAX-WS.

Tanto AppUsersResource como AppUsersManager han sido marcadas como @Stateless (ganamos monitoring y dejamos al servidor de aplicaciones que las gestione como quiera ya que no tienen estado).

AppUsersResource delega también todas aquellas peticiones que se refieren a un usuario en concreto a AppUserResource. Con esto aligeramos un poco más AppUsersResource y le quitamos responsabilidades también aunque las dos clases quedan acopladas.

Decidí juntar la creación/edición de usuarios en un mismo método REST (verbo PUT). Puesto que el identificador (technical key) no está generado en el servidor y viene determinado por el usuario, creo que la elección del verbo es correcta: PUT puede utilizarse para crear/updatear recursos siempre y cuando las operaciones sean idempotentes, como es el caso aquí. Podía haber utilizado POST para la creación (mandando al usuario la URL al nuevo recurso en la respuesta) y PUT para la modificación pero decidí no hacerlo así. Esta decisión me pareció ventajosa al principio (simplifica el frontal), aunque luego, y por temas del password, llevó a incluir un poco más de lógica. Si tuviera que mantener el proyecto y viera que tenerlo junto en un sólo método empieza a ser un problema, lo refactorizaría sin dudarlo y lo transformaría en dos métodos (juntar no suele ser buena idea).

En el frontal también decidí juntar estas dos operaciones: se crea usuario cuando el userName no exite en la BD y se actualiza la información cuando el userName coincide con el de algún usuario guardado. Como he comentado antes, el password no se manda al frontal. Si al editar a un usuario se deja el campo password en blanco, este no se modifica. Si lleva valor, se procede a generar un nuevo password al usuario. La creación de un usuario obliga a que el request de creación lleve este campo. Al igual que sucede con la parte del backend, en caso de que las operaciones de actualización/creación de usuario ganaran complejidad, se podrían separar estas responsabilidades.

La gestión de la validez de un bean se realiza a través del objeto Validator que inyecto en AppUsersManager y que valida la entidad, lanzando una excepción si alguna constraint ha sido violada. No hay validaciones en el frontal (por mantenerlo simple).

# Webservice (WSDL)

El webservice es también lo más simple posible: un POJO anotado con @Webservice y un método anotado con @WebMethod. La lógica se delega a AppUsersManager (agnóstico en cuanto a protocolo) que comprueba si las credenciales son correctas y devuelve en consecuencia. He creado un pequeño script que permite probar el Webservice fácilmente (curlWebService). Hace uso de request.xml donde se expresa la petición.

# Control de versiones

La gestión de versiones la suelo realizar a través de Mercurial (bitbucket): tengo cuenta personal y del trabajo. También tengo cuenta en Github (git) y he usado subversion anteriormente. Los tres me parecen muy válidos, aunque casi que me quedo con los que tienen un modelo distribuido (en este caso Git y Mercurial). Este proyecto lo he realizado integramente a través de Github, en un repositorio público que cualquiera puede clonar y al que cualquiera puede contribuir. En un proyecto con varias personas contribuyendo, utilizaría alguna de estas dos tecnologías, quizás más Git, por estar más de moda y tener mejores herramientas para controlar proyectos con muchísimos commits (en Odigeo están estudiando pasar de Mercurial a Git por esto). Espero que esto responda a la pregunta (no acabo de tener claro si era esto lo que queríais saber).

Por último comentar que cualquier duda o comentario sobre el código es bien recibido. Gracias por vuestro tiempo!