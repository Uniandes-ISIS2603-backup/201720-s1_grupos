/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;
import co.edu.uniandes.csw.grupos.persistence.MultimediaPersistence;
import co.edu.uniandes.csw.grupos.exceptions.*;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author s.guzmanm
 */
@Stateless
public class MultimediaLogic {
    @Inject
    MultimediaPersistence persistence;
    
    public MultimediaEntity getEntity (String link) throws NotFoundException
    {
        if(link==null || link.equals(""))
        {
            throw new NotFoundException("No existe un objeto con esa especificación.");
        }
        MultimediaEntity entity= persistence.find(link);
        if (entity == null) throw new NotFoundException("No existe un objeto con esa especificación.");
        return entity;

    }
    
    public MultimediaEntity createEntity(MultimediaEntity entity) throws BusinessException
    {
        if(entity == null) throw new BusinessException("No se puede guardar un objeto nulo en los datos del sistema.");
        if(entity.getLink()== null || entity.getNombre()==null)
            throw new BusinessException("No se puede guardar un objeto nulo en los datos del sistema.");
        String id= entity.getLink();
        MultimediaEntity other= persistence.find(id);
        if(other!=null) throw new BusinessException ("La multimedia ya se encuentra en el sistema.");
        return persistence.createEntity(entity);
    }
    
    public MultimediaEntity updateEntity(String link, MultimediaEntity entity) throws NotFoundException, BusinessException
    {
        if(entity == null) throw new BusinessException("No se puede guardar un objeto nulo en los datos del sistema.");
        if(entity.getNombre()==null)
            throw new BusinessException("No se puede guardar un objeto nulo en los datos del sistema.");
        if(persistence.find(link)==null) throw new NotFoundException("No existe el objeto a actualizar.");
        entity.setLink(link);
        return persistence.updateEntity(entity);
    }
    
    public void deleteEntity(String link) throws NotFoundException
    {
        MultimediaEntity entity=persistence.find(link);
        if(entity == null) throw new NotFoundException("No existe una entidad a eliminar");
        persistence.delete(link);
    }
    
    
}
