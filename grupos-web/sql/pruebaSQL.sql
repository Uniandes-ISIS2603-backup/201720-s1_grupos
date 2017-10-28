delete from GrupoEntity_NoticiaEntity;
delete from GrupoEntity_CategoriaEntity;
delete from GrupoEntity_EventoEntity;
delete from Usuario_Admin;
delete from Usuario_Grupo;
delete from UsuarioEntity_BlogEntity;
delete from UsuarioEntity_EventoEntity;
delete from UsuarioEntity_TarjetaEntity;

delete from BlogEntity_MultimediaEntity;
delete from BlogEntity_ComentarioEntity;
delete from EventoEntity_PatrocinioEntity;
delete from PatrocinioEntity;

delete from NoticiaEntity_ComentarioEntity;
delete from CalificacionEntity; 
delete from BlogEntity;
delete from EventoEntity;

delete from GrupoEntity;
delete from CategoriaEntity;
delete from NoticiaEntity_MultimediaEntity;

delete from ComentarioEntity;
delete from CalificacionEntity;
delete from NoticiaEntity;
delete from ComentarioEntity;
delete from MultimediaEntity;
delete from TarjetaEntity;
delete from PatrocinioEntity;
delete from UsuarioEntity;
delete from EmpresaEntity;
delete from LugarEntity;

ALTER TABLE NoticiaEntity ALTER COLUMN id RESTART WITH 5;
ALTER TABLE CategoriaEntity ALTER COLUMN id RESTART WITH 13;
ALTER TABLE UsuarioEntity ALTER COLUMN id RESTART WITH 12;
ALTER TABLE PatrocinioEntity ALTER COLUMN id RESTART WITH 4;
ALTER TABLE GrupoEntity ALTER COLUMN id RESTART WITH 13;
ALTER TABLE BlogEntity ALTER COLUMN id RESTART WITH 5;
ALTER TABLE CalificacionEntity ALTER COLUMN id RESTART WITH 2;
ALTER TABLE LugarEntity ALTER COLUMN id RESTART WITH 4;
ALTER TABLE EventoEntity ALTER COLUMN id RESTART WITH 4;
ALTER TABLE ComentarioEntity ALTER COLUMN id RESTART WITH 5;



insert into CategoriaEntity (id, tipo, descripcion, rutaIcono) values (10,'Videojuegos', 'La mejor categoria', 'videojuegos.png');
insert into CategoriaEntity (id, tipo, descripcion, rutaIcono) values (11,'Ciencia', 'La segunda mejor categoria', 'ciencia.png');
insert into CategoriaEntity (id, tipo, descripcion, rutaIcono) values (12,'CategoriaVacia', 'Esta categoria está vacía', 'vacia.png');


insert into TarjetaEntity(numero, banco, dinerodisponible, maxcupo) values (1,'Caja social',100,1000);
insert into TarjetaEntity(numero, banco, dinerodisponible, maxcupo) values (2,'Caja social',102,1200);
insert into TarjetaEntity(numero, banco, dinerodisponible, maxcupo) values (3,'Caja social',120,1300);
insert into TarjetaEntity(numero, banco, dinerodisponible, maxcupo) values (4,'Caja social',130,1400);
insert into TarjetaEntity(numero, banco, dinerodisponible, maxcupo) values (5,'Caja social',140,1100);

insert into EmpresaEntity(nit,logo,nombre) values (1,'hola.png','Hola');
insert into EmpresaEntity(nit,logo,nombre) values (2,'empresa.png','Empresa');

--INFORMACIÖN USUARIO
insert into UsuarioEntity(id, nombre, apellido, password,email) values (1,'Sergio','Guzmán','hola','hola@uniandes.edu.co');
insert into UsuarioEntity(id, nombre, apellido, password,email) values (11,'DE','Rd','Hola','xd@uniandes.edu.co');
insert into UsuarioEntity(id, nombre, apellido, password,email,empresa_nit) values (3,'DE','Rd','Hola','x@uniandes.edu.co',1);
insert into UsuarioEntity(id, nombre, apellido, password,email,empresa_nit) values (2,'DE','Rd','Hola','d@uniandes.edu.co',2);
insert into UsuarioEntity(id, nombre, apellido, password,email,empresa_nit) values (4,'UsuarioParaGrupo','Rd','Hola','xp@uniandes.edu.co',null);

