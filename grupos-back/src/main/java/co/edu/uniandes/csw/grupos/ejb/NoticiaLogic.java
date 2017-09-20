/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.persistence.NoticiaPersistence;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 *
 * @author s.guzmanm
 */
@Stateless
public class NoticiaLogic {
    @Inject 
    NoticiaPersistence persistence;
    
    @Inject
    MultimediaLogic multimedia;
    
    /**
     * 
     * @param id
     * @return
     * @throws BusinessException
     */
    public NoticiaEntity getEntity(Long id) throws BusinessException
    {
        if(id==null) throw new BusinessException("No se puede acceder con identificaciones vacías o nulas.");
        NoticiaEntity entity= persistence.find(id);
        
        if(entity==null) throw new NotFoundException("No se encuentra la noticia buscada.");
        return entity;
    }
    
    /**
     *
     * @return
     */
    public List<NoticiaEntity> getAll()
    {
        List<NoticiaEntity> list=persistence.findAll();
        if(list==null || list.isEmpty()) return new ArrayList<>();
        return list;
    }
    
    /**
     *
     * @param entity
     * @return
     * @throws BusinessException
     * @throws NotFoundException
     */
    public NoticiaEntity createEntity(NoticiaEntity entity) throws BusinessException
    {
        if(entity== null) throw new BusinessException("No se puede agregar algo nulo al sistema.");
        if(entity.getId()==null) throw new BusinessException ("No se pueden agregar atributos nulos al sistema");
       validarNoticia(entity);
       if(persistence.find(entity.getId())!=null) throw new BusinessException("Ya hay un objeto creado con ese id");
       return persistence.createEntity(entity);
       
       
    }
    
    /**
     *
     * @param id
     * @param entity
     * @return
     * @throws BusinessException
     * @throws NotFoundException
     */
    public NoticiaEntity updateEntity (Long id, NoticiaEntity entity) throws BusinessException
    {
        if(id==null || entity== null) throw new BusinessException ("No se puede agregar algo nulo al sistema.");
        validarNoticia(entity);
        if(persistence.find(id)==null) throw new NotFoundException("La entidad que quiere actualizar no existe en el sistema.");
        return persistence.updateEntity(entity);
    }
    
    /**
     *
     * @param id
     * @throws NotFoundException
     * @throws BusinessException
     */
    public void deleteEntity(Long id) throws  BusinessException
    {
        if(id==null) throw new BusinessException("No se puede agregar algo nulo al sistema.");
        NoticiaEntity other=persistence.find(id);
        if(other==null) throw new NotFoundException("No se encuentra el recurso para eliminar.");
        persistence.delete(id);
    }
    
    /**
     * Valida que la noticia que llega esté bien.<br>
     * @param entity Entidad a evaluar.<br>
     * @throws NotFoundException Si no se encuentra en el sistema.<br>
     * @throws BusinessException Si hay una excepción de regla de negocio.
     */
    private void validarNoticia(NoticiaEntity entity) throws  BusinessException
    {
        
        if(entity.getTitulo()==null || entity.getInformacion()==null) throw new BusinessException("La información de la noticia no puede estar vacía");
        //Es improbable pero necesito hacer un caso en el que se le dé un usuario vacío a persistir, o alguno que no existe en el sistema.
        
    }
    
    public List<MultimediaEntity> getMultimedia(Long id) throws BusinessException
    {
        return getEntity(id).getMultimedia();
    }
    
    public MultimediaEntity getMultimedia(Long idNoticia, String link) throws BusinessException 
    {
        List<MultimediaEntity> list = getMultimedia(idNoticia);
        MultimediaEntity buscada = multimedia.getEntity(link);
        int index = list.indexOf(buscada);
        if (index<0) throw new NotFoundException("No se encuentra el elemento multimedia de la noticia");
        return buscada;
    }
    
    public List<MultimediaEntity> addMultimedia(Long idNoticia, List<MultimediaEntity> mult)
    {
        NoticiaEntity noticia = getNoticia(idNoticia);
        MultimediaEntity entity=null;
        for(MultimediaEntity m: mult)
        {
            try
            {
                entity=multimedia.createEntity(m);
                noticia.getMultimedia().add(m);
            }
            catch (BusinessException e)
            {
                //
            }
        }
        persistence.updateEntity(noticia);
        return noticia.getMultimedia();
    }
    
    public List<MultimediaEntity> updateMultimedia(Long idNoticia, MultimediaEntity mult, String link) throws BusinessException
    {
        NoticiaEntity noticia = getNoticia(idNoticia);
        MultimediaEntity m = multimedia.getEntity(link);
        int index = noticia.getMultimedia().indexOf(m);
        if((index<0)) throw new NotFoundException ("No se encuentra la multimedia a actualizar en la noticia.");
        MultimediaEntity updated = multimedia.updateEntity(link, mult);
        noticia.getMultimedia().set(index, updated);
        persistence.updateEntity(noticia);
        return noticia.getMultimedia();
    }
    
    public void deleteMultimedia (Long idNoticia,String link)
    {
        NoticiaEntity noticia = getNoticia(idNoticia);
        MultimediaEntity m = multimedia.getEntity(link);
        if(noticia.getMultimedia().indexOf(m)<0) throw new NotFoundException ("No se encuentra la multimedia a borrar de la noticia.");
        noticia.getMultimedia().remove(m);
    }

    private NoticiaEntity getNoticia(Long idNoticia) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
