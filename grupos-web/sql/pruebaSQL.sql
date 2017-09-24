delete from GrupoEntity_NoticiaEntity;
delete from GrupoEntity;
delete from CategoriaEntity;
delete from NoticiaEntity_MultimediaEntity;
delete from NoticiaEntity;
delete from MultimediaEntity;
delete from CalificacionEntity; 
delete from BlogEntity;
delete from UsuarioEntity;

insert into GrupoEntity (id, nombre, descripcion) values (10,'GrupoPrueba1', 'Este grupo es el numero uno');
insert into GrupoEntity (id, nombre, descripcion) values (11,'GrupoPrueba2', 'Este grupo es el numero dos');

insert into CategoriaEntity (id, tipo, descripcion, rutaIcono) values (10,'Videojuegos', 'La mejor categoria', 'videojuegos.png');
insert into CategoriaEntity (id, tipo, descripcion, rutaIcono) values (11,'Ciencia', 'La segunda mejor categoria', 'ciencia.png');


--INFORMACIÖN USUARIO
insert into UsuarioEntity(id, nombre, apellido, password,email) values (1,'Sergio','Guzmán','hola','hola@uniandes.edu.co');
insert into UsuarioEntity(id, nombre, apellido, password,email) values (11,'DE','Rd','Hola','xd@uniandes.edu.co');

--INFORMACIÓN BLOG
insert into BlogEntity (id,titulo,contenido) values (1,'Blog','Contenido');
insert into BlogEntity (id,titulo,contenido) values (2,'B','Content');
insert into BlogEntity(id,titulo,contenido) values (3,'¿Cómo hago el punto 10 de SQL?','No sé');
insert into BlogEntity(id,titulo,contenido) values (4,'HOLA','Qué hace');

--INFORMACIÓN MULTIMEDIA
insert into MultimediaEntity (nombre,descripcion,link) values ('GATO','G','abc');
insert into MultimediaEntity (nombre,descripcion,link) values ('PERRO','G','dfdf');


--INFORMACIÓN NOTICIA
insert into NoticiaEntity (id,titulo,informacion,autor_id) values (1,'Titulo','Info',1);
insert into NoticiaEntity (id,titulo,informacion,autor_id) values (2,'Tit3lo','Irfo',11);

--INFORMACION NOTICIAENTITY_MULTIMEDIAENTITY
insert into NoticiaEntity_MultimediaEntity(noticiaentity_id,multimedia_link) values (2,'abc');
insert into NoticiaEntity_MultimediaEntity(noticiaentity_id,multimedia_link) values (2,'dfdf');
insert into NoticiaEntity_MultimediaEntity(noticiaentity_id,multimedia_link) values (1,'abc');


