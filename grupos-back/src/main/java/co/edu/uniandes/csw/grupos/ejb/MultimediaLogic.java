/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;
import co.edu.uniandes.csw.grupos.persistence.MultimediaPersistence;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

/**
 * Lógica de multimedia
 * @author s.guzmanm
 */
@Stateless
public class MultimediaLogic {
    /**
     * Persistencia de multimedia
     */
    @Inject
    MultimediaPersistence persistence;
   /**
    * Obtiene la multimedia con el link dado.<br>
    * @param link Link dado.<br>
    * @return Entidad con el link.
    */
    public MultimediaEntity getEntity (String link) 
    {
        if(link==null || "".equals(link))
        {
            throw new NotFoundException("No existe un objeto con esa especificación.");
        }
        return persistence.find(link);

    }
    /**
     * Crea una nueva entidad con el objeto dado.<br>
     * @param entity Entidad a persistir.<br>
     * @return Entidad creada.<br>
     * @throws BusinessException Excepción de negocio
     */
    public MultimediaEntity createEntity(MultimediaEntity entity) throws BusinessException
    {
        if(entity == null)
        {
            throw new BusinessException("No se puede guardar un objeto nulo en los datos del sistema.");
        }
        if(entity.getLink()== null || entity.getNombre()==null || entity.getRuta()==null)
        {
            throw new BusinessException("No se puede guardar un objeto nulo en los datos del sistema.");
        }
        String id= entity.getLink();
        MultimediaEntity other= persistence.find(id);
        if(other!=null) 
        {
            throw new BusinessException ("La multimedia ya se encuentra en el sistema.");
        }
        return persistence.createEntity(entity);
    }
    /**
     * Actualiza la entidad con el link dado y el objeto de multimedia<br>
     * @param link Link de multimedia.<br>
     * @param entity Entidad a actualizar.<br>
     * @return Entidad actualizada.<br>
     * @throws BusinessException Excepción de negocio
     */
    public MultimediaEntity updateEntity(String link, MultimediaEntity entity) throws  BusinessException
    {
        if(entity == null)
        {
            throw new BusinessException("No se puede guardar un objeto nulo en los datos del sistema.");
        }
        if(entity.getNombre()==null || entity.getRuta()==null)
        {
            throw new BusinessException("No se puede guardar un objeto nulo en los datos del sistema.");
        }
        if(persistence.find(link)==null) 
        {
            throw new NotFoundException("No existe el objeto a actualizar.");
        }
        entity.setLink(link);
        return persistence.updateEntity(entity);
    }
    /**
     * Borra la entidad con link dado.<br>
     * @param link  Link para borrar.
     */
    public void deleteEntity(String link) 
    {
        MultimediaEntity entity=persistence.find(link);
        if(entity == null) 
        {
            throw new NotFoundException("No existe una entidad a eliminar");
        }
        persistence.delete(link);
    }
    
    
}
