# s1_grupos
Repositorio del proyecto de vivienda universitaria del grupo 3 de la sección 1

UNIT NAME = gruposPU

## Recurso Usuario
El objeto Usuario tiene 2 representaciones JSON:	

### Representación Minimum
```javascript
{
    id: '' /*Tipo Long*/,
    nombre: '' /*Tipo String*/,
    apellido: '' /*Tipo String*/,
    nickname: '' /*Tipo String*/,
    contrasena: '' /*Tipo String*/,
    e-mail: '' /*Tipo String*/
}
```

### Representación Detail
```javascript
{
    // todo lo de la representación Minimum más los objetos Minimum con relación simple.
    patrocinio: {
    pago: '' /*Tipo Long*/
    },
    evento: {
    nombre: '' /*Tipo String*/,
    fechaInicio: '' /*Tipo Date*/,
    fechaFin: '' /*Tipo Date*/
    },
    tarjeta: {
    // Representación Minimum de los objetos de tipo tarjeta.
    }
    empresa: {
    // Representación Minimum de los objetos de tipo empresa.
    }
    noticia: {
    // Representación Minimum de los objetos de tipo noticia.
    }
    blog: {
    // Representación Minimum de los objetos de tipo blog.
    }
    grupo: {
    // Representación Minimum de los objetos de tipo grupo.
    }
}
```

### GET /usuarios

Retorna una colección de objetos usuario en representación Detail.
Cada usuario en la colección tiene embebidos los siguientes objetos: tarjeta, empresa, patrocinio, noticia, evento, blog y grupo.

#### Parámetros

##### N/A

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de representaciones Detail
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

### GET /usuarios/{id}

Retorna una colección de objetos usuario en representación Detail.
Cada usuario en la colección tiene los siguientes objetos: tarjeta, empresa, patrocinio, noticia, evento, blog y grupo.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto usuario a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto usuario en representaciones Detail
404|No existe un objeto usuario con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

### POST /usuarios

Es el encargado de crear objetos usuario.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
body|body|Objeto usuario que será creado|Sí|representaciones Detail

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto usuario ha sido creado|representaciones Detail
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo crear el objeto usuario|Mensaje de error

### PUT /usuarios/{id}

Es el encargado de actualizar objetos usuario.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto usuario a actualizar|Sí|Integer
body|body|Objeto usuario nuevo|Sí|representaciones Detail

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto usuario actualizado|representaciones Detail
412|business exception, no se cumple con las reglas de negocio|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo actualizar el objeto usuario|Mensaje de error


### Recurso Grupo
El objeto Grupo tiene 2 representaciones JSON:	

#### Representación Minimum
```javascript
{
    nombre: '' /*Tipo String*/,
    id:  '' /*Tipo int*/,
    descripcion: '' /*Tipo String*/
}
```

#### Representación Detail
```javascript
{
    // todo lo de la representación Minimum más los objetos Minimum con relación simple.
   noticias: [
    { /* Noticia 1 en su representación JSON Minimum*/ },
 ...
  { /* Noticia n en su representación JSON Minimum*/ },
   ],
    categorías: [
    { /* Categoría 1 en su representación JSON Minimum*/ },
 ...
  { /* Categoría n en su representación JSON Minimum*/ },
   ],
 administradores: [
    { /* Usuario 1 en su representación JSON Minimum*/ },
 ...
  { /* Usuario n en su representación JSON Minimum*/ },
   ],
 usuarios: [
    { /* Usuario 1 en su representación JSON Minimum*/ },
 ...
  { /* Usuario n en su representación JSON Minimum*/ },
   ],
 blogs: [
    { /* Blog 1 en su representación JSON Minimum*/ },
 ...
  { /* Blog n en su representación JSON Minimum*/ },
   ],
 eventos: [
    { /* Evento 1 en su representación JSON Minimum*/ },
 ...
  { /* Evento n en su representación JSON Minimum*/ },
   ]
}
```
#### GET /grupos

Retorna una colección de objetos Grupo en representación Minumun

#### Parámetros

