/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.EventoEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.exceptions.NotFoundException;
import co.edu.uniandes.csw.grupos.persistence.EventoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author js.ramos14
 */
@Stateless
public class EventoLogic {
    
    @Inject
    EventoPersistence persistence;
    
    public EventoEntity getEntity(Long id) throws BusinessException, NotFoundException
    {
        if(id == null) throw new BusinessException("La id solicitada no puede estar vacia o nula");
        EventoEntity entity = persistence.find(id);
        if(entity== null) throw new NotFoundException("No existe un evento con la id " + id +" solicitada");
        return entity;
    }
    
    public List<EventoEntity> getAll()
    {
        return persistence.findAll();
    }

    public EventoEntity createEntity(EventoEntity entity,UsuarioEntity admin) throws BusinessException
    {
        if(entity == null) throw new BusinessException("No se puede agregar algo nulo");
        if(persistence.find(entity.getId()) != null) throw new BusinessException("Ya existe un Evento con ese id");
        return persistence.create(entity);        
    }
    public EventoEntity updateEntity(EventoEntity entity, UsuarioEntity admin) throws NotFoundException, BusinessException{
        EventoEntity Evento= persistence.find(entity.getId());
        if(Evento==null)
       {
           throw new NotFoundException("No se encontró una Evento con el id: " + entity.getId());
       }
        EventoEntity newEntity = persistence.update(entity);
        return newEntity;
    }
    
    public void deleteEntity(EventoEntity entity, UsuarioEntity admin) throws BusinessException, NotFoundException
    {
        EventoEntity Evento = persistence.find(entity.getId());
       if(Evento==null)
       {
           throw new NotFoundException("No se encontró una Evento con el id: " + entity.getId());
       }
       persistence.delete(entity.getId());
    }
    
}
