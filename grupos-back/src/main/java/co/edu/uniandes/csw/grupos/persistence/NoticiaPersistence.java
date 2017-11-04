/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * Persistencia de la noticia.<br>
 * @author s.guzmanm
 */
@Stateless
public class NoticiaPersistence {
    
    /**
     * Logger de la persistencia.
     */
       private static final Logger LOGGER = Logger.getLogger(NoticiaPersistence.class.getName());
   
       /**
    * Manejador de entidades.
    */
       @PersistenceContext(unitName = "gruposPU")
    protected EntityManager em;
   /**
    * Crea una nueva entidad en la base de datos.<br>
    * @param e Noticia a crear.<br>
    * @return Noticia creada.
    */

   public NoticiaEntity createEntity(NoticiaEntity e)
   {
       LOGGER.info("Creando objeto "+e.getTitulo()+" ");
       em.persist(e);
       LOGGER.info("Éxito en creación");
       return e;
   }

   /**
    * Actualiza la noticia en la base de datos creada.<br>
    * @param e Noticia a actualizar.<br>
    * @return Noticia actualizada
    */
   public NoticiaEntity updateEntity(NoticiaEntity e)
   {
       LOGGER.info("Actualizando entidad "+e.getTitulo()+" ");
       return em.merge(e);
   }

   /**
    * Encuentra la noticia con ese id.<br>
    * @param id Id de la noticia a encontrar.<br>
    * @return Noticia con el id dado.
    */

   public NoticiaEntity find(Long id)
   {
       LOGGER.info("Buscando "+id);
       return em.find(NoticiaEntity.class, id);
   }   

   /**
    * Encuentra todas las noticias del sistema.<br>
    * @return Todas las noticias de la base.
    */
   public List<NoticiaEntity> findAll()
   {
       try
       {
           LOGGER.info("Buscando a todos...");
       TypedQuery q =em.createQuery("Select x from NoticiaEntity x",NoticiaEntity.class);
       return q.getResultList();
       }
       catch (Exception e) {return new ArrayList<>();}
       
   }
   /**
    * Borra la noticia con el id dado.<br>
    * @param id Id de la noticia a borrar.
    */

   public void delete(Long id)
   {
       NoticiaEntity    e=em.find(NoticiaEntity.class, id);
       LOGGER.info("Borrando "+id+" con un objeto que existe");
       em.remove(e);
       
   }
  
    

}