N/A

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Grupo en [representación Minimum](#recurso-grupo)
500|Error consultando grupo|Mensaje de error

#### GET /grupos/id

Retorna el grupo con el id especificado en representación Detail

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idGrupo|Path|ID del objeto Grupo a consultar|Sí|String

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Grupo en [representación Detail](#recurso-grupo)
404|No existe un objeto con el ID solicitado|Mensaje de error
500|Error consultando grupo|Mensaje de error

#### GET /grupos/nombre?nombre=nombre

Retorna el grupo con el nombre especificado en representación Detail

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
nombreGrupo|Path|nombre del objeto Grupo a consultar|Sí|String

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Grupo en [representación Detail](#recurso-grupo)
404|No existe un objeto con el nombre solicitado|Mensaje de error
500|Error consultando grupo|Mensaje de error


#### POST /grupo

Crea un objeto Grupo asociado al usuario que lo creó

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idUsuario|PathParam|ID del objeto Usuario al cual se asociará el objeto Grupo|Sí|Integer
body|body|Objeto Grupo que será asociado al objeto Usuario indicado|Sí|[Representación Minimum](#recurso-grupo)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Objeto Grupo asociado al usuario indicado|[Representación Detail de Grupo](#recurso-grupo)
500|No se pudo asociar el objeto Grupo|Mensaje de error

#### PUT/grupos/id

Modifica un objeto Grupo

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idGrupo|PathParam|ID del objeto Grupo a modificar|Sí|Integer
body|body|Objeto Grupo que será asociado al objeto Usuario indicado|Sí|[Representación Minimum](#recurso-grupo)
idUsuario|Body|ID del usuario que realiza la modificación|Sí|Integer
#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Se remplazó el grupo|Objeto Grupo en [Representación Minimum](#recurso-grupo)
404|No existe un objeto con el ID solicitado|Mensaje de error
500|No se pudo remplazar el grupo|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### DELETE /grupos/id

Remueve un objeto Grupo.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idGrupo|PathParam|ID del objeto Grupo a modificar|Sí|Integer
404|No existe un objeto con el ID solicitado|Mensaje de error
idUsuario|Body|ID del usuario que realiza la modificación|Sí|Integer
#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto removido|N/A
500|No se pudo borrar la empresa|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### GET /grupos/id/noticias

Retorna la lista de las noticias que se publicaron en el grupo con el id especificado.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idGrupo|Path|ID del objeto Grupo que tiene asociado las noticias a consultar|Sí|String

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Noticia en [representación Minimum](#recurso-noticia)
404|No existe un objeto con el ID solicitado|Mensaje de error
500|Error consultando grupo|Mensaje de error

#### POST /grupos/id/noticia

Crea un objeto Noticia asociado al usuario que lo creó, en el grupo con el id especificado.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idUsuario|PathParam|ID del objeto Usuario al cual se asociará el objeto Noticia|Sí|Integer
body|body|Objeto Noticia que será asociado al objeto Usuario y al objeto Grupo indicado|Sí|[Representación Minimum](#recurso-noticia)
405|method not allowed, no existe permiso para el recurso|Mensaje de error
#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Objeto Noticia asociado al usuario indicado|[Representación Minimum de Noticia](#recurso-noticia)
500|No se pudo asociar el objeto Noticia|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error


#### GET /grupos/id/blogs

Retorna la lista de las blogs que se publicaron en el grupo con el id especificado.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idGrupo|Path|ID del objeto Grupo que tiene asociado los blogs a consultar|Sí|String

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Blog en [representación Minimum](#recurso-blog)
500|Error consultando blogs|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### POST /grupos/id/blogs

Crea un objeto Blog asociado al usuario que lo creó, en el grupo con el id especificado.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idUsuario|PathParam|ID del objeto Usuario al cual se asociará el objeto Blog|Sí|Integer
body|body|Objeto Blog que será asociado al objeto Usuario y al objeto Grupo indicado|Sí|[Representación Minimum](#recurso-blog)
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Objeto Blog asociado al usuario indicado|[Representación Minimum de Blog](#recurso-blog)
500|No se pudo asociar el objeto Blog|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### GET /grupos/id/usuarios

Retorna la lista de usuarios del grupo con el id especificado.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idGrupo|Path|ID del objeto Grupo que tiene asociado los administradores a consultar|Sí|String

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Usuario en [representación Minimum](#recurso-usuario)
500|Error consultando usuarios|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### POST /grupos/id/usuarios

Inscribe a un usuario en el grupo con el id especificado.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idUsuario|PathParam|ID del objeto Usuario que se inscribirá|Sí|Integer
body|body|Id del Usuario que será asociado al grupo indicado|Sí|[Representación Minimum](#recurso-Usuario)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Objeto Usuario asociado al grupo indicado|[Representación Minimum de usuario](#recurso-usuario)
500|No se pudo asociar el objeto Usuario|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error


#### GET /grupos/id/administradores

Retorna la lista de administradores del grupo con el id especificado.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idGrupo|Path|ID del objeto Grupo que tiene asociado los administradores a consultar|Sí|String

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Usuario en [representación Minimum](#recurso-usuario)
500|Error consultando administradores|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### POST /grupos/id/administradores

Asocia un usuario como administrador, en el grupo con el id especificado.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idUsuario|PathParam|ID del objeto Usuario que dió permisos de Administrador|Sí|Integer
body|body|Id del Usuario que será asociado al objeto Usuario que lo creó y al objeto Administrador indicado|Sí|[Representación Minimum](#recurso-Usuario)
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Objeto Usuario asociado al usuario indicado|[Representación Minimum](#recurso-usuario)
500|No se pudo asociar el objeto Usuario|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### GET /grupos/id/categorias

Retorna la lista de categorías del grupo con el id especificado.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idGrupo|Path|ID del objeto Grupo que tiene asociado las categorías a consultar|Sí|String

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Categoría en [representación Minimum](#recurso-categoría)
500|Error consultando categorías|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### POST /grupos/id/categorias

Asocia una categoría a un grupo

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idUsuario|PathParam|ID del objeto Usuario que realiza la acción|Sí|Integer
body|body|Categoría a ser adicionada al grupo|Sí|[Representación Minimum](#recurso-categoría)
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Objeto Categoría asociado al Grupo indicado|[Representación Minimum](#recurso-categoría)
500|No se pudo asociar el objeto Categoría|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

## Recurso Lugar
El objeto Lugar tiene 2 representaciones JSON
#### Representación Minimun
```javascript
{
    id: "/*Tipo Integer*/,
    nombre: '' /*Tipo String*/,
    direccion: '' /*Tipo String*/,
}
```
#### Representacion Detail
```javascript
{
    id: "/*Tipo Integer*/
    nombre: '' /*Tipo String*/,
    direccion: '' /*Tipo String*/,
    capacidad: "/* TIpo Integer*/,
}
```
#### GET/lugares
Retorna un coleccion de objetos Lugar en representacion Minimun
#### Parametros
N/A
#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de representaciones Minimun
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error
#### GET /lugares/{id}

Retorna un objeto  Lugar en representación Detail.

#### Parámetros

Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--
id|id del objeto Lugar a consultar|Sí|Integer
#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Lugar en representaciones Detail
404|No existe un objeto Lugar con la id solicitada|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

#### POST /lugares

Es el encargado de crear objetos Lugar.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
lugar|body|Objeto Lugar que será creado|Sí|Representación Detail

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Lugar ha sido creado|Representación Detail
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo crear el objeto Lugar|Mensaje de error


#### DELETE /lugares/{id}

Elimina un objeto Lugar.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|id del objeto Lugar a eliminar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
500|Error interno|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### PUT /lugares/{id}

Es el encargado de actualizar objetos Lugar.

## Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Lugar a actualizar|Sí|Integer
body|body|Objeto Lugar nuevo|Sí|Representación Detallada

##Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Lugar actualizado|Representación Detallada
412|business exception, no se cumple con las reglas de negocio|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

### Recurso Noticia
El objeto Noticia tiene 2 representaciones JSON:	

#### Representación Minimum
```javascript
{
    titulo: '' " /*Tipo String*/,
    informacion: '' " /*Tipo String*/,
    id: " " /*Tipo Long*/
}
```

#### Representación Detail
```javascript
{
    // todo lo de la representación Minimum más los objetos Minimum con relación simple.
    multimedia:[{/*Información entidad multimedia 1 en forma Minimum*/},...
	{/*Información entidad multimedia n en forma Minimum*/}]
    comentarios: [{/*Información entidad comentario 1 en forma Minimum*/}...
        {/*Información entidad comentario n en forma Minimum*/}]
    autor:{ '' /*Representación minimum de la clase Usuario*/}
}
```



#### GET /noticias

Retorna una colección de objetos Noticia en representación Detail.
Cada Noticia en la colección tiene embebidos los siguientes objetos: Multimedia, Usuario.

#### Parámetros

#### N/A

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de [representaciones Detail](#recurso-noticia)
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

#### GET /noticias/{id}

Retorna una colección de objetos Noticia en representación Detail.
Cada Noticia en la colección tiene los siguientes objetos: Multimedia, Usuario. 

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Noticia a consultar|Sí|Long

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Noticia en [representaciones Detail](#recurso-noticia)
404|No existe un objeto con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

#### GET /usuarios/{id}/noticias

Retorna una colección de objetos Noticia en representación Detail de un Usuario particular.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Usuario a consultar su colección de Noticias|Sí|Long


#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de Noticias en [representaciones Minimum](#recurso-noticia)
404|No existe un objeto Noticia con el id solicitado dentro de ese Usuario|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

#### GET /usuarios/{id}/noticias/{id_noticia}

Retorna un objeto Noticia en representación Detail de un  usuario particular. 

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Usuario a consultar su colección de Noticias|Sí|Long
id_noticia|Path|ID del objeto Noticia|Sí|Long

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de Noticias en [representaciones Minimum](#recurso-noticia)
404|No existe un objeto Noticia con el id solicitado dentro de ese Usuario|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

#### PUT /usuarios/{id}/noticias/{id_noticia}

Es el encargado de actualizar objetos Noticia de un Usuario. El id es la identificación particular que posee el usuario.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Usuario a actualizar|Sí|Long
id_noticia|Path|Id del objeto Noticia a consultar|Sí|Long
body|body|Objeto Noticia nuevo|Sí|[Representación Detail](#recurso-noticia)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Noticia actualizado|[Representación Detail](#recurso-comentario)
412|business exception, no se cumple con las reglas de negocio|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo actualizar el objeto Noticia del Usuario seleccionado|Mensaje de error

#### POST /usuarios/{id}/noticias/{id_noticia}

Es el encargado de poner objetos Noticia en un Usuario.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Usuario para asignarle una Noticia|Sí|String
id_noticia|Path|Id del objeto Noticia a consultar|Sí|Long
body|body|Objeto Noticia nuevo|Sí|[Representación Detail](#recurso-noticia)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Noticia se almacenó en el usuario.|[Representación Minimum](#recurso-noticia)
412|business exception, no se cumple con las reglas de negocio|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo actualizar crear el objeto Noticia del Usuario seleccionado|Mensaje de error

#### DELETE /usuarios/{id}/noticias/{id_noticia}

Elimina un objeto Noticia de un usuario particular. 

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Usuario que contiene la noticia| Sí| Long
id_noticia|Path|ID del objeto Noticia a eliminar|Sí|Long


#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
500|Error interno|Mensaje de error
404| El usuario dado no existe| Mensaje de error
412|precondition failed, no cumple con la regla de negocio para eliminar el recurso|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### GET /grupos/{id}/noticias

Retorna una colección de objetos Noticia en representación Detail de un Usuario particular.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Grupo a consultar su colección de Noticias|Sí|String


#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de Noticias en [representaciones Minimum](#recurso-noticia)
404|No existe un objeto Noticia con el id solicitado dentro de ese Grupo|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

#### GET /grupos/{id}/noticias/{id_noticia}

Retorna un objeto Noticia en representación Detail de un  grupo particular. 

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Grupo a consultar su colección de Noticias|Sí|String
id_noticia|Path|ID del objeto Noticia|Sí|Long

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de Noticias en [representaciones Minimum](#recurso-noticia)
404|No existe un objeto Noticia con el id solicitado dentro de ese Usuario|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

#### PUT /grupos/{id}/noticias/{id_noticia}

Es el encargado de actualizar objetos Noticia de un Grupo. El id es la identificación particular que posee el grupo.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Grupo a actualizar|Sí|String
id_noticia|Path|Id del objeto Noticia a consultar|Sí|Long
body|body|Objeto Noticia nuevo|Sí|[Representación Detail](#recurso-comentario)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Comentario actualizado|[Representación Minimum](#recurso-noticia)
412|business exception, no se cumple con las reglas de negocio|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo actualizar el objeto Noticia |Mensaje de error

#### POST /grupos/{id}/noticias

Es el encargado de poner objetos Noticia en un Grupo.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Grupo para asignarle una Noticia|Sí|String
id_noticia|Path|Id del objeto Noticia a consultar|Sí|Long
body|body|Objeto Noticia nuevo|Sí|[Representación Detail](#recurso-minimum)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Noticia se almacenó en el grupo.|[Representación Minimum](#recurso-noticia)
412|business exception, no se cumple con las reglas de negocio|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo actualizar crear el objeto Noticia del Grupo seleccionado|Mensaje de error

#### DELETE grupos/{id}/noticias/{id_noticia}

Elimina un objeto Noticia de un Usuario específico. 

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Usuario| Sí|Long
id_noticia|Path|ID del objeto Noticia a eliminar|Sí|Long

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
500|Error interno|Mensaje de error
412|precondition failed, no cumple con la regla de negocio para eliminar el recurso|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

### Recurso Categoría
El objeto Categoría tiene 2 representaciones JSON:	

#### Representación Minimum
```javascript
{
    id: '' /*Tipo Long*/
    tipo: '' /*Tipo String*/
}
```

#### Representación Detail
```javascript
{
   id: '' /*Tipo Long*/
   tipo: '' /*Tipo String*/,
descripcion: '' /*Tipo String*/
   rutaIcono: '' /*Tipo String*/
}
```
#### GET /categorias

Retorna una colección de objetos Categoria en representación Minumun

#### Parámetros

N/A

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Categoria en [representación Minimum](#recurso-categoria)
404|Error consultando grupo|No existe un grupo con ese nombre
500|Error interno|Mensaje de error

#### GET /categorias/id

Retorna la categoría con el id especificado con en representación Detail

#### Parámetros

ID|Path|ID del objeto Categoría a consultar|Sí|Categoria

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Categoria en [representación Detail](#recurso-categoria)
404|No existe una categoría con el id dado|Mensaje de error
500|Error consultando categoria|Mensaje de error

#### GET /categorias?nombre=nom

Retorna la categoría con el id especificado con en representación Detail

#### Parámetros

nombreCategoria|Path|nombre del objeto Categoría a consultar|Sí|Categoria

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Categoria en [representación Detail](#recurso-categoria)
404|No existe una categoría con el nombre dado|Mensaje de error
500|Error consultando categoria|Mensaje de error


#### POST /categorias

Crea un objeto Categoría

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
body|body|Objeto Categoria a crearse|Sí|[Representación Minimum](#recurso-categoria)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Objeto Categoria|[Representación Detail de Grupo](#recurso-categoria)
500|No se pudo crear el objeto Categoria|Mensaje de error

#### PUT/categorias/id

Modifica un objeto Categoria

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idCategoria|Path|ID del objeto Categoria a modificar|Sí|String
body|body|Objeto Categoria que será modificado|Sí|[Representación Detail](#recurso-categoria)
idUsuario|Path|ID del usuario que realiza la modificación|Sí|String
#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Se remplazó la categoria|Objeto Categoria en [Representación Minimum](#recurso-categoria)
500|No se pudo remplazar la categoria|Mensaje de error
404|No existe una categoría con el id dado|Mensaje de error
403|method not allowed, no existe permiso para el recurso|Mensaje de error

#### DELETE /categorias/id

Remueve un objeto Categoria con el id especificado

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idCategoria|Path|ID del objeto Categoria a borrar|Sí|String
idUsuario|Body|ID del usuario que realiza la modificación|Sí|String
#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto removido|N/A
404|No existe una categoría con el id dado|Mensaje de error
500|No se pudo borrar la categoria|Mensaje de error
403|method not allowed, no existe permiso para el recurso|Mensaje de error

#### GET /categorias/id/grupos

Devuelve los grupos que contienen esa categoría

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idCategoria|Path|ID del objeto Categoria a consultar|Sí|String
idUsuario|Body|ID del usuario que realiza la modificación|Sí|String
#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Lista de grupos Grupo en [representación Minimum](#recurso-grupo)
500|Error consultando los grupos de la categoría|Mensaje de error

## Recurso Blog
El recurso Blog tiene dos representaciones JSON:
### Representación Minimum
```javascript
{
    titulo: '' /*Tipo String*/,
    nombreAutor: '' /*Tipo String*/,
    numCalificaciones: '' /*Tipo Integer*/,
    promedio: '' /*Tipo Double*/
}
```
### Representación Detail
```javascript
{
    titulo: '' /*Tipo String*/,
    nombreAutor: '' /*Tipo String*/,
    numCalificaciones: '' /*Tipo Integer*/,
    promedio: '' /*Tipo Double*/,
    Multimedia: [{ /*Multimedia 1 en su representación Minimum*/ },..., { /*Multimedia n en su representación Minimum*/ }],
    Comentarios: [{ /*Comentario 1 en su representación Minimum*/ },..., { /*Comentario n en su representación Minimum*/ }]
    Calificaciones: [{/*Calificación 1 en su representación Minimum*/ },..., {/*Calificaciónn en su representación Minimum*/ }]
}
```

### GET /grupos/{grupoid}/blogs
Retorna una colección de los objetos Blog de un determinado Grupo en representación Detail.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del grupo|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de objetos Blog en [representación Detail](#recurso-blog)
404|not found, no existe un objeto Grupo con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

### GET /grupos/{grupoid}/blogs/{blogid}
Retorna el objeto Blog con el ID solicitado en un determinado Grupo.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del grupo|Sí|Integer
blogid|Path|ID del blog|Sí|Integer

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de objetos Blog en [representación Detail](#recurso-blog)
404|not found, no existe un objeto Grupo con el ID solicitado o no existe un objeto Blog con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error


### POST /grupos/{grupoid}/blogs
Es el encargado de crear Objetos Blog en un grupo.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del grupo que crea el Blog|Sí|Integer
body|body|Objeto Blog que será creado|Sí|[Representación Detail](#recurso-blog)

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Blog ha sido creado|[Representación Detail](#recurso-blog)
404|not found, no existe un objeto Grupo con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
500|Error interno|Mensaje de error

### PUT /grupos/{grupoid}/blogs/{blogid}
Es el encargado de actualizar objetos Blog.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del objeto Grupo donde se encuentra el Blog a actualizar|Sí|Integer
blogid|Path|ID del objeto Blog a actualizar|Sí|Integer
body|body|Objeto Blog nuevo|Sí|[Representación Detail](#recurso-blog)

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Blog actualizado|[Representación Detail](#recurso-blog)
404|not found, no existe un Objeto Grupo con el ID solicitado o un objeto Blog con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
412|business exception, no se cumple con las reglas de negocio|Mensaje de error
500|No se pudo actualizar el objeto Book|Mensaje de error

### DELETE /grupos/{grupoid}/blogs/{blogid}
Es el encargado de eliminar objetos Blog en un Grupo.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del objeto Grupo|Sí|Integer
blogid|Path|ID del objeto Blog a eliminar|Sí|Integer

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
404|not found, no existe un objeto Grupo con el ID solicitado o un objeto Blog con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

### GET /usuarios/{usuarioid}/blogs
Retorna una colección de objetos Blog que el usuario tiene como favoritos.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
usuario|Path|ID del usuario|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de objetos Blog en [representación Detail](#recurso-blog)
404|not found, no existe un objeto Usuario con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

## Recurso Calificación
El recurso Calificación tiene dos representaciones JSON:
### Representación Minimum
```javascript
{
    id: '' /*Tipo Long*/,
    calificacion: '' /*Tipo Double*/,
    fecha: '' /*Tipo Date*/
}
```
### Representación Detail
```javascript
{
    /* Mismos atributos de la versión Minimum exceptuando relaciones simples*/
    calificador: {/*Representación Minimum del Usuario*/}
    blog:{/*Representación Minimum del Blog*/}
}
```

### GET /grupos/{grupoid}/blogs/{blogid}/calificaciones
Retorna la colección de objetos Calificacion de un Blog.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del grupo|Sí|String
blogid|Path|ID del blog|Sí|Integer

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de objetos Calificacion en [representación Detail](#recurso-calificacion)
404|not found, no existe un objeto Grupo con el ID solicitado o no existe un objeto Blog con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

### GET /grupos/{grupoid}/blogs/{blogid}/calificaciones/{id}
Retorna el objeto Comentario de un Blog.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del grupo|Sí|String
blogid|Path|ID del blog|Sí|Integer
id|Path|ID del Calificacion|Sí|Long

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|objeto Calificacion en [representación Detail](#recurso-calificacion)
404|not found, no existe un objeto Grupo con el ID solicitado, un objeto Blog con el ID solicitado, o una calificacion con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

### POST /grupos/{grupoid}/blogs/{blogid}/calificaciones
Es el encargado de crear objetos Calificacion en un Blog.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del grupo|Sí|String
blogid|Path|ID del blog|Sí|Integer
body|body|Objeto Calificacion que será creado|Sí|[Representación Detail](#recurso-calificacion)

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Calificacion ha sido creado|[Representación Detail](#recurso-calificacion)
404|not found, no existe un objeto Grupo con el ID solicitado o un objeto Blog con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
500|Error interno|Mensaje de error

### PUT /grupos/{grupoid}/blogs/{blogid}/calificaciones/{id}
Es el encargado de actualizar objetos Calificacion en un Blog.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del grupo|Sí|Integer
blogid|Path|ID del blog|Sí|Integer
id|Path|ID del comentario a actualizar|Sí|Integer
body|body|Objeto Calificacion que será actualizado|Sí|[Representación Detail](#recurso-calificacion)

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Calificacion ha sido actualizado|[Representación Detail](#recurso-calificacion)
404|not found, no existe un objeto Grupo con el ID solicitado, un objeto Blog con el ID solicitado o un objeto Calificacion con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
500|Error interno|Mensaje de error

### DELETE /grupos/{grupoid}/blogs/{blogid}/calificacion/{id}
Es el encargado de eliminar objetos Calificacion en un Blog.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del objeto Grupo|Sí|String
blogid|Path|ID del objeto Blog|Sí|Integer
id|Path|ID del objeto Calificacion a eliminar|Sí|Long

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
404|not found, no existe un objeto Grupo con el ID solicitado, un objeto Blog con el ID solicitado, o un objeto Calificacion con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

## Recurso Evento
El objeto Evento tiene 2 representaciones JSON
#### Representación Minimum
```javascript
{
    id: " /*Tipo Integer*/,
    nombre: '' /*Tipo String*/,
    fechaInicio: '' /*Tipo Date*/,
    fechaFin: "/*Tipo Date*/,
}
```
#### Representación Detail
```javascript
{
//Todo lo de la representación Minimum mas un Objeto Lugar con representacion Detail, las siguientes colecciones, usuarios inscritos de Objeto Usuario con la representacion Minimum, patrocinios hechos de Objeto Patrocinio con la representacion Minimum,
}
```
#### GET/grupos/{id}/eventos
Retorna un coleccion de objetos Evento de un grupo en representacion Minimun
#### Parametros
Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--
id|id del objeto Grupo a consultar|Sí|String
#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de representaciones Minimun
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
404|not found, no existe un grupo con ese id|Mensaje de error
500|Error interno|Mensaje de error

#### GET/usuarios/{id}/eventos
Retorna un coleccion de objetos Evento de un usuario en representacion Minimun
#### Parametros
Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--
id|id del objeto Usuario a consultar|Sí|String
#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de representaciones Minimun
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
404|not found, no existe un usuario con ese id|Mensaje de error
500|Error interno|Mensaje de error
#### GET /eventos/{id}

Retorna un objeto  Evento en representación Detail.

#### Parámetros

Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--
nombre|nombre del objeto Evento a consultar|Sí|String
#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Evento en representaciones Detail
404|No existe un objeto Evento con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

#### POST /eventos

Es el encargado de crear objetos Evento.

#### Parámetros

Nombre|Ubicacion|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
lugar|Path|Lugar donde se realizara el evento|Si| Representacion Detail
body|body|Objeto Evento que será creado|Sí|Representacion Detail
#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Evento ha sido creado|Representación Detail
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo crear el objeto Evento|Mensaje de error
#### PUT /evento/{id}

Es el encargado de actualizar objetos Evento.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|id del objeto Evento a actualizar|Sí|String
body|body|Objeto Evento nuevo|Sí|Representación Detail

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Evento actualizado|Representación Detail
412|business exception, no se cumple con las reglas de negocio|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo actualizar el objeto Book|Mensaje de error

#### DELETE /eventos/{id}

Elimina un objeto Evento.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|id del objeto Evento a eliminar|Sí|String

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
500|Error interno|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### GET/eventos/{id}/usuarios
Retorna un coleccion de objetos Usuario de un evento en representacion Minimun
#### Parametros
Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--
id|id del objeto Evento a consultar|Sí|String
#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de representaciones Minimun
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
404|not found, no existe un evento con ese id|Mensaje de error
500|Error interno|Mensaje de error}

#### GET/eventos/{id}/patrocinios
Retorna un coleccion de objetos Patrocinio de un evento en representacion Minimun
#### Parametros
Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--
id|id del objeto Evento a consultar|Sí|String
#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de representaciones Minimun
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
404|not found, no existe un evento con ese id|Mensaje de error
500|Error interno|Mensaje de error}

#### POST/eventos/{id}/usuarios
Añade un usuario al evento 
#### Parametros
Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--
id|id del objeto Evento a consultar|Sí|String
body|body|Objeto Usuario que será asociado|Sí|Representacion Detail
#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de representaciones Minimun
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
404|not found, no existe un evento con ese id|Mensaje de error
500|Error interno|Mensaje de error

#### POST/eventos/{id}/patrocinios
Añade un patrocinio al evento 
#### Parametros
Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--
id|id del objeto Evento a consultar|Sí|String
body|body|Objeto Patrocinio que será asociado|Sí|Representacion Detail
#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de representaciones Minimun
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
404|not found, no existe un evento con ese id|Mensaje de error
500|Error interno|Mensaje de error

#### DELETE /eventos/{eventoid}/usuarios/{usuarioid}

Elimina un Usuario de un Evento.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
eventoid|Path|id del objeto Evento|Sí|String
usuarioid|Path|id del objeto Usuario a eliminar|Sí|String

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Relacion eliminada|N/A
500|Error interno|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### DELETE /eventos/{eventoid}/usuarios/{patrocinioid}

Elimina un Patrocinio de un Evento.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
eventoid|Path|id del objeto Evento|Sí|String
patrocinioid|Path|id del objeto Patrocinio a eliminar|Sí|String

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Relacion eliminada|N/A
500|Error interno|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

### Recurso Tarjeta
El objeto Tarjeta tiene 2 representaciones JSON:	

#### Representación Minimum
```javascript
{
    numero: '' /*Tipo Integer*/,
    dineroDisponible: '' /*Tipo Double*/,
    banco: '' /*Tipo String*/,
    maxCupo: '' /*Tipo Double*/
}
```

#### Representación Detail
```javascript
{
    // todo lo de la representación Minimum más los objetos Minimum con relación simple.
}
```


#### GET /usuarios/{idUsuario}/tarjetas

Retorna una colección de objetos Tarjeta asociados a un objeto Usuario en representación Minimum.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idUsuario|Path|ID del objeto Usuario a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de objetos Tarjeta en [representación Minimum](#recurso-tarjeta)
500|Error consultando tarjetas|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error


#### GET /usuarios/{idUsuario}/tarjetas/{numTarjeta}

Retorna un objeto Tarjeta asociado a un objeto Usuario en representación Minimum.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idUsuario|Path|ID del objeto Usuario a consultar|Sí|Integer
numTarjeta|Path|Número del objeto Tarjeta a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Tarjeta en [representación Minimum](#recurso-tarjeta)
404|No existe un objeto Tarjeta con el Número solicitado asociado al objeto Usuario indicado |Mensaje de error
500|Error interno|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error


#### POST /usuarios/{idUsuario}/tarjetas

Asocia un objeto Tarjeta a un objeto Usuario.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idUsuario|PathParam|ID del objeto Usuario al cual se asociará el objeto Tarjeta|Sí|Integer
body|body|Objeto Tarjeta que será asociado al objeto Usuario indicado|Sí|[Representación Minimum](#recurso-tarjeta)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Objeto Tarjeta asociado al usuario indicado|[Representación Minimum de Tarjeta](#recurso-tarjeta)
500|No se pudo asociar el objeto Tarjeta|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### PUT /usuarios/{idUsuario}/tarjetas/{numTarjeta}

Es el encargado de modificar un objeto Tarjeta asociada a un objeto Usuario.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idUsuario|Path|ID del objeto Usuario cuya colección será remplazada|Sí|Integer
numTarjeta|Path|Número del objeto Tarjeta a modificar|Sí|Integer
body|body|Colección de objetos Tarjeta|Sí|[Representación Minimum](#recurso-tarjeta)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Se remplazó la colección|Colección de objetos Tarjeta en [Representación Minimum](#recurso-tarjeta)
500|No se pudo remplazar la colección|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### DELETE /usuarios/{idUsuario}/tarjetas/{numTarjeta}

Remueve un objeto Tarjeta de la colección en un objeto Usuario.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idUsuario|Path|ID del objeto Usuario asociado al objeto Tarjeta|Sí|Integer
numTarjeta|Path|Número que identifica al objeto Tarjeta que se va a remover|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto removido|N/A
500|Error interno|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

### Recurso Empresa
El objeto Empresa tiene 2 representaciones JSON:	

#### Representación Minimum
```javascript
{
    nit: '' /*Tipo Integer*/,
    nombre: '' /*Tipo String*/,
    logo: '' /*Tipo String*/
}
```

#### Representación Detail
```javascript
{
    // todo lo de la representación Minimum más los objetos Minimum con relación simple.
}
```

#### GET /empresa

Retorna una colección de objetos Empresa en representación Minumum
#### Parámetros
N/A

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK| Colección Objetos Empresa en [representación Minimum](#recurso-empresa)
500|Error consultando empresas|Mensaje de error


#### GET /usuarios/{idUsuario}/empresa

Retorna el objeto Empresa asociado a un objeto Usuario en representación Minimum.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idUsuario|Path|ID del objeto Usuario a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Empresa en [representación Minimum](#recurso-empresa)
500|Error consultando empresa|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### POST /usuarios/{idUsuario}/empresa

Asocia un objeto Empresa a un objeto Usuario.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idUsuario|PathParam|ID del objeto Usuario al cual se asociará el objeto Empresa|Sí|Integer
body|body|Objeto Empresa que será asociado al objeto Usuario indicado|Sí|[Representación Minimum](#recurso-empresa)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Objeto Empresa asociado al usuario indicado|[Representación Minimum de Empresa](#recurso-empresa)
500|No se pudo asociar el objeto Empresa|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### PUT /usuarios/{idUsuario}/empresa

Es el encargado de remplazar el objeto Empresa asociada a un objeto Usuario.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idUsuario|Path|ID del objeto Usuario cuya empresa será reemplazada|Sí|Integer
body|body|Nuevo objeto Empresa que será asociado|Sí|[Representación Minimum](#recurso-empresa)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Se remplazó la empresa|Objeto Empresa en [Representación Minimum](#recurso-empresa)
500|No se pudo remplazar la empresa|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

#### DELETE /usuarios/{idUsuario}/empresa

Remueve un objeto Empresa asociado a un objeto Usuario.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
idUsuario|Path|ID del objeto Usuario asociado al objeto Empresa|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto removido|N/A
500|Error interno|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error

### Recurso Multimedia
El objeto Multimedia tiene 2 representaciones JSON:	

#### Representación Minimum
```javascript
{
    nombre: '' /*Tipo String*/,
    link: '' /*Tipo String*/,
    descripcion: '' /*Tipo String*/
}
```
#### Representación Detail
```javascript
{
    // todo lo de la representación Minimum más los objetos Minimum con relación simple.
	
}
```
#### GET /noticias/{id}/multimedia/{link}

Retorna una colección de objetos Multimedia en representación Detail. El id es una concatenación de la siguiente forma :" nombreNoticia-nombreAutor", y el link es el enlace que tiene asignado el objeto multimedia.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Noticia a consultar|Sí|String
link|Path|Link del objeto Multimedia a consultar|Sí|String

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Multimedia en [representaciones Detail](#recurso-multimedia)
404|No existe un objeto Multimedia con el link solicitado dentro de esa Noticia|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

#### PUT /noticias/{id}/multimedia/{link}

Es el encargado de actualizar objetos Multimedia en una Noticia. El id es una concatenación de la siguiente forma:" nombreNoticia-nombreAutor", y el link es el enlace que posee el archive multimedia.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Noticia a actualizar|Sí|String
link|Path|Link del objeto Multimedia a consultar|Sí|String
body|body|Objeto Multimedia nuevo|Sí|[Representación Detail](#recurso-multimedia)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Multimedia actualizado|[Representación Detail](#recurso-noticia)
412|business exception, no se cumple con las reglas de negocio|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo actualizar el objeto Multimedia de la Noticia seleccionada|Mensaje de error

#### DELETE /noticias/{id}/multimedia/{link}

Es el encargado de borrar objetos Multimedia en una Noticia. El id es una concatenación de la siguiente forma:" nombreNoticia-nombreAutor", y el link es el enlace que posee el archive multimedia.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Noticia a actualizar|Sí|String
link|Path|Link del objeto Multimedia a consultar|Sí|String
body|body|Objeto Multimedia nuevo|Sí|[Representación Detail](#recurso-multimedia)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|N/A
412|business exception, no se cumple con las reglas de negocio|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo actualizar el objeto Multimedia de la Noticia seleccionada|Mensaje de error


#### POST /noticias/{id}/multimedia

Es el encargado de poner objetos Multimedia en una Noticia. El id es una concatenación de la siguiente forma:" nombreNoticia-nombreAutor".

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Noticia a poner un nuevo valor|Sí|String
link|Path|Link del objeto Multimedia a consultar|Sí|String
body|body|Objeto Multimedia nuevo|Sí|[Representación Detail](#recurso-multimedia)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Multimedia actualizado|[Representación Detail](#recurso-noticia)
412|business exception, no se cumple con las reglas de negocio|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|No se pudo actualizar el objeto Multimedia de la Noticia seleccionada|Mensaje de error

### GET /grupos/{grupoid}/blogs/{blogid}/multimedia/{link}
Retorna un objeto Multimedia en un determinado Blog.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del objeto Grupo donde se encuentra el Blog |Sí|Integer
blogid|Path|ID del objeto Blog donde se encuentra el objeto Multimedia|Sí|Integer
link|Path|Link del objeto Multimedia|Sí|Integer

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Multimedia en [representación Detail](#recurso-multimedia)
404|not found, no existe el recurso solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error


### POST /grupos/{grupoid}/blogs/{blogid}/multimedia
Es el encargado de crear los objetos Multimedia de un Blog

#### Parámetros 
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del objeto Grupo donde se encuentra el Blog |Sí|Integer
blogid|Path|ID del objeto Blog|Sí|Integer
body|body|Objeto Multimedia que será creado|Sí|representación Detail de Multimedia


#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Multimedia creado en [representación Detail](#recurso-multimedia)
404|not found, no existe un objeto Grupo con el ID solicitado o no existe un objeto Blog con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

### PUT /grupos/{grupoid}/blogs/{blogid}/multimedia/{link}
Es el encargado de actualizar los objetos Multimedia de un Blog de un Grupo.

#### Parámetros 
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del objeto Grupo donde se encuentra el Blog |Sí|Integer
blogid|Path|ID del objeto Blog|Sí|Integer
link|Path|Link del objeto Multimedia|Sí|String
body|body|Objeto Multimedia que será creado|Sí|representación Detail de Multimedia


#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Multimedia actualizado en [representación Detail](#recurso-multimedia)
404|not found, no existe un objeto Grupo con el ID solicitado o no existe un objeto Blog con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

### DELETE /grupos/{grupoid}/blogs/{blogid}/multimedia/{link}
Es el encargado de eliminar los objetos Multimedia de un Blog de un Grupo.

#### Parámetros 
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del objeto Grupo donde se encuentra el Blog |Sí|Integer
blogid|Path|ID del objeto Blog|Sí|Integer
link|Path|Link del objeto Multimedia|Sí|String
body|body|Objeto Multimedia que será creado|Sí|representación Detail de Multimedia


#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|N/A
404|not found, no existe un objeto Grupo con el ID solicitado o no existe un objeto Blog con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error



### GET /grupos/{grupoid}/noticias/{noticiaid}/multimedia/{link}
Retorna un objeto Multimedia en una Noticia de un Grupo.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del objeto Grupo donde se encuentra el Blog |Sí|String
noticiaid|Path|ID del objeto Noticia donde se encuentra el objeto Multimedia|Sí|String
link|Path|Link del objeto Multimedia|Sí|String

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Multimedia en [representación Detail](#recurso-multimedia)
404|not found, no existe el recurso solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error


### POST /grupos/{grupoid}/noticias/{noticiaid}/multimedia
Es el encargado de crear los objetos Multimedia de una Noticia de un Grupo.

#### Parámetros 
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del objeto Grupo donde se encuentra el Blog |Sí|String
noticiaid|Path|ID del objeto Noticia|Sí|String
body|body|Objeto Multimedia que será creado|Sí|representación Detail de Multimedia


#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Multimedia creado en [representación Detail](#recurso-multimedia)
404|not found, no existe un objeto Grupo con el ID solicitado o no existe un objeto Noticia con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

### PUT /grupos/{grupoid}/noticias/{noticiaid}/multimedia/{link}
Es el encargado de actualizar los objetos Multimedia de una Noticia de un Grupo.
#### Parámetros 
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del objeto Grupo donde se encuentra el Blog |Sí|String
noticiaid|Path|ID del objeto Noticia|Sí|String
link|Path|Link del objeto Multimedia|Sí|String
body|body|Objeto Multimedia que será creado|Sí|representación Detail de Multimedia


#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Multimedia actualizado en [representación Detail](#recurso-multimedia)
404|not found, no existe un objeto Grupo con el ID solicitado o no existe un objeto Noticia con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

### PUT /grupos/{grupoid}/noticias/{noticiaid}/multimedia/{link}
Es el encargado de borrar los objetos Multimedia de una Noticia de un Grupo.
#### Parámetros 
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del objeto Grupo donde se encuentra el Blog |Sí|String
noticiaid|Path|ID del objeto Noticia|Sí|String
link|Path|Link del objeto Multimedia|Sí|String
body|body|Objeto Multimedia que será creado|Sí|representación Detail de Multimedia


#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|N/A
404|not found, no existe un objeto Grupo con el ID solicitado o no existe un objeto Noticia con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error



### GET /usuarios/{usuarioid}/noticias/{noticiaid}/multimedia/{link}
Retorna un objeto Multimedia en una determinada Noticia de un Usuario.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
usuarioid|Path|ID del objeto Usuario autor de la Noticia |Sí|Integer
noticiaid|Path|ID del objeto Noticia donde se encuentra el objeto Multimedia|Sí|String
link|Path|Link del objeto Multimedia|Sí|String

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Multimedia en [representación Detail](#recurso-multimedia)
404|not found, no existe el recurso solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error


### POST /usuarios/{usuarioid}/noticias/{noticiaid}/multimedia
Es el encargado de crear los objetos Multimedia de una Noticia de un Usuario

#### Parámetros 
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
usuarioid|Path|ID del objeto Grupo donde se encuentra el Blog |Sí|Integer
noticiaid|Path|ID del objeto Blog|Sí|String
body|body|Objeto Multimedia que será creado|Sí|representación Detail de Multimedia


#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Multimedia creado en [representación Detail](#recurso-multimedia)
404|not found, no existe un objeto Usuario con el ID solicitado o no existe un objeto Noticia con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

### PUT /usuarios/{usuarioid}/noticias/{noticiaid}/multimedia/{link}
Es el encargado de actualizar los objetos Multimedia de una Noticia escrita por un Usuario

#### Parámetros 
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
usuarioid|Path|ID del objeto Usuario autor de la Noticia |Sí|Integer
noticiaid|Path|ID del objeto Noticia|Sí|Integer
link|Path|Link del objeto Multimedia|Sí|String
body|body|Objeto Multimedia que será creado|Sí|representación Detail de Multimedia


#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Multimedia actualizado en [representación Detail](#recurso-multimedia)
404|not found, no existe un objeto Usuario con el ID solicitado o no existe un objeto Noticia con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

### DELETE /usuarios/{usuarioid}/noticias/{noticiaid}/multimedia/{link}
Es el encargado de borrar los objetos Multimedia de una noticia de un Usuario

#### Parámetros 
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
usuarioid|Path|ID del objeto Usuario autor de la Noticia |Sí|Integer
noticiaid|Path|ID del objeto Noticia|Sí|Integer
link|Path|Link del objeto Multimedia|Sí|String
body|body|Objeto Multimedia que será creado|Sí|representación Detail de Multimedia


#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|N/A
404|not found, no existe un objeto Usuario con el ID solicitado o no existe un objeto Noticia con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

## Recurso Comentario
El recurso Comentario tiene dos representaciones JSON:
### Representación Minimum
```javascript
{
    id: '' /*Tipo Long*/,
    nombreCiudadano: '' /*Tipo String*/,
    comentario: '' /*Tipo String*/
}
```
### Representación Detail
```javascript
{
    id: '' /*Tipo Long*/,
    nombreCiudadano: '' /*Tipo String*/,
    comentario: '' /*Tipo String*/
}
```

### GET /grupos/{grupoid}/blogs/{blogid}/comentarios
Retorna la colección de objetos Comentario de un Blog.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del grupo|Sí|Integer
blogid|Path|ID del blog|Sí|Integer

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de objetos Comentario en [representación Detail](#recurso-comentario)
404|not found, no existe un objeto Grupo con el ID solicitado o no existe un objeto Blog con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

### GET /grupos/{grupoid}/blogs/{blogid}/comentarios/{comentarioid}
Retorna el objeto Comentario de un Blog.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del grupo|Sí|Integer
blogid|Path|ID del blog|Sí|Integer
comentarioid|Path|ID del Comentario|Sí|Integer

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|objeto Comentario en [representación Detail](#recurso-comentario)
404|not found, no existe un objeto Grupo con el ID solicitado, un objeto Blog con el ID solicitado, o un comentario con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

### POST /grupos/{grupoid}/blogs/{blogid}/comentarios
Es el encargado de crear objetos Comentario en un Blog.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del grupo|Sí|Integer
blogid|Path|ID del blog|Sí|Integer
body|body|Objeto Comentario que será creado|Sí|[Representación Detail](#recurso-comentario)

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Comentario ha sido creado|[Representación Detail](#recurso-comentario)
404|not found, no existe un objeto Grupo con el ID solicitado o un objeto Blog con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
500|Error interno|Mensaje de error

### PUT /grupos/{grupoid}/blogs/{blogid}/comentarios/{comentarioid}
Es el encargado de actualizar objetos Comentario en un Blog.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del grupo|Sí|Integer
blogid|Path|ID del blog|Sí|Integer
comentarioid|Path|ID del comentario a actualizar|Sí|Integer
body|body|Objeto Comentario que será actualizado|Sí|[Representación Detail](#recurso-comentario)

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Comentario ha sido actualizado|[Representación Detail](#recurso-comentario)
404|not found, no existe un objeto Grupo con el ID solicitado, un objeto Blog con el ID solicitado o un objeto Comentario con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
500|Error interno|Mensaje de error

### DELETE /grupos/{grupoid}/blogs/{blogid}/comentarios/{comentarioid}
Es el encargado de eliminar objetos Comentario en un Blog.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del objeto Grupo|Sí|Integer
blogid|Path|ID del objeto Blog|Sí|Integer
comentarioid|Path|ID del objeto Comentario a eliminar|Sí|Integer

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
404|not found, no existe un objeto Grupo con el ID solicitado, un objeto Blog con el ID solicitado, o un objeto Comentario con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error


### GET /grupos/{grupoid}/noticias/{noticiaid}/comentarios
Retorna la colección de objetos Comentario de una Noticia.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del Grupo|Sí|Integer
noticiaid|Path|ID de la Noticia|Sí|Integer

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de objetos Comentario en [representación Detail](#recurso-comentario)
404|not found, no existe un objeto Grupo con el ID solicitado o no existe un objeto Noticia con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

### GET /grupos/{grupoid}/noticias/{noticiaid}/comentarios/{comentarioid}
Retorna el objeto Comentario de una Noticia.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del Grupo|Sí|Integer
noticiaid|Path|ID de la Noticia|Sí|Integer
comentarioid|Path|ID del Comentario|Sí|Integer

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
200|OK|objeto Comentario en [representación Detail](#recurso-comentario)
404|not found, no existe un objeto Grupo con el ID solicitado, un objeto Noticia con el ID solicitado, o un comentario con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error

### POST /grupos/{grupoid}/noticias/{noticiaid}/comentarios
Es el encargado de crear objetos Comentario en una Noticia.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del Grupo|Sí|Integer
noticiaid|Path|ID de la Noticia|Sí|Integer
body|body|Objeto Comentario que será creado|Sí|[Representación Detail](#recurso-comentario)

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Comentario ha sido creado|[Representación Detail](#recurso-comentario)
404|not found, no existe un objeto Grupo con el ID solicitado o un objeto Noticia con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
500|Error interno|Mensaje de error

### PUT /grupos/{grupoid}/noticias/{noticiaid}/comentarios/{comentarioid}
Es el encargado de actualizar objetos Comentario en una Noticia.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del Grupo|Sí|Integer
noticiaid|Path|ID de la Noticia|Sí|Integer
comentarioid|Path|ID del comentario a actualizar|Sí|Integer
body|body|Objeto Comentario que será actualizado|Sí|[Representación Detail](#recurso-comentario)

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Comentario ha sido actualizado|[Representación Detail](#recurso-comentario)
404|not found, no existe un objeto Grupo con el ID solicitado, un objeto Noticia con el ID solicitado o un objeto Comentario con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
412|precondition failed, no se cumple la regla de negocio establecida|Mensaje de error
500|Error interno|Mensaje de error

### DELETE /grupos/{grupoid}/noticias/{noticiaid}/comentarios/{comentarioid}
Es el encargado de eliminar objetos Comentario en una Noticia.

#### Parámetros
Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
grupoid|Path|ID del objeto Grupo|Sí|Integer
noticiaid|Path|ID del objeto Noticia|Sí|Integer
comentarioid|Path|ID del objeto Comentario a eliminar|Sí|Integer

#### Respuesta
Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
404|not found, no existe un objeto Grupo con el ID solicitado, un objeto Noticia con el ID solicitado, o un objeto Comentario con el ID solicitado|Mensaje de error
405|method not allowed, no existe permiso para el recurso|Mensaje de error
500|Error interno|Mensaje de error
