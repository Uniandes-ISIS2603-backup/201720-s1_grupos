/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.EmpresaEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Persistencia de la empresa
 * @author af.lopezf
 */
@Stateless
public class EmpresaPersistence {
    
    /**
     * Entity manager
     */
    @PersistenceContext(unitName = "gruposPU")
    protected EntityManager em;
    
    
    /**
     *
     * @param entity objeto empresa que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public EmpresaEntity create(EmpresaEntity entity) {
        em.persist(entity);
        return entity;
    }


    /**
     * Encuentra todas las empresas.<br>
     * @return Todas las empresas 
     */
    public List<EmpresaEntity> findAll() {
     
        TypedQuery query = em.createQuery("select u from EmpresaEntity u", EmpresaEntity.class);
        return query.getResultList();
    }
    /**
     * Encuentra por nit.<br>
     * @param nit Nit
     * @return Entidad de empresa.
     */
    public EmpresaEntity findByNit(int nit){
        
        TypedQuery query = em.createQuery("Select e From EmpresaEntity e where e.nit = :nit", EmpresaEntity.class);
        
        query = query.setParameter("nit", nit);
        
        List<EmpresaEntity> sameNit = query.getResultList();
        if (sameNit.isEmpty()) {
            return null;
        } else {
            return sameNit.get(0);
        }
        
    }
    
    
    public EmpresaEntity update(EmpresaEntity entity){
        return em.merge(entity);
    }
    
    
    public void delete(int nit){
        EmpresaEntity entity = em.find(EmpresaEntity.class, nit);
        em.remove(entity);
    }


    
}
