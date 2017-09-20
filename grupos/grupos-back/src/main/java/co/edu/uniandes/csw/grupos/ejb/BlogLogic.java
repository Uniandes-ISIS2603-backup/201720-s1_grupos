/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
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
    
    public BlogEntity createBlog(BlogEntity entity) throws BusinessException{
        //no sé si la verificación de que el usuario pertenezca al grupo del blog se hace aquí o en la lógica de blog.
        if(entity == null) {
            throw new BusinessException("El blog no puede ser nulo");
        }
        if(entity.getTitulo() == null || entity.getContenido() == null) {
            throw new BusinessException("El título y el contenido deben ser no nulos");
        }
        return persistence.createBlog(entity);
    }
    
    public List<BlogEntity> getBlogs() {
        //Esto debería ser get de todos los blogs de un grupo. Toca arreglarlo
        return persistence.findAll();
    }
    
    public BlogEntity getBlog(Long id) throws NotFoundException{
        BlogEntity blog = persistence.find(id);
        if(blog == null) {
            throw new NotFoundException("No existe ningún blog con el id "+id);
        }
        return blog;
    }
    
    public BlogEntity updateBlog(BlogEntity blog) throws NotFoundException{
        BlogEntity blog2 = persistence.find(blog.getId());
        if(blog2 == null) {
            throw new NotFoundException("No existe ningún blog con el id "+blog.getId());
        }
        return persistence.update(blog);
    }
    
    public void deleteBlog(Long id) throws NotFoundException{
        BlogEntity blog = persistence.find(id);
        if(blog == null) {
            throw new NotFoundException("No existe ningún blog con el id "+id);
        }
        persistence.delete(id);
    }
}
