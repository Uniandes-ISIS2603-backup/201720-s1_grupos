/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;


import co.edu.uniandes.csw.grupos.entities.CategoriaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
/**
 * Persistencia de categoría
 * @author cm.sarmiento10
 */
@Stateless
public class CategoriaPersistence {
    /**
     * Entity manager
     */
 @PersistenceContext(unitName = "gruposPU")
    protected EntityManager em;

    /**
     *
     * @param entity objeto Categoria que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public CategoriaEntity create(CategoriaEntity entity) {

        /* Note que hacemos uso de un método propio de EntityManager para persistir la Categoria en la base de datos.
        Es similar a "INSERT INTO table_codigo (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(entity);
     
        return entity;
    }

    /**
     * Actualiza un Categoria.
     *
     * @param entity: la Categoria que viene con los nuevos cambios. Por ejemplo
     * el codigo pudo cambiar. En ese caso, se haria uso del método update.
     * @return un Categoria con los cambios aplicados.
     */
    public CategoriaEntity update(CategoriaEntity entity) {
       
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la Categoria con los cambios, esto es similar a 
        "UPDATE table_codigo SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        return em.merge(entity);
    }

    /**
     *
     * Borra un Categoria de la base de datos recibiendo como argumento el id
     * de la Categoria
     *
     * @param id: id correspondiente a la Categoria a borrar.
     */
    public void delete(Long id) {
       
        // Se hace uso de mismo método que esta explicado en public CategoriaEntity find(Long id) para obtener la Categoria a borrar.
        CategoriaEntity entity = em.find(CategoriaEntity.class, id);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from CategoriaEntity where id=id;" - "DELETE FROM table_codigo WHERE condition;" en SQL.*/
        em.remove(entity);
    }

    /**
     * Busca si hay algun Categoria con el id que se envía de argumento
     *
     * @param id: id correspondiente a la Categoria buscada.
     * @return un Categoria.
     */
    public CategoriaEntity find(Long id) {
       
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from CategoriaEntity where id=id;" - "SELECT * FROM table_codigo WHERE condition;" en SQL.
         */
        return em.find(CategoriaEntity.class, id);
    }

    /**
     * Devuelve todas las Categoriaes de la base de datos.
     *
     * @return una lista con todas las Categorias que encuentre en la base de
     * datos, "select u from CategoriaEntity u" es como un "select * from
     * CategoriaEntity;" - "SELECT * FROM table_codigo" en SQL.
     */
    public List<CategoriaEntity> findAll() {
      
        // Se crea un query para buscar todas las Categorias en la base de datos.
        TypedQuery query = em.createQuery("select u from CategoriaEntity u", CategoriaEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de Categoriaes.
        return query.getResultList();
    }

    /**
     * Busca si hay algun Categoria con el nombre que se envía de argumento
     *
     * @param address: dirección de la Categoria que se está buscando
     * @return null si no existe ningun Categoria con la dirección del argumento.
     * Si existe alguna devuelve la primera.
     */
    public CategoriaEntity findByTipo(String address) {
      

        // Se crea un query para buscar Categorias con la dirección que recibe el método como argumento. ":address" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From CategoriaEntity e where e.tipo = :name", CategoriaEntity.class);
        // Se remplaza el placeholder ":cancion" con el valor del argumento 
        query = query.setParameter("name", address);
        // Se invoca el query se obtiene la lista resultado
        List<CategoriaEntity> sameName = query.getResultList();
        if (sameName.isEmpty()) {
            return null;
        } else {
            return sameName.get(0);
        }
    }
}

