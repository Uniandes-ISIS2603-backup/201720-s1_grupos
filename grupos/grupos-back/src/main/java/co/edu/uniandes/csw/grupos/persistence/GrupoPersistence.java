/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
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
public class GrupoPersistence {
    
     @PersistenceContext(unitName = "gruposPU")
    protected EntityManager em;

    /**
     *
     * @param entity objeto Grupo que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public GrupoEntity create(GrupoEntity entity) {

        /* Note que hacemos uso de un método propio de EntityManager para persistir la Grupo en la base de datos.
        Es similar a "INSERT INTO table_codigo (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(entity);
     
        return entity;
    }

    /**
     * Actualiza un Grupo.
     *
     * @param entity: la Grupo que viene con los nuevos cambios. Por ejemplo
     * el codigo pudo cambiar. En ese caso, se haria uso del método update.
     * @return un Grupo con los cambios aplicados.
     */
    public GrupoEntity update(GrupoEntity entity) {
       
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la Grupo con los cambios, esto es similar a 
        "UPDATE table_codigo SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        return em.merge(entity);
    }

    /**
     *
     * Borra un Grupo de la base de datos recibiendo como argumento el id
     * de la Grupo
     *
     * @param id: id correspondiente a la Grupo a borrar.
     */
    public void delete(Long id) {
       
        // Se hace uso de mismo método que esta explicado en public GrupoEntity find(Long id) para obtener la Grupo a borrar.
        GrupoEntity entity = em.find(GrupoEntity.class, id);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from GrupoEntity where id=id;" - "DELETE FROM table_codigo WHERE condition;" en SQL.*/
        em.remove(entity);
    }

    /**
     * Busca si hay algun Grupo con el id que se envía de argumento
     *
     * @param id: id correspondiente a la Grupo buscada.
     * @return un Grupo.
     */
    public GrupoEntity find(Long id) {
       
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from GrupoEntity where id=id;" - "SELECT * FROM table_codigo WHERE condition;" en SQL.
         */
        return em.find(GrupoEntity.class, id);
    }

    /**
     * Devuelve todas las Grupoes de la base de datos.
     *
     * @return una lista con todas las Grupos que encuentre en la base de
     * datos, "select u from GrupoEntity u" es como un "select * from
     * GrupoEntity;" - "SELECT * FROM table_codigo" en SQL.
     */
    public List<GrupoEntity> findAll() {
      
        // Se crea un query para buscar todas las Grupos en la base de datos.
        TypedQuery query = em.createQuery("select u from GrupoEntity u", GrupoEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de Grupoes.
        return query.getResultList();
    }

    /**
     * Busca si hay algun Grupo con el nombre que se envía de argumento
     *
     * @param address: dirección de la Grupo que se está buscando
     * @return null si no existe ningun Grupo con la dirección del argumento.
     * Si existe alguna devuelve la primera.
     */
    public GrupoEntity findByNombre(String address) {
      

        // Se crea un query para buscar Grupos con la dirección que recibe el método como argumento. ":address" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From GrupoEntity e where e.nombre = :name", GrupoEntity.class);
        // Se remplaza el placeholder ":cancion" con el valor del argumento 
        query = query.setParameter("name", address);
        // Se invoca el query se obtiene la lista resultado
        List<GrupoEntity> sameName = query.getResultList();
        if (sameName.isEmpty()) {
            return null;
        } else {
            return sameName.get(0);
        }
    }
}
