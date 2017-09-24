/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.entities.CalificacionEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.BlogPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

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
    
    @Inject
    CalificacionLogic calificacionLogic;
    
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
    
    public BlogEntity getBlog(Long id)  {
        BlogEntity blog = persistence.find(id);
        if(blog == null) {
            throw new NotFoundException("No existe ningún blog con el id "+id);
        }
        return blog;
    }
    
    public BlogEntity getBlog(Long grupoId, Long blogId)  {
        grupoLogic.getGrupo(grupoId);
        BlogEntity blog = persistence.findBlogGrupo(grupoId, blogId);
        if(blog == null) {
            throw new NotFoundException("No existe ningún blog con el id "+blogId+" en el grupo con id "+grupoId);
        }
        return blog;
    }
    
    public BlogEntity updateBlog(BlogEntity blog, Long grupoId)  {
        BlogEntity blog2 = persistence.find(blog.getId());
        if(blog2 == null) {
            throw new NotFoundException("No existe ningún blog con el id "+blog.getId());
        }
        blog.setGrupo(grupoLogic.getGrupo(grupoId));
        return persistence.update(blog);
    }
    
    public void deleteBlog(Long grupoId, Long blogId) {
        grupoLogic.getGrupo(grupoId);
        BlogEntity blog = persistence.findBlogGrupo(blogId, grupoId);
        if(blog == null) {
            throw new NotFoundException("No existe ningún blog con el id "+blogId+"en el grupo con id"+grupoId);
        }
        persistence.delete(blogId);
    }
    
    public MultimediaEntity getMultimedia(Long blogId, String link) {
        List<MultimediaEntity> multimedia = getBlog(blogId).getMultimedia();
        MultimediaEntity multiEntity = new MultimediaEntity();
        multiEntity.setLink(link);
        int index = multimedia.indexOf(multiEntity);
        if(index<0) {
            throw new NotFoundException("No existe una multimedia con el link "+link+" en el blog con id "+blogId);
        }
        return multimedia.get(index);
    }
    
    public MultimediaEntity addMultimedia(Long blogId, String link) {
        BlogEntity entity = getBlog(blogId);
        MultimediaEntity multi = multimediaLogic.getEntity(link);
        entity.getMultimedia().add(multi);
        return getMultimedia(blogId, link);
    }
    
     
    public List<MultimediaEntity> getMultimedia(Long id) throws BusinessException, NotFoundException
    {
        return getBlog(id).getMultimedia();
    }
    
    
    public List<MultimediaEntity> addMultipleMultimedia(Long grupoId, Long id, List<MultimediaEntity> mult) throws BusinessException, NotFoundException
    {
        BlogEntity blog = getBlog(id);
        MultimediaEntity entity=null;
        for(MultimediaEntity m: mult)
        {
            
            entity=multimediaLogic.getEntity(m.getLink());
            if(entity==null)
                entity=multimediaLogic.createEntity(m);
            
            if(blog.getMultimedia().indexOf(entity)<0)
            blog.getMultimedia().add(m);
        }
        updateBlog(blog,grupoId);
        return blog.getMultimedia();
    }
    
    public List<MultimediaEntity> updateMultimedia(Long grupoId, Long id, MultimediaEntity mult, String link) throws BusinessException, NotFoundException
    {
        BlogEntity blog = getBlog(id);
        MultimediaEntity m = multimediaLogic.getEntity(link);
        if(m==null) throw new NotFoundException("La multimedia no existe");
        int index = blog.getMultimedia().indexOf(m);
        if((index<0)) throw new NotFoundException ("No se encuentra la multimedia a actualizar en la noticia.");
        MultimediaEntity updated = multimediaLogic.updateEntity(link, mult);
        blog.getMultimedia().set(index, updated);
        updateBlog(blog,grupoId);
        return blog.getMultimedia();
    }
    
    public void deleteMultimedia (Long id,String link) throws BusinessException, NotFoundException
    {
        
        BlogEntity blog = getBlog(id);
        MultimediaEntity m = multimediaLogic.getEntity(link);
        if(m==null) throw new NotFoundException("La multimedia no existe");
        int index=blog.getMultimedia().indexOf(m);
        if(blog.getMultimedia().indexOf(m)<0) throw new NotFoundException ("No se encuentra la multimedia a borrar de la noticia.");
        blog.getMultimedia().remove(m);
        
        
    }
    
    public CalificacionEntity getCalificacion(Long blogId, Long id) 
    {
        BlogEntity entity = getBlog(blogId);
        List<CalificacionEntity> calificaciones = entity.getCalificaciones();
        CalificacionEntity c = new CalificacionEntity();
        c.setId(id);
        int index = calificaciones.indexOf(c);
        if(index<0) throw new NotFoundException("No se encuentra la calificación");
        return calificaciones.get(index);
    }
    
    public List<CalificacionEntity> getCalificaciones(Long blogId) 
    {
        BlogEntity entity = getBlog(blogId);
        return entity.getCalificaciones();
    }
    
    public CalificacionEntity addCalificacion(Long grupoId, Long blogId, CalificacionEntity calificacion) throws BusinessException, NotFoundException
    {
        CalificacionEntity c=calificacionLogic.createEntity(calificacion);
        BlogEntity blog = getBlog(blogId);
        int index=blog.getCalificaciones().indexOf(c);
        if(index>=0) 
        {
            calificacionLogic.deleteEntity(c.getId());
            throw new BusinessException("Ya existe la calificación dada.");
        }
        int numero=blog.getCalificaciones().size();
        blog.setPromedio(((blog.getPromedio()*numero)+c.getCalificacion())/(numero+1));
        blog.getCalificaciones().add(c);
        updateBlog(blog,grupoId);
        return blog.getCalificaciones().get(blog.getCalificaciones().size()-1);
    }
    
    public CalificacionEntity updateCalificacion(Long grupoId, Long blogId, Long calificacionId,CalificacionEntity nueva) throws BusinessException, NotFoundException
    {
        BlogEntity blog = getBlog(blogId);
        int index=blog.getCalificaciones().indexOf(nueva);
        if(index<0) throw new NotFoundException("La calificación buscada no existe");
        CalificacionEntity updated=calificacionLogic.updateEntity(calificacionId, nueva);
        blog.getCalificaciones().set(index, updated);
        updateBlog(blog,grupoId);
        return blog.getCalificaciones().get(index);
    }
    
    public void removeCalificacino(Long grupoId,Long blogId, Long calificacionId) throws BusinessException
    {
      BlogEntity blog = getBlog(blogId);
      CalificacionEntity calificacion=calificacionLogic.getEntity(calificacionId);
      int index=blog.getCalificaciones().indexOf(calificacion);
      if(index<0) throw new BusinessException("La calificación a eliminar no existe");
      int tam=blog.getCalificaciones().size();
      if(tam==0) blog.setPromedio(0.0);
      else
      {
          blog.setPromedio(((blog.getPromedio()*tam)-calificacion.getCalificacion())/(tam-1));
      }
      blog.getCalificaciones().remove(index);
      calificacionLogic.deleteEntity(calificacionId);
      updateBlog(blog,grupoId);
    }
}
