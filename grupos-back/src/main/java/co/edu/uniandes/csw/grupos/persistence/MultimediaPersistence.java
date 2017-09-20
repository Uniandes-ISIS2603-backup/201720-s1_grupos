/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Persistencia de la multimedia
 * @author s.guzmanm
 */
@Stateless
public class MultimediaPersistence {

    /**
     * Logger de la persistencia.
     */
       private static final Logger LOGGER = Logger.getLogger(MultimediaPersistence.class.getName());
       /**
        * Manejador de entidades.
        */
   @PersistenceContext(unitName = "gruposPU")
    protected EntityManager em;
   /**
    * Persite una nueva entidad dada por parámetro.<br>
    * @param e Nueva entidad.<br>
    * @return Entidad persistida.
    */

   public MultimediaEntity createEntity(MultimediaEntity e)
   {
       LOGGER.info("Creando objeto "+e.getLink());
       em.persist(e);
       LOGGER.info("Éxito en creación");
       return e;
   }

   /**
    * Actualiza la entidad con elv alor dado por parámetro.<br>
    * @param e Entidad a actualizar.<br>
    * @return Entidad actualizada.
    */

   public MultimediaEntity updateEntity(MultimediaEntity e)
   {
       LOGGER.info("Actualizando entidad "+e.getLink());
       return em.merge(e);
   }

   /**
    * Encuentra la multimedia con el id dado.<br>
    * @param id Id de la multimedia.<br>
    * @return Multimedia encontrada.
    */

   public MultimediaEntity find(String id)
   {
       LOGGER.info("Buscando "+id);
       return em.find(MultimediaEntity.class, id);
   }

   /**
    * Borra la multimedia con el id dado.<br>
    * @param id 
    */
   public void delete(String id)
   {
       MultimediaEntity e=em.find(MultimediaEntity.class, id);
       LOGGER.info("Borrando "+id+" con un objeto que existe");
       em.remove(e);
       
   }
    

}