--INFORMACIÓN USUARIO TARJETA

insert into UsuarioEntity_TarjetaEntity(UsuarioEntity_Id, Tarjetas_numero) values (2,1);
insert into UsuarioEntity_TarjetaEntity(UsuarioEntity_Id, Tarjetas_numero) values (2,2);
insert into UsuarioEntity_TarjetaEntity(UsuarioEntity_Id, Tarjetas_numero) values (3,3);
insert into UsuarioEntity_TarjetaEntity(UsuarioEntity_Id, Tarjetas_numero) values (3,4);
insert into UsuarioEntity_TarjetaEntity(UsuarioEntity_Id, Tarjetas_numero) values (3,5);


--INFORMACIÓN PATROCINIO
insert into PatrocinioEntity(Id, Pago, usuario_Id) values (1,800.0,3);
insert into PatrocinioEntity(Id, Pago, usuario_Id) values (2,800.0,2);
insert into PatrocinioEntity(Id, Pago, usuario_Id) values (3,800.0,2);

--INFORMACIÓN BLOG



insert into GrupoEntity (id, nombre, descripcion) values (10,'GrupoPrueba1', 'Este grupo es el numero uno');
insert into GrupoEntity (id, nombre, descripcion) values (11,'GrupoPrueba2', 'Este grupo es el numero dos');
insert into GrupoEntity (id, nombre, descripcion) values (12,'GrupoVacio', 'Este grupo está vacío');


insert into BlogEntity (id,titulo,contenido,promedio,grupo_id) values (1,'Blog','Contenido',0,10);
insert into BlogEntity (id,titulo,contenido,promedio,grupo_id) values (2,'B','Content',0,10);
insert into BlogEntity(id,titulo,contenido,promedio,grupo_id) values (3,'¿Cómo hago el punto 10 de SQL?','No sé',0,11);
insert into BlogEntity(id,titulo,contenido,promedio,grupo_id) values (4,'HOLA','Qué hace',0,11);




--INFORMACIÓN LUGAR
insert into LugarEntity(id,capacidad,direccion,nombre) values(1,20,'Dirección','Lugar');
insert into LugarEntity (id,capacidad,direccion,nombre) values (2,30,'Dirección1','Lugar');
insert into LugarEntity (id,capacidad,direccion,nombre) values (3,40,'Dirección2','Lugar');
--INFORMACiÓN EVENTO
insert into EventoEntity(id,fechafin,fechainicio,nombre,grupo_id,lugar_id)values(1,'2017-08-07','2017-08-08','Evento',10,1);
insert into EventoEntity(id,fechafin,fechainicio,nombre,grupo_id,lugar_id)values(2,'2017-08-08','2017-08-09','Evento',10,2);
insert into EventoEntity(id,fechafin,fechainicio,nombre,grupo_id,lugar_id)values(3,'2017-08-09','2017-08-10','Evento',11,3);





---INSERCIÓN SEGURA DE USUARIO, GRUPO Y BLOG FICTICIOS QUE NO SE VAN A BORRAR EN LAS PRUEBAS DE SERGIO GUZMÁN M.

insert into UsuarioEntity(id, nombre, apellido, password,email) values (1000000,'Sergio','Guzmán','hola','sergio@uniandes.edu.co');
insert into GrupoEntity (id, nombre, descripcion) values (1000000,'GrupoPrueba', 'Este grupo es el numero millón');
insert into BlogEntity (id,titulo,contenido,promedio,grupo_id) values (1000000,'Blog','Contenido',0,1000000);

--INFORMACIÓN CALIFICACIÓN
insert into CalificacionEntity(id,calificacion,fecha,blog_id,calificador_id) values (1,2.0,'2017-09-20',1000000,1000000);

--INFORMACIÓN MULTIMEDIA
insert into MultimediaEntity (nombre,descripcion,link) values ('GATO','G','abc');
insert into MultimediaEntity (nombre,descripcion,link) values ('PERRO','G','dfdf');
insert into MultimediaEntity (nombre,descripcion,link) values ('HOLA','G','EEE');

