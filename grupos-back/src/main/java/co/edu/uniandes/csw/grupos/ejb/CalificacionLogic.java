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
 * Lógica de calificación
 * @author s.guzmanm
 */
@Stateless
public class CalificacionLogic {
    /**
     * Persistencia de calificación
     */
    @Inject
    CalificacionPersistence persistence;
    /**
     * Get calificación-<br>
     * @param id Id.<br>
     * @return Entidad de calificación.<br>
     * @throws BusinessException  Excepción de negocio.
     */
    public CalificacionEntity getEntity(Long id) throws BusinessException
    {
        if(id==null) throw new BusinessException("No se puede acceder con identificaciones vacías o nulas.");
        CalificacionEntity entity= persistence.find(id);
        if(entity==null) throw new NotFoundException("No se encuentra la calificación buscada.");
        return entity;
    }
    /**
     * Obtiene todas las calificaciones.<br>
     * @return Todas las calificaciones.
     */
    public List<CalificacionEntity> getAll()
    {
        return persistence.findAll();
    }
    /**
     * Crea una entidad con el param dado.<br>
     * @param entity Entidad a crear.<br>
     * @return Entidad de calificación.<br>
     * @throws BusinessException Excepción de negocvio
     */
    public CalificacionEntity createEntity(CalificacionEntity entity) throws BusinessException
    {
        if(entity== null) throw new BusinessException("No se puede agregar algo nulo al sistema.");
       validarCalificacion(entity);
       return persistence.createEntity(entity);
       
       
    }
    /**
     * Actualiza la entidad.<br>
     * @param id id de la entidad.<br>
     * @param entity Nueva entidad.<br>
     * @return Entidad actualziada.<br>
     * @throws BusinessException Excepción de negocio.
     */
    public CalificacionEntity updateEntity (Long id, CalificacionEntity entity) throws BusinessException
    {
        if(id==null || entity== null) throw new BusinessException ("No se puede agregar algo nulo al sistema.");
        entity.setId(id);
        validarCalificacion(entity);
        if(persistence.find(id)==null) throw new NotFoundException("La entidad que quiere actualizar no existe en el sistema.");
        return persistence.updateEntity(entity);
    }
    /**
     * Borra una calificación.<br>
     * @param id Id a borrar.<br>
     * @throws BusinessException Excepción de negocio.
     */
    public void deleteEntity(Long id) throws BusinessException
    {
        if(id==null) throw new BusinessException("No se puede agregar algo nulo al sistema.");
        CalificacionEntity other=persistence.find(id);
        if(other==null) throw new NotFoundException("No se encuentra el recurso para eliminar.");
        persistence.delete(id);
    }
    /**
     * Valida la calificación que entra por parámetro para que tenfa calificacion y fecha.<br>
     * @param entity Entidad a validar.<br>
     * @throws BusinessException Excepción de negocio.
     */
    private void validarCalificacion(CalificacionEntity entity) throws BusinessException {
        if(entity.getCalificacion()==null) throw new BusinessException ("No se pueden agregar calificaciones sin un valor dado");
       if(entity.getFecha()==null) throw new BusinessException("No se pueden agregar calificaciones sin fecha");
       if(entity.getCalificacion()<0 || entity.getCalificacion()>5) throw new BusinessException ("La calificación está por fuera del rango dado");
    }
    
   
    
}
