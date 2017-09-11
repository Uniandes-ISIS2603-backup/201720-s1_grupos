/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.TarjetaEntity;
import javax.ejb.Stateless;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author af.lopezf
 */
@Stateless
public class TarjetaPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(TarjetaPersistence.class.getName());

    @PersistenceContext(unitName = "gruposPU")
    protected EntityManager em;
    
    
    /**
     *
     * @param entity objeto tarjeta que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public TarjetaEntity create(TarjetaEntity entity) {
        em.persist(entity);
        return entity;
    }



    public List<TarjetaEntity> findAll() {
     
        TypedQuery query = em.createQuery("select u from TarjetaEntity u", TarjetaEntity.class);
        return query.getResultList();
    }
    
    public TarjetaEntity findByNumero(int numero){
        
        TypedQuery query = em.createQuery("Select e From TarjetaEntity e where e.numero = :numero", TarjetaEntity.class);
        
        query = query.setParameter("numero", numero);
        
        List<TarjetaEntity> sameNumero = query.getResultList();
        if (sameNumero.isEmpty()) {
            return null;
        } else {
            return sameNumero.get(0);
        }
        
    }
    
    
    public TarjetaEntity update(TarjetaEntity entity){
        return em.merge(entity);
    }
    
    
    public void delete(int numero){
        TarjetaEntity entity = em.find(TarjetaEntity.class, numero);
        em.remove(entity);
    }

    
}
