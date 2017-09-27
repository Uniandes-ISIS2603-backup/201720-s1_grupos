/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Persistenceia de usuario
 * @author tefa
 */
@Stateless
public class UsuarioPersistence {
    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(UsuarioPersistence.class.getName());
   @PersistenceContext(unitName = "gruposPU")

   /**
    * Entity Manager
    */
    protected EntityManager em;
   
   /**
    * Crea una entidad de tipo usuario
    * @param e entidad tipo usuario
    * @return el usuario creado
    */
   public UsuarioEntity createEntity(UsuarioEntity e)
   {
       LOGGER.info("Creando objeto "+e.getId());
       em.persist(e);
       LOGGER.info("Éxito en creación");
       return e;
   }
   
   /**
    * Actualiza a un usuario
    * @param e entidad tipo usuario que se quiere actualizar
    * @return entidad actualizada
    */
   public UsuarioEntity updateEntity(UsuarioEntity e)
   {
       LOGGER.info("Actualizando entidad "+e.getId());
       return em.merge(e);
   }
   
   /**
    * Busca un usuario con el id dado por parámetro
    * @param id del usuario a buscar
    * @return el usuario buscado, en caso de no existir null
    */
   public UsuarioEntity find(Long id)
   {
       LOGGER.info("Buscando "+id);
       return em.find(UsuarioEntity.class, id);
   }
   
   /**
    * Busca un usuario por nombre
    * @param nom nombre del usuario que se quiere buscar
    * @return el usuario con nombre especificado
    */
   public UsuarioEntity findByName(String nom)
   {
       LOGGER.info("Buscando "+nom);
       TypedQuery q= em.createQuery("Select u from UsuarioEntity u where u.nombre =:nom", UsuarioEntity.class );
       q.setParameter("nom", nom);
       return (UsuarioEntity) q.getSingleResult(); 
   }
   
   /**
    * Busca todos los usuarios que estan registrados
    * @return Una lista con todos los usuarios
    */
   public List<UsuarioEntity> findAll()
   {
       LOGGER.info("Buscando a todos...");
       TypedQuery q =em.createQuery("Select u from UsuarioEntity u",UsuarioEntity.class);
       return q.getResultList();
   }
   
   /**
    * Elimina un usuario dado un id por parametro
    * @param id del usuario a eliminar.
    */
   public void delete(Long id)
   {
       UsuarioEntity e=em.find(UsuarioEntity.class, id);
       LOGGER.info("Borrando "+id+" con un objeto que existe");
       em.remove(e);
       
   }
    
}
