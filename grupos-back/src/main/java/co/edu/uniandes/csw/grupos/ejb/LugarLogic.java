/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.LugarEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.exceptions.NotFoundException;
import co.edu.uniandes.csw.grupos.persistence.LugarPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author js.ramos14
 */
@Stateless
public class LugarLogic {
    
    @Inject
    LugarPersistence persistence;
    
    public LugarEntity getEntity(Long id) throws BusinessException, NotFoundException
    {
        if(id == null) throw new BusinessException("La id solicitada no puede estar vacia o nula");
        LugarEntity entity = persistence.find(id);
        if(entity== null) throw new NotFoundException("No existe un evento con la id " + id +" solicitada");
        return entity;
    }
    
    public List<LugarEntity> getAll()
    {
        return persistence.findAll();
    }

    public LugarEntity createEntity(LugarEntity entity) throws BusinessException
    {
        if(entity == null) throw new BusinessException("No se puede agregar algo nulo");
        if(persistence.find(entity.getId()) != null) throw new BusinessException("Ya existe un lugar con ese id");
        return persistence.create(entity);        
    }
    public LugarEntity updateEntity(LugarEntity entity, UsuarioEntity admin) throws NotFoundException, BusinessException{
        LugarEntity lugar= persistence.find(entity.getId());
        if(lugar==null)
       {
           throw new NotFoundException("No se encontró una Lugar con el id: " + entity.getId());
       }
        LugarEntity newEntity = persistence.update(entity);
        return newEntity;
    }
    
    public void deleteEntity(LugarEntity entity) throws BusinessException, NotFoundException
    {
        LugarEntity lugar = persistence.find(entity.getId());
       if(lugar==null)
       {
           throw new NotFoundException("No se encontró una Lugar con el id: " + entity.getId());
       }
       persistence.delete(entity.getId());
    }
}