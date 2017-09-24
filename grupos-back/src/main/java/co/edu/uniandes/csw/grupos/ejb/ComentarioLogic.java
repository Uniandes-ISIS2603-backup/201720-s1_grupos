/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.ComentarioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.exceptions.NotFoundException;
import co.edu.uniandes.csw.grupos.persistence.ComentarioPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

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
    
    public List<ComentarioEntity> getComentarios() {
        
        return persistence.findAll();
    }
    
    public List<ComentarioEntity> getComentariosBlog(Long grupoId, Long blogId) throws NotFoundException {
        return blogLogic.getBlog(grupoId, blogId).getComentarios();
    }
    
    public List<ComentarioEntity> getComentariosNoticia(Long noticiaId) {
        return null;
    }
    
    public ComentarioEntity updateComentario(ComentarioEntity comentario) throws NotFoundException {
        ComentarioEntity comentario2 = persistence.find(comentario.getId());
        if(comentario2 == null) {
            throw new NotFoundException("No existe ningún comentario con el id "+comentario.getId());
        }
        return persistence.update(comentario);
    }
    
    public void deleteComentario(Long id) throws NotFoundException {
        ComentarioEntity comentario = persistence.find(id);
        if(comentario == null) {
            throw new NotFoundException("No existe ningún comentario con el id "+id);
        }
        persistence.delete(id);
    }
}
