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
    
    public ComentarioEntity createComentario(ComentarioEntity entity) throws BusinessException {
        if(entity == null) {
            throw new BusinessException("El comentario no puede ser nulo");
        }
        if(entity.getAutor() == null || entity.getComentario() == null) {
            throw new BusinessException("El autor y el comentario deben ser no nulos");
        }
        return persistence.createComentario(entity);
    }
    
    public ComentarioEntity createComentarioBlog(Long grupoId, Long blogId, ComentarioEntity entity) throws BusinessException{
        List<ComentarioEntity> comentarios = blogLogic.getBlog(grupoId, blogId).getComentarios();
        entity = createComentario(entity);
        comentarios.add(entity);
        return entity;
    }
    
    public List<ComentarioEntity> getComentarios() {
        
        return persistence.findAll();
    }
    
    public List<ComentarioEntity> getComentariosBlog(Long grupoId, Long blogId) {
        return blogLogic.getBlog(grupoId, blogId).getComentarios();
    }
    
    public ComentarioEntity getComentarioBlog(Long grupoId, Long blogId, Long comentarioId) {
        List<ComentarioEntity> comentarios = blogLogic.getBlog(grupoId, blogId).getComentarios();
        ComentarioEntity comentario = new ComentarioEntity();
        comentario.setId(comentarioId);
        int index = comentarios.indexOf(comentario);
        if(index<0) {
            throw new NotFoundException("No existe ningún comentario con el id "+comentarioId+" en el blog con id "+blogId);
        }
        return comentarios.get(index);
    }
    
    public List<ComentarioEntity> getComentariosNoticia(Long noticiaId) throws BusinessException {
        return null;
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
    
    public void deleteComentario(Long id) {
        ComentarioEntity comentario = persistence.find(id);
        if(comentario == null) {
            throw new NotFoundException("No existe ningún comentario con el id "+id);
        }
        persistence.delete(id);
    }
    
    public void deleteComentarioBlog(Long grupoId, Long blogId, Long comentarioId) {
        List<ComentarioEntity> comentarios = blogLogic.getBlog(grupoId, blogId).getComentarios();
        ComentarioEntity comentario = new ComentarioEntity();
        comentario.setId(comentarioId);
        int index = comentarios.indexOf(comentario);
        if(index<0) {
            throw new NotFoundException("No existe ningún comentario con el id "+comentarioId+" en el blog con id "+blogId);
        }
        comentarios.remove(index);
        deleteComentario(comentarioId);
    }
}
