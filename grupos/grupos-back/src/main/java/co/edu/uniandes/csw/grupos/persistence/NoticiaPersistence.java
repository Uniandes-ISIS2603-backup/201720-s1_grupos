/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author s.guzmanm
 */
@Stateless
public class NoticiaPersistence {
       private static final Logger LOGGER = Logger.getLogger(NoticiaPersistence.class.getName());
   @PersistenceContext(unitName = "gruposPU")

    protected EntityManager em;
   
   public NoticiaEntity createEntity(NoticiaEntity e)
   {
       LOGGER.info("Creando objeto "+e.getTitulo()+" ");
       em.persist(e);
       LOGGER.info("Éxito en creación");
       return e;
   }
   
   public NoticiaEntity updateEntity(NoticiaEntity e)
   {
       LOGGER.info("Actualizando entidad "+e.getTitulo()+" ");
       return em.merge(e);
   }
   
   public NoticiaEntity find(Long id)
   {
       LOGGER.info("Buscando "+id);
       return em.find(NoticiaEntity.class, id);
   }
   
   public List<NoticiaEntity> findAll()
   {
       LOGGER.info("Buscando a todos...");
       TypedQuery q =em.createQuery("Select x from NoticiaEntity x",NoticiaEntity.class);
       return q.getResultList();
   }
   
   public void delete(Long id)
   {
       NoticiaEntity    e=em.find(NoticiaEntity.class, id);
       LOGGER.info("Borrando "+id+" con un objeto que existe");
       em.remove(e);
       
   }
    

}
