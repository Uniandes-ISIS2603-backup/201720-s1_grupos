/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.exceptions.NotFoundException;
import co.edu.uniandes.csw.grupos.persistence.BlogPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author se.cardenas
 */
@Stateless
public class BlogLogic {
    
    @Inject
    BlogPersistence persistence;
    
    @Inject
    GrupoLogic grupoLogic;
    
    @Inject
    MultimediaLogic multimediaLogic;
    
    public BlogEntity createBlog(BlogEntity entity, Long grupoId) throws BusinessException{
        //no sé si la verificación de que el usuario pertenezca al grupo del blog se hace aquí o en la lógica de blog.
        if(entity == null) {
            throw new BusinessException("El blog no puede ser nulo");
        }
        if(entity.getTitulo() == null || entity.getContenido() == null) {
            throw new BusinessException("El título y el contenido deben ser no nulos");
        }
        entity.setGrupo(grupoLogic.getGrupo(grupoId));
        return persistence.createBlog(entity);
    }
    
    public List<BlogEntity> getBlogs() {
        return persistence.findAll();
    }
    
    public List<BlogEntity> getBlogs(Long grupoId) {
        //Esto debería ser get de todos los blogs de un grupo. Toca arreglarlo
        GrupoEntity grupo = grupoLogic.getGrupo(grupoId);
        return grupo.getBlogsGrupo();
    }
    
    public BlogEntity getBlog(Long id) throws NotFoundException {
        BlogEntity blog = persistence.find(id);
        if(blog == null) {
            throw new NotFoundException("No existe ningún blog con el id "+id);
        }
        return blog;
    }
    
    public BlogEntity getBlog(Long grupoId, Long blogId) throws NotFoundException {
        grupoLogic.getGrupo(grupoId);
        BlogEntity blog = persistence.findBlogGrupo(grupoId, blogId);
        if(blog == null) {
            throw new NotFoundException("No existe ningún blog con el id "+blogId+" en el grupo con id "+grupoId);
        }
        return blog;
    }
    
    public BlogEntity updateBlog(BlogEntity blog, Long grupoId) throws NotFoundException {
        BlogEntity blog2 = persistence.find(blog.getId());
        if(blog2 == null) {
            throw new NotFoundException("No existe ningún blog con el id "+blog.getId());
        }
        blog.setGrupo(grupoLogic.getGrupo(grupoId));
        return persistence.update(blog);
    }
    
    public void deleteBlog(Long grupoId, Long blogId) throws NotFoundException{
        grupoLogic.getGrupo(grupoId);
        BlogEntity blog = persistence.findBlogGrupo(blogId, grupoId);
        if(blog == null) {
            throw new NotFoundException("No existe ningún blog con el id "+blogId+"en el grupo con id"+grupoId);
        }
        persistence.delete(blogId);
    }
    
    public MultimediaEntity getMultimedia(Long blogId, String link) throws NotFoundException{
        List<MultimediaEntity> multimedia = getBlog(blogId).getMultimedia();
        MultimediaEntity multiEntity = new MultimediaEntity();
        multiEntity.setLink(link);
        int index = multimedia.indexOf(multiEntity);
        if(index<0) {
            throw new NotFoundException("No existe una multimedia con el link "+link+" en el blog con id "+blogId);
        }
        return multimedia.get(index);
    }
    
    public MultimediaEntity addMultimedia(Long blogId, String link) throws NotFoundException{
        BlogEntity entity = getBlog(blogId);
        MultimediaEntity multi = multimediaLogic.getEntity(link);
        entity.getMultimedia().add(multi);
        return getMultimedia(blogId, link);
    }
}
