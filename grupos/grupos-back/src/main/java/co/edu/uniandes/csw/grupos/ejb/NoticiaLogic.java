/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.persistence.NoticiaPersistence;
import co.edu.uniandes.csw.grupos.exceptions.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author s.guzmanm
 */
@Stateless
public class NoticiaLogic {
    @Inject 
    NoticiaPersistence persistence;
    
    public NoticiaEntity getEntity(Long id) throws BusinessException, NotFoundException
    {
        if(id==null) throw new BusinessException("No se puede acceder con identificaciones vacías o nulas.");
        NoticiaEntity entity= persistence.find(id);
        if(entity==null) throw new NotFoundException("No se encuentra la calificación buscada.");
        return entity;
    }
    
    public List<NoticiaEntity> getAll()
    {
        return persistence.findAll();
    }
    
    public NoticiaEntity createEntity(NoticiaEntity entity) throws BusinessException, NotFoundException
    {
        if(entity== null) throw new BusinessException("No se puede agregar algo nulo al sistema.");
        if(entity.getId()==null) throw new BusinessException ("No se pueden agregar atributos nulos al sistema");
       validarNoticia(entity);
       if(persistence.find(entity.getId())!=null) throw new BusinessException("Ya hay un objeto creado con ese id");
       return persistence.createEntity(entity);
       
       
    }
    
    public NoticiaEntity updateEntity (Long id, NoticiaEntity entity) throws BusinessException, NotFoundException
    {
        if(id==null || entity== null) throw new BusinessException ("No se puede agregar algo nulo al sistema.");
        validarNoticia(entity);
        if(persistence.find(id)==null) throw new NotFoundException("La entidad que quiere actualizar no existe en el sistema.");
        return persistence.updateEntity(entity);
    }
    
    public void deleteEntity(Long id) throws NotFoundException, BusinessException
    {
        if(id==null) throw new BusinessException("No se puede agregar algo nulo al sistema.");
        NoticiaEntity other=persistence.find(id);
        if(other==null) throw new NotFoundException("No se encuentra el recurso para eliminar.");
        persistence.delete(id);
    }
    
    private void validarNoticia(NoticiaEntity entity) throws NotFoundException, BusinessException
    {
        if(entity.getTitulo()==null || entity.getInformacion()==null) throw new BusinessException("La información de la noticia no puede estar vacía");
        //Es improbable pero necesito hacer un caso en el que se le dé un usuario vacío a persistir, o alguno que no existe en el sistema.
        
    }

}
