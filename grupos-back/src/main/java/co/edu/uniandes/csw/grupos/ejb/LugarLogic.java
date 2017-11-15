/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.LugarEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.LugarPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

/**
 *Lógica de lugar
 * @author js.ramos14
 */
@Stateless
public class LugarLogic {
    /**
     * Persistencia de lugar.<br>
     */
    @Inject
    LugarPersistence persistence;
    /**
     * Obtiene la entidad con id dado.<br>
     * @param id Id de lugar.<br>
     * @return Entidad.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException Si no se encuentra.
     */
    public LugarEntity getEntity(Long id) throws BusinessException
    {
        if(id == null) throw new BusinessException("La id solicitada no puede estar vacia o nula");
        LugarEntity entity = persistence.find(id);
        if(entity== null) throw new NotFoundException("No existe un evento con la id " + id +" solicitada");
        return entity;
    }
    /**
     * Obtiene todas las entidades.<br>
     * @return Todos los lugares en lista
     */
    public List<LugarEntity> getAll()
    {
        return persistence.findAll();
    }
    /**
     * Crea una nueva entidad de lugar.<br>
     * @param entity Entidad de lugar.<br>
     * @return Entidad creada.<br>
     * @throws BusinessException  Excepción de negocio.
     */
    public LugarEntity createEntity(LugarEntity entity) throws BusinessException
    {
        if(entity == null) throw new BusinessException("No se puede agregar algo nulo");
        return persistence.create(entity);        
    }
    /**
     * Actualiza la entidad al valor dado de un usuario.<br>
     * @param entity Entidad a actualizar.<br>
     * @param admin Administrador.<br>
     * @return Entidad actualizada.<br>
     * @throws NotFoundException Si no se encuentra nada.<br>
     * @throws BusinessException  Excepción de negocio.
     */
    public LugarEntity updateEntity(LugarEntity entity, UsuarioEntity admin) throws BusinessException{
        LugarEntity lugar= persistence.find(entity.getId());
        if(lugar==null)
       {
           throw new NotFoundException("No se encontró una Lugar con el id: " + entity.getId());
       }
        LugarEntity newEntity = persistence.update(entity);
        return newEntity;
    }
    /**
     * Cambia la disponibilidad de un un lugar con el objeto dado.<br>
     * @param id id de la entidad dada.<br>
     * @return Entidad actualizada.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException  Si no se encuentra algo.
     */
    public LugarEntity deleteEntity(Long id) throws NotFoundException
    {
        LugarEntity lugar = persistence.find(id);
       if(lugar==null)
       {
           throw new NotFoundException("No se encontró una Lugar con el id: " + id);
       }
       lugar.setDisponibilidad(!lugar.isDisponibilidad());
       LugarEntity newEntity = persistence.update(lugar);
       return newEntity;
    }
}