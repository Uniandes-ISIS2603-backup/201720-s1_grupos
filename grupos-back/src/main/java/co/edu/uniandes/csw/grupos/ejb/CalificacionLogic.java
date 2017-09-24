/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.CalificacionEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.CalificacionPersistence;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author s.guzmanm
 */
@Stateless
public class CalificacionLogic {
    
    @Inject
    CalificacionPersistence persistence;
    
    public CalificacionEntity getEntity(Long id) throws BusinessException
    {
        if(id==null) throw new BusinessException("No se puede acceder con identificaciones vacías o nulas.");
        CalificacionEntity entity= persistence.find(id);
        if(entity==null) throw new NotFoundException("No se encuentra la calificación buscada.");
        return entity;
    }
    
    public List<CalificacionEntity> getAll()
    {
        return persistence.findAll();
    }
    
    public CalificacionEntity createEntity(CalificacionEntity entity) throws BusinessException
    {
        if(entity== null) throw new BusinessException("No se puede agregar algo nulo al sistema.");
        if(entity.getId()==null) throw new BusinessException ("No se pueden agregar atributos nulos al sistema");
       validarCalificacion(entity);
       if(persistence.find(entity.getId())!=null) throw new BusinessException("Ya hay un objeto creado con ese id");
       for(CalificacionEntity c: getAll())
       {
           if(c.getCalificador().getId().equals(entity.getCalificador().getId()))
           {
               throw new BusinessException("Ya ese usuario calfició con el id dado.");
           }
       }
       return persistence.createEntity(entity);
       
       
    }
    
    public CalificacionEntity updateEntity (Long id, CalificacionEntity entity) throws BusinessException
    {
        if(id==null || entity== null) throw new BusinessException ("No se puede agregar algo nulo al sistema.");
        validarCalificacion(entity);
        if(persistence.find(id)==null) throw new NotFoundException("La entidad que quiere actualizar no existe en el sistema.");
        return persistence.updateEntity(entity);
    }
    
    public void deleteEntity(Long id) throws NotFoundException, BusinessException
    {
        if(id==null) throw new BusinessException("No se puede agregar algo nulo al sistema.");
        CalificacionEntity other=persistence.find(id);
        if(other==null) throw new NotFoundException("No se encuentra el recurso para eliminar.");
        persistence.delete(id);
    }

    private void validarCalificacion(CalificacionEntity entity) throws BusinessException {
        if(entity.getCalificacion()==null) throw new BusinessException ("No se pueden agregar calificaciones sin un valor dado");
       if(entity.getFecha()==null) throw new BusinessException("No se pueden agregar calificaciones sin fecha");
       if(entity.getCalificacion()<0 || entity.getCalificacion()>5) throw new BusinessException ("La calificación está por fuera del rango dado");
    }
    
   
    
}
