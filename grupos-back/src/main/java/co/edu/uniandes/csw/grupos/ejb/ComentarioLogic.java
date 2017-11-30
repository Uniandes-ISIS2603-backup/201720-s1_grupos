/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.ComentarioEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.ComentarioPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

/**
 *Lógica del comentario
 * @author se.cardenas
 */
@Stateless
public class ComentarioLogic {
    /**
     * Persistencia del comentario.
     */
    @Inject
    private ComentarioPersistence persistence;
    /**
     * Lógica del blog.
     */
    @Inject
    private BlogLogic blogLogic;
    /**
     * Lógica de la noticia.
     */
    @Inject
    private NoticiaLogic noticiaLogic;
    /**
     * Lógica del grupo
     */
    @Inject
    private GrupoLogic grupoLogic;
    
    private String err = "No existe ningún comentario con el id ";
    
    /**
     * Crea un nuevo comentario.<br>
     * @param entity Entidad a persistir.<br>
     * @return Entidad persistida.<br>
     * @throws BusinessException Excepción de negocio.
     */
    public ComentarioEntity createComentario(ComentarioEntity entity) throws BusinessException {
        if(entity == null) {
            throw new BusinessException("El comentario no puede ser nulo");
        }
        if(entity.getAutor() == null || entity.getComentario() == null) {
            throw new BusinessException("El autor y el comentario deben ser no nulos");
        }
        return persistence.createComentario(entity);
    }
    /**
     * Crea un nuevo comentario de blog.<br>
     * @param grupoId Id de grupo.<br>
     * @param blogId Id de blog.<br>
     * @param entity Entidad a crear.<br>
     * @return Entidad persistida.<br>
     * @throws BusinessException Excepción de negocio.
     */
    public ComentarioEntity createComentarioBlog(Long grupoId, Long blogId, ComentarioEntity entity) throws BusinessException {
        List<ComentarioEntity> comentarios = getComentariosBlog(grupoId, blogId);
        ComentarioEntity newEntity = createComentario(entity);
        comentarios.add(newEntity);
        return newEntity;
    }
    /**
     * Crea un nuevo comentario de noticia.<br>
     * @param noticiaId Id de noticia.<br>
     * @param entity Entidad a persistir.<br>
     * @return Entidad persistida.<br>
     * @throws BusinessException  Excepción de negocio.
     */
    public ComentarioEntity createComentarioNoticia(Long noticiaId, ComentarioEntity entity)throws BusinessException {
        List<ComentarioEntity> comentarios = getComentariosNoticia( noticiaId);
        ComentarioEntity newEntity = createComentario(entity);
        comentarios.add(newEntity);
        return newEntity;
    }
    /**
     * Obtiene todos los comentarios.<br>
     * @return Todos los comentarios
     */
    public List<ComentarioEntity> getComentarios() {
        
        return persistence.findAll();
    }
    /**
     * Obtiene los comentarios de un blog.<br>
     * @param grupoId Id del grupo.<br>
     * @param blogId Id del blog.<br>
     * @return Listado de entidades de comentario.
     */
    public List<ComentarioEntity> getComentariosBlog(Long grupoId, Long blogId) {
        return blogLogic.getBlog(grupoId, blogId).getComentarios();
    }
    /**
     * Obtiene los comentarios de una noticia.<br>
     * @param noticiaId Id de noticia.<br>
     * @return Listado de entidades.<br>
     * @throws BusinessException Excepción de negocio.
     */
    public List<ComentarioEntity> getComentariosNoticia(Long noticiaId) throws BusinessException {
        NoticiaEntity noticia = noticiaLogic.getEntity(noticiaId);
        if(noticia == null) {
            throw new NotFoundException("No existe una noticia con el id "+noticiaId);
        }
        return noticia.getComentarios();
    }
    /**
     * Obtiene un comentario de un blog de grupo.<br>
     * @param grupoId Id de grupo.<br>
     * @param blogId Id de blog.<br>
     * @param comentarioId Id de comentario.<br>
     * @return Entidad de comentario.
     */
    public ComentarioEntity getComentarioBlog(Long grupoId, Long blogId, Long comentarioId) {
        List<ComentarioEntity> comentarios = getComentariosBlog(grupoId, blogId);
        ComentarioEntity comentario = new ComentarioEntity();
        comentario.setId(comentarioId);
        int index = comentarios.indexOf(comentario);
        if(index<0) {
            throw new NotFoundException(err+comentarioId+" en el blog con id "+blogId);
        }
        return comentarios.get(index);
    }
    /**
     * Obtiene un comentario de una noticia.<br>
     * @param noticiaId Id de noticia.<br>
     * @param comentarioId Id de comentario.<br>
     * @return Entidad de comentario.<br>
     * @throws BusinessException  Excepción de negocio.
     */
    public ComentarioEntity getComentarioNoticia(Long noticiaId, Long comentarioId) throws BusinessException {
        List<ComentarioEntity> comentarios = getComentariosNoticia(noticiaId);
        ComentarioEntity comentario = new ComentarioEntity();
        comentario.setId(comentarioId);
        int index = comentarios.indexOf(comentario);
        if(index<0) {
            throw new NotFoundException(err+comentarioId+" en la noticia con id "+noticiaId);
        }
        return comentarios.get(index);
    }
    /**
     * Actualiza un comentario<br>
     * @param comentario Comentario a actualizar.<br>
     * @return Comentario actualizado.
     */
    public ComentarioEntity updateComentario(ComentarioEntity comentario) {
        ComentarioEntity comentario2 = persistence.find(comentario.getId());
        if(comentario2 == null) {
            throw new NotFoundException(err+comentario.getId());
        }
        return persistence.update(comentario);
    }
    /**
     * Actualiza un comentario de un blog.<br>
     * @param grupoId Id de grupo.<br>
     * @param blogId Id de blog.<br>
     * @param comentario Comentario a actualizar.<br>
     * @return Comentario actualizado.
     */
    public ComentarioEntity updateComentarioBlog(Long grupoId, Long blogId, ComentarioEntity comentario) {
        getComentarioBlog(grupoId, blogId, comentario.getId());
        return updateComentario(comentario);
    }
    /**
     * Actualiza un comentario de una noticia.<br>
     * @param noticiaId Id de noticia.<br>
     * @param comentario Comentario a actualizar.<br>
     * @return Comentario actualizado.<br>
     * @throws BusinessException Excepción de negocio.
     */
    public ComentarioEntity updateComentarioNoticia(Long noticiaId, ComentarioEntity comentario) throws BusinessException{
        getComentarioNoticia(noticiaId, comentario.getId());
        return updateComentario(comentario);
    }
    /**
     * Borra un comentario.<br>
     * @param id Id del comentario a borrar.
     */
    public void deleteComentario(Long id) {
        ComentarioEntity comentario = persistence.find(id);
        if(comentario == null) {
            throw new NotFoundException(err+id);
        }
        persistence.delete(id);
    }
    /**
     * Borra un comentario de blog.<br>
     * @param grupoId Id de grupo.<br>
     * @param blogId Id de blog.<br>
     * @param comentarioId Id de comentario.
     */
    public void deleteComentarioBlog(Long grupoId, Long blogId, Long comentarioId) {
        List<ComentarioEntity> comentarios = getComentariosBlog(grupoId, blogId);
        ComentarioEntity comentario = new ComentarioEntity();
        comentario.setId(comentarioId);
        int index = comentarios.indexOf(comentario);
        if(index<0) {
            throw new NotFoundException(err+comentarioId+" en el blog con id "+blogId);
        }
        comentarios.remove(index);
        deleteComentario(comentarioId);
    }
    /**
     * Borra un comentario de noticia.<br>
     * @param noticiaId Borra un id de noticia.<br>
     * @param comentarioId Borra un id de comentario.<br>
     * @throws BusinessException Excepción de negocio.
     */
    public void deleteComentarioNoticia(Long noticiaId, Long comentarioId) throws BusinessException {
        List<ComentarioEntity> comentarios = getComentariosNoticia(noticiaId);
        ComentarioEntity comentario = new ComentarioEntity();
        comentario.setId(comentarioId);
        int index = comentarios.indexOf(comentario);
        if(index<0) {
            throw new NotFoundException(err+comentarioId+" en la noticia con id "+noticiaId);
        }
        comentarios.remove(index);
        deleteComentario(comentarioId);
    }
}
