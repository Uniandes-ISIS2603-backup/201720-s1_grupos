delete from GrupoEntity;
delete from CategoriaEntity;

insert into GrupoEntity (id, nombre, descripcion) values (10,'GrupoPrueba1', 'Este grupo es el numero uno');
insert into GrupoEntity (id, nombre, descripcion) values (11,'GrupoPrueba2', 'Este grupo es el numero dos');

insert into CategoriaEntity (id, tipo, descripcion, rutaIcono) values (10,'Videojuegos', 'La mejor categoria', 'videojuegos.png');
insert into CategoriaEntity (id, tipo, descripcion, rutaIcono) values (11,'Ciencia', 'La segunda mejor categoria', 'ciencia.png');
