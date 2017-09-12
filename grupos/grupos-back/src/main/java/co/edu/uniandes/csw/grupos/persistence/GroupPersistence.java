/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.GroupEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
/**
 *
 * @author cm.sarmiento10
 */
@Stateless
public class GroupPersistence {
    
     @PersistenceContext(unitName = "gruposPU")
    protected EntityManager em;

    /**
     *
     * @param entity objeto Group que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public GroupEntity create(GroupEntity entity) {

        /* Note que hacemos uso de un método propio de EntityManager para persistir la Group en la base de datos.
        Es similar a "INSERT INTO table_codigo (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(entity);
     
        return entity;
    }

    /**
     * Actualiza un Group.
     *
     * @param entity: la Group que viene con los nuevos cambios. Por ejemplo
     * el codigo pudo cambiar. En ese caso, se haria uso del método update.
     * @return un Group con los cambios aplicados.
     */
    public GroupEntity update(GroupEntity entity) {
       
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la Group con los cambios, esto es similar a 
        "UPDATE table_codigo SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        return em.merge(entity);
    }

    /**
     *
     * Borra un Group de la base de datos recibiendo como argumento el id
     * de la Group
     *
     * @param id: id correspondiente a la Group a borrar.
     */
    public void delete(Long id) {
       
        // Se hace uso de mismo método que esta explicado en public GroupEntity find(Long id) para obtener la Group a borrar.
        GroupEntity entity = em.find(GroupEntity.class, id);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from GroupEntity where id=id;" - "DELETE FROM table_codigo WHERE condition;" en SQL.*/
        em.remove(entity);
    }

    /**
     * Busca si hay algun Group con el id que se envía de argumento
     *
     * @param id: id correspondiente a la Group buscada.
     * @return un Group.
     */
    public GroupEntity find(Long id) {
       
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from GroupEntity where id=id;" - "SELECT * FROM table_codigo WHERE condition;" en SQL.
         */
        return em.find(GroupEntity.class, id);
    }

    /**
     * Devuelve todas las Groupes de la base de datos.
     *
     * @return una lista con todas las Groups que encuentre en la base de
     * datos, "select u from GroupEntity u" es como un "select * from
     * GroupEntity;" - "SELECT * FROM table_codigo" en SQL.
     */
    public List<GroupEntity> findAll() {
      
        // Se crea un query para buscar todas las Groups en la base de datos.
        TypedQuery query = em.createQuery("select u from GroupEntity u", GroupEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de Groupes.
        return query.getResultList();
    }

    /**
     * Busca si hay algun Group con el nombre que se envía de argumento
     *
     * @param address: dirección de la Group que se está buscando
     * @return null si no existe ningun Group con la dirección del argumento.
     * Si existe alguna devuelve la primera.
     */
    public GroupEntity findByNombre(String address) {
      

        // Se crea un query para buscar Groups con la dirección que recibe el método como argumento. ":address" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From GroupEntity e where e.nombre = :name", GroupEntity.class);
        // Se remplaza el placeholder ":cancion" con el valor del argumento 
        query = query.setParameter("name", address);
        // Se invoca el query se obtiene la lista resultado
        List<GroupEntity> sameName = query.getResultList();
        if (sameName.isEmpty()) {
            return null;
        } else {
            return sameName.get(0);
        }
    }
}
