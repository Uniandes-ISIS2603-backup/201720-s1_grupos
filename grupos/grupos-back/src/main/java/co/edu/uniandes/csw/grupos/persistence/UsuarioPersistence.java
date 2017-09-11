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
 *
 * @author tefa
 */
@Stateless
public class UsuarioPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(PatrocinioPersistence.class.getName());
   @PersistenceContext(unitName = "gruposPU")

    protected EntityManager em;
   
   public UsuarioEntity createEntity(UsuarioEntity e)
   {
       LOGGER.info("Creando objeto "+e.getId());
       em.persist(e);
       LOGGER.info("Éxito en creación");
       return e;
   }
   
   public UsuarioEntity updateEntity(UsuarioEntity e)
   {
       LOGGER.info("Actualizando entidad "+e.getId());
       return em.merge(e);
   }
   
   public UsuarioEntity find(Long id)
   {
       LOGGER.info("Buscando "+id);
       return em.find(UsuarioEntity.class, id);
   }
   
   public UsuarioEntity findByName(String nom)
   {
       LOGGER.info("Buscando "+nom);
       TypedQuery q= em.createQuery("Select u from UsuarioEntity u where u.nombre =:nom", UsuarioEntity.class );
       q.setParameter("nom", nom);
       return (UsuarioEntity) q.getSingleResult(); 
   }
   
   public List<UsuarioEntity> findAll()
   {
       LOGGER.info("Buscando a todos...");
       TypedQuery q =em.createQuery("Select u from UsuarioEntity u",UsuarioEntity.class);
       return q.getResultList();
   }
   
   public void delete(Long id)
   {
       UsuarioEntity e=em.find(UsuarioEntity.class, id);
       LOGGER.info("Borrando "+id+" con un objeto que existe");
       em.remove(e);
       
   }
    
}