--INFORMACIÓN NOTICIA
insert into NoticiaEntity (id,titulo,informacion,autor_id) values (1,'Titulo','Info',1);
insert into NoticiaEntity (id,titulo,informacion,autor_id) values (2,'Tit3lo','Irfo',11);
insert into NoticiaEntity (id,titulo,informacion,autor_id) values (3,'Nueva','Irfo',11);
insert into NoticiaEntity (id,titulo,informacion,autor_id) values (4,'New','Irfo',11);
insert into NoticiaEntity (id,titulo,informacion,autor_id) values (1000000,'Otra','Irfo',1000000);
insert into NoticiaEntity (id,titulo,informacion,autor_id) values (1000001,'Otra','Irfo',1000000);
insert into NoticiaEntity (id,titulo,informacion,autor_id) values (1000002,'Otra','Irfo',1000000);

--INFORMACIÓN COMENTARIO
insert into ComentarioEntity (id, autor, comentario) values (1, 'yo', 'hola');
insert into ComentarioEntity (id, autor, comentario) values (2, 'Sergio', 'solo milloz loks');
insert into ComentarioEntity (id, autor, comentario) values (3, 'yo', 'hola k ase');
insert into ComentarioEntity (id, autor, comentario) values (4, 'abc', 'qwerty');

--GRUPOENTITY_NOTICIAENTITY
insert into GrupoEntity_NoticiaEntity(grupoentity_id,noticiasgrupo_id) values (10,1);
insert into GrupoEntity_NoticiaEntity(grupoentity_id,noticiasgrupo_id) values (11,2);
insert into GrupoEntity_NoticiaEntity(grupoentity_id,noticiasgrupo_id) values (10,3);
insert into GrupoEntity_NoticiaEntity(grupoentity_id,noticiasgrupo_id) values (1000000,1000000);
insert into GrupoEntity_NoticiaEntity(grupoentity_id,noticiasgrupo_id) values (1000000,1000001);


--INFORMACION NOTICIAENTITY_MULTIMEDIAENTITY
insert into NoticiaEntity_MultimediaEntity(noticiaentity_id,multimedia_link) values (2,'abc');
insert into NoticiaEntity_MultimediaEntity(noticiaentity_id,multimedia_link) values (2,'dfdf');
insert into NoticiaEntity_MultimediaEntity(noticiaentity_id,multimedia_link) values (1,'abc');
insert into NoticiaEntity_MultimediaEntity(noticiaentity_id,multimedia_link) values (1000001,'abc');
insert into NoticiaEntity_MultimediaEntity(noticiaentity_id,multimedia_link) values (1000001,'dfdf');

--INFORMACIÓN BLOGENTITY_MULTIMEDIAENTITY
insert into BlogEntity_MultimediaEntity(blogentity_id,multimedia_link) values (1000000,'abc');
insert into BlogEntity_MultimediaEntity(blogentity_id,multimedia_link) values (1000000,'dfdf');
insert into BlogEntity_MultimediaEntity(blogentity_id,multimedia_link) values (1,'abc');
insert into BlogEntity_MultimediaEntity(blogentity_id,multimedia_link) values (2,'abc');
insert into BlogEntity_MultimediaEntity(blogentity_id,multimedia_link) values (2,'dfdf');


insert into UsuarioEntity_Eventoentity(usuarios_id,eventos_id) values (11,2);
insert into UsuarioEntity_Eventoentity(usuarios_id,eventos_id) values (1,1);
insert into UsuarioEntity_Eventoentity(usuarios_id,eventos_id) values (2,3);
insert into UsuarioEntity_Eventoentity(usuarios_id,eventos_id) values (2,1);
insert into UsuarioEntity_Eventoentity(usuarios_id,eventos_id) values (2,2);


--INFORMACIÓN BLOGENTITY_COMENTARIOENTITY
insert into BlogEntity_ComentarioEntity (blogentity_id, comentarios_id) values (1, 1);
insert into BlogEntity_ComentarioEntity (blogentity_id, comentarios_id) values (1, 2);

--INFORMACIÓN NOTICIAENTITY_COMENTARIOENTITY
insert into NoticiaEntity_ComentarioEntity (noticiaentity_id, comentarios_id) values (1, 3);
insert into NoticiaEntity_ComentarioEntity (noticiaentity_id, comentarios_id) values (1, 4);