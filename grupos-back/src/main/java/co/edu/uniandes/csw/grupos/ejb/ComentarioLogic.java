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
 *
 * @author se.cardenas
 */
@Stateless
public class ComentarioLogic {
    
    @Inject
    private ComentarioPersistence persistence;
    
    @Inject
    private BlogLogic blogLogic;
    
    @Inject
    private NoticiaLogic noticiaLogic;
    
    @Inject
    private GrupoLogic grupoLogic;
    
    public ComentarioEntity createComentario(ComentarioEntity entity) throws BusinessException {
        if(entity == null) {
            throw new BusinessException("El comentario no puede ser nulo");
        }
        if(entity.getAutor() == null || entity.getComentario() == null) {
            throw new BusinessException("El autor y el comentario deben ser no nulos");
        }
        return persistence.createComentario(entity);
    }
    
    public ComentarioEntity createComentarioBlog(Long grupoId, Long blogId, ComentarioEntity entity) throws BusinessException {
        List<ComentarioEntity> comentarios = getComentariosBlog(grupoId, blogId);
        ComentarioEntity newEntity = createComentario(entity);
        comentarios.add(newEntity);
        return entity;
    }
    
    public ComentarioEntity createComentarioNoticia(Long grupoId, Long noticiaId, ComentarioEntity entity)throws BusinessException {
        List<ComentarioEntity> comentarios = getComentariosNoticia(grupoId, noticiaId);
        ComentarioEntity newEntity = createComentario(entity);
        comentarios.add(newEntity);
        return entity;
    }
    
    public List<ComentarioEntity> getComentarios() {
        
        return persistence.findAll();
    }
    
    public List<ComentarioEntity> getComentariosBlog(Long grupoId, Long blogId) {
        return blogLogic.getBlog(grupoId, blogId).getComentarios();
    }
    
    public List<ComentarioEntity> getComentariosNoticia(Long grupoId, Long noticiaId) {
        NoticiaEntity noticia = grupoLogic.getNoticia(grupoId, noticiaId);
        if(noticia == null) {
            throw new NotFoundException("No existe una noticia con el id "+noticiaId+"en el grupo con id "+grupoId);
        }
        return noticia.getComentarios();
    }
    
    public ComentarioEntity getComentarioBlog(Long grupoId, Long blogId, Long comentarioId) {
        List<ComentarioEntity> comentarios = getComentariosBlog(grupoId, blogId);
        ComentarioEntity comentario = new ComentarioEntity();
        comentario.setId(comentarioId);
        int index = comentarios.indexOf(comentario);
        if(index<0) {
            throw new NotFoundException("No existe ningún comentario con el id "+comentarioId+" en el blog con id "+blogId);
        }
        return comentarios.get(index);
    }
    
    public ComentarioEntity getComentarioNoticia(Long grupoId, Long noticiaId, Long comentarioId) throws BusinessException {
        List<ComentarioEntity> comentarios = getComentariosNoticia(grupoId, noticiaId);
        ComentarioEntity comentario = new ComentarioEntity();
        comentario.setId(comentarioId);
        int index = comentarios.indexOf(comentario);
        if(index<0) {
            throw new NotFoundException("No existe ningún comentario con el id "+comentarioId+" en la noticia con id "+noticiaId);
        }
        return comentarios.get(index);
    }
    
    public ComentarioEntity updateComentario(ComentarioEntity comentario) {
        ComentarioEntity comentario2 = persistence.find(comentario.getId());
        if(comentario2 == null) {
            throw new NotFoundException("No existe ningún comentario con el id "+comentario.getId());
        }
        return persistence.update(comentario);
    }
    
    public ComentarioEntity updateComentarioBlog(Long grupoId, Long blogId, ComentarioEntity comentario) {
        getComentarioBlog(grupoId, blogId, comentario.getId());
        return updateComentario(comentario);
    }
    
    public ComentarioEntity updateComentarioNoticia(Long grupoId, Long noticiaId, ComentarioEntity comentario) throws BusinessException{
        getComentarioNoticia(grupoId, noticiaId, comentario.getId());
        return updateComentario(comentario);
    }
    
    public void deleteComentario(Long id) {
        ComentarioEntity comentario = persistence.find(id);
        if(comentario == null) {
            throw new NotFoundException("No existe ningún comentario con el id "+id);
        }
        persistence.delete(id);
    }
    
    public void deleteComentarioBlog(Long grupoId, Long blogId, Long comentarioId) {
        List<ComentarioEntity> comentarios = getComentariosBlog(grupoId, blogId);
        ComentarioEntity comentario = new ComentarioEntity();
        comentario.setId(comentarioId);
        int index = comentarios.indexOf(comentario);
        if(index<0) {
            throw new NotFoundException("No existe ningún comentario con el id "+comentarioId+" en el blog con id "+blogId);
        }
        comentarios.remove(index);
        deleteComentario(comentarioId);
    }
    
    public void deleteComentarioNoticia(Long grupoId, Long noticiaId, Long comentarioId) {
        List<ComentarioEntity> comentarios = getComentariosNoticia(grupoId, noticiaId);
        ComentarioEntity comentario = new ComentarioEntity();
        comentario.setId(comentarioId);
        int index = comentarios.indexOf(comentario);
        if(index<0) {
            throw new NotFoundException("No existe ningún comentario con el id "+comentarioId+" en la noticia con id "+noticiaId);
        }
        comentarios.remove(index);
        deleteComentario(comentarioId);
    }
}
