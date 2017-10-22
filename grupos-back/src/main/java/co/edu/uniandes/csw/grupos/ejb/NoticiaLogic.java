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
 * Lógica de noticia
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
        validarNoticia(entity);
        entity.setComentarios(new ArrayList<>());
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
        NoticiaEntity ent= persistence.find(id);
        if(ent==null) throw new NotFoundException("La entidad que quiere actualizar no existe en el sistema.");
        entity.setAutor(ent.getAutor());
        entity.setComentarios(ent.getComentarios());
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
    /**
     * Obtiene la multimedia de una noticia.<br>
     * @param id Id de la noticia.<br>
     * @return Listado de entidades.<br>
     * @throws BusinessException Excepción de negcio.
     */
    public List<MultimediaEntity> getMultimedia(Long id) throws BusinessException
    {
        return getEntity(id).getMultimedia();
    }
    /**
     * Obtiene una multimedia dada.<br>
     * @param idNoticia Noticia id.<br>
     * @param link Link de multimedia.<br>
     * @return MultimediaEntity.<br>
     * @throws BusinessException Excepción de negocio.
     */
    public MultimediaEntity getMultimedia(Long idNoticia, String link) throws BusinessException
    {
        List<MultimediaEntity> list = getMultimedia(idNoticia);
        MultimediaEntity buscada = multimedia.getEntity(link);
        if(buscada==null) throw new NotFoundException("No se encuentra la multimedia en el sistema.");
        int index = list.indexOf(buscada);
        if (index<0) throw new NotFoundException("No se encuentra el elemento multimedia de la noticia");
        return buscada;
    }
    /**
     * Agreega multimedia.<br>
     * @param idNoticia Id de la noticia.<br>
     * @param mult Lista de entidades.<br>
     * @return Lista de entidades.<br>
     * @throws BusinessException Excepción de negocio.
     */
    public List<MultimediaEntity> addMultimedia(Long idNoticia, List<MultimediaEntity> mult) throws BusinessException
    {
        NoticiaEntity noticia = getEntity(idNoticia);
        MultimediaEntity entity=null;
        for(MultimediaEntity m: mult)
        {
            
            entity=multimedia.getEntity(m.getLink());
            if(entity==null)
            {
                entity=multimedia.createEntity(m);
            }
            if(noticia.getMultimedia().indexOf(entity)<0)
            {
                noticia.getMultimedia().add(m);
            }
        }
        persistence.updateEntity(noticia);
        return noticia.getMultimedia();
    }
    /**
     * Actualiza la entidad de multimedia.<br>
     * @param idNoticia Id de noticia.<br>
     * @param mult Multimedia.<br>
     * @param link Link de multimedia.<br>
     * @return Lista de entidades.<br>
     * @throws BusinessException Excepción de negocio.
     */
    public List<MultimediaEntity> updateMultimedia(Long idNoticia, MultimediaEntity mult, String link) throws BusinessException
    {
        NoticiaEntity noticia = getEntity(idNoticia);
        MultimediaEntity m = multimedia.getEntity(link);
        if(m==null) throw new NotFoundException("La multimedia no existe");
        int index = noticia.getMultimedia().indexOf(m);
        if((index<0)) throw new NotFoundException ("No se encuentra la multimedia a actualizar en la noticia. "+m.getLink()+"-"+link);
        MultimediaEntity updated = multimedia.updateEntity(link, mult);
        noticia.getMultimedia().set(index, updated);
        persistence.updateEntity(noticia);
        return noticia.getMultimedia();
    }
    /**
     * Borra una multimedia.<br>
     * @param idNoticia Id de noticia.<br>
     * @param link Link de la noticia.<br>
     * @throws BusinessException Excepción de negocio.
     */
    public void deleteMultimedia (Long idNoticia,String link) throws BusinessException
    {
        
        NoticiaEntity noticia = getEntity(idNoticia);
        MultimediaEntity m = multimedia.getEntity(link);
        if(m==null) throw new NotFoundException("No existe la multimedia a borrar");
        int index=noticia.getMultimedia().indexOf(m);
        if(noticia.getMultimedia().indexOf(m)<0) throw new NotFoundException ("No se encuentra la multimedia a borrar de la noticia.");
        noticia.getMultimedia().remove(m);
        
        
    }
    
    
}
