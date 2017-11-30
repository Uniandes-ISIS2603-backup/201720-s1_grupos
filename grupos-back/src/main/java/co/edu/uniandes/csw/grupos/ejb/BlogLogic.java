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
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.BlogPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

/**
 * Lógica de blog.
 * @author se.cardenas
 */
@Stateless
public class BlogLogic {
    /**
     * Persistencia de blog
     */
    @Inject
    BlogPersistence persistence;
    /**
     * Lógica de grupo
     */
    @Inject
    GrupoLogic grupoLogic;
    /**
     * Lógica de multimedia
     */
    @Inject
    MultimediaLogic multimediaLogic;
    /**
     * Lógica de calificación
     */
    @Inject
    CalificacionLogic calificacionLogic;
    
    private String err = "No existe ningún blog con el id ";
    
    /**
     * Crea un blog.<br>
     * @param entity Blog.<br>
     * @param grupoId Id del grupo.<br>
     * @return Entidad creada.<br>
     * @throws BusinessException Excepción de negocio
     */
    public BlogEntity createBlog(BlogEntity entity, Long grupoId) throws BusinessException{
        //no sé si la verificación de que el usuario pertenezca al grupo del blog se hace aquí o en la lógica de blog.
        if(entity == null) {
            throw new BusinessException("El blog no puede ser nulo");
        }
        if(entity.getTitulo() == null || entity.getContenido() == null) {
            throw new BusinessException("El título y el contenido deben ser no nulos");
        }
        entity.setGrupo(grupoLogic.getGrupo(grupoId));
        entity.setPromedio(0.0);
        entity.setId(new Long(1));
        return persistence.createBlog(entity);
    }
    /**
     * Obtiene todos los blog.<br>
     * @return Lista de todos los blogs.
     */
    public List<BlogEntity> getBlogs() {
        return persistence.findAll();
    }
    /**
     * Obtiene blogs de un grupo.<br>
     * @param grupoId Id del grupo.<br>
     * @return Entidad de blog.
     */
    public List<BlogEntity> getBlogs(Long grupoId) {
        //Esto debería ser get de todos los blogs de un grupo. Toca arreglarlo
        GrupoEntity grupo = grupoLogic.getGrupo(grupoId);
        return grupo.getBlogsGrupo();
    }
    /**
     * Obtiene un blog de la persistencia.<br>
     * @param id Id del blog.<br>
     * @return Entidad de blog.
     */
    public BlogEntity getBlog(Long id)  {
        BlogEntity blog = persistence.find(id);
        if(blog == null) {
            throw new NotFoundException(err+id);
        }
        return blog;
    }
    /**
     * Obtiene un blog con id de grupo e id de blog.<br>
     * @param grupoId Id del grupo.<br>
     * @param blogId Id del blog.<br>
     * @return  Entidad de blog.
     */
    public BlogEntity getBlog(Long grupoId, Long blogId)  {
        grupoLogic.getGrupo(grupoId);
        BlogEntity blog = persistence.findBlogGrupo(grupoId, blogId);
        if(blog == null) {
            throw new NotFoundException(err+blogId+" en el grupo con id "+grupoId);
        }
        return blog;
    }
    /**
     * Actualiza el blog con id de grupo dado.<br>
     * @param blog Entidad de blog.<br>
     * @param grupoId Entidad de grupo.<br>
     * @return Blog actualizado
     */
    public BlogEntity updateBlog(BlogEntity blog, Long grupoId)  {
        BlogEntity blog2 = persistence.find(blog.getId());
        if(blog2 == null) {
            throw new NotFoundException(err+blog.getId());
        }
        blog.setGrupo(grupoLogic.getGrupo(grupoId));
        return persistence.update(blog);
    }
    /**
     * Borra un blog del grupo dado.<br>
     * @param grupoId Id del grupo.<br>
     * @param blogId Id del blog.
     */
    public void deleteBlog(Long grupoId, Long blogId) {
        GrupoEntity g=grupoLogic.getGrupo(grupoId);
        BlogEntity blog = persistence.find(blogId);
        if(blog == null) {
            throw new NotFoundException(err+blogId);
        }
        int index=g.getBlogsGrupo().indexOf(blog);
        if(index<0) {
            throw new NotFoundException(err+blogId+"en el grupo con id"+grupoId);
        }
        persistence.delete(blogId);
    }
    /**
     * Obtiene multimedia específica de blog.<br>
     * @param blogId Id del blog.<br>
     * @param link Link de multimedia.<br>
     * @return  Entidad de multimedia.
     */
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
    /**
     * Agrega una multimedia dada a la base.<br>
     * @param blogId Id del blog.<br>
     * @param link Link de multimedia.<br>
     * @return Nueva multimedia.<br>
     */
    public MultimediaEntity addMultimedia(Long blogId, String link) {
        BlogEntity entity = getBlog(blogId);
        MultimediaEntity multi = multimediaLogic.getEntity(link);
        entity.getMultimedia().add(multi);
        return getMultimedia(blogId, link);
    }
    
     /**
      * Obtiene una multimedia .<br>
      * @param id Id del blog<br>
      * @return Lista de multimedia.<br>
      * @throws BusinessException Excepción de negioci.<br>
      * @throws NotFoundException Excepción de no encontrado si no encuentra nada.
      */
    public List<MultimediaEntity> getMultimedia(Long id) throws BusinessException
    {
        return getBlog(id).getMultimedia();
    }
    
    /**
     * Agrega una nueva multimedia de lista al blog.<br>
     * @param grupoId Id del grupo.<br>
     * @param id Id del blog.<br>
     * @param mult Lista de muñtimedia.<br>
     * @return Lista de multimedia actualziada.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException Si no se encuentra algo.
     */
    public List<MultimediaEntity> addMultipleMultimedia(Long grupoId, Long id, List<MultimediaEntity> mult) throws BusinessException
    {
        BlogEntity blog = getBlog(id);
        for(MultimediaEntity m: mult)
        {
            
            MultimediaEntity entity=multimediaLogic.getEntity(m.getLink());
            if(entity==null)
                entity=multimediaLogic.createEntity(m);
            
            if(blog.getMultimedia().indexOf(entity)<0)
            blog.getMultimedia().add(m);
        }
        updateBlog(blog,grupoId);
        return blog.getMultimedia();
    }
    /**
     * Actualiza la multimedia del blog.<br>
     * @param grupoId Id del grupo.<br>
     * @param id Id del blog.<br>
     * @param mult Multimedia a actualizar.<br>
     * @param link Link.<br>
     * @return Lista actualizada.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException  Excepción de no encontrado.
     */
    public List<MultimediaEntity> updateMultimedia(Long grupoId, Long id, MultimediaEntity mult, String link) throws BusinessException
    {
        BlogEntity blog = getBlog(id);
        MultimediaEntity m = multimediaLogic.getEntity(link);
        if(m==null) {
            throw new NotFoundException("La multimedia no existe");
        }
        int index = blog.getMultimedia().indexOf(m);
        if(index<0) {
            throw new NotFoundException ("No se encuentra la multimedia a actualizar en la noticia.");
        }
        MultimediaEntity updated = multimediaLogic.updateEntity(link, mult);
        blog.getMultimedia().set(index, updated);
        updateBlog(blog,grupoId);
        return blog.getMultimedia();
    }
    /**
     * Borra la multimedia de un blog dado.<br>
     * @param id Id del blog.<br>
     * @param link Link<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException Excepción de no encontrado.
     */
    public void deleteMultimedia (Long id,String link) throws BusinessException
    {
        
        BlogEntity blog = getBlog(id);
        MultimediaEntity m = multimediaLogic.getEntity(link);
        if(m==null) {
            throw new NotFoundException("La multimedia no existe");
        }
        if(blog.getMultimedia().indexOf(m)<0) {
            throw new NotFoundException ("No se encuentra la multimedia a borrar de la noticia.");
        }
        blog.getMultimedia().remove(m);
        
        
    }
    /**
     * Obtiene calificación con id de blog e id de calificación.<br>
     * @param blogId Id de blog.<br>
     * @param id Id de calificación.<br>
     * @return Entidad dada.
     */
    public CalificacionEntity getCalificacion(Long blogId, Long id) 
    {
        BlogEntity entity = getBlog(blogId);
        List<CalificacionEntity> calificaciones = entity.getCalificaciones();
        CalificacionEntity c = new CalificacionEntity();
        c.setId(id);
        int index = calificaciones.indexOf(c);
        if(index<0) {
            throw new NotFoundException("No se encuentra la calificación");
        }
        return calificaciones.get(index);
    }
    /**
     * Obtiene todas las calificaciones de un blog.<br>
     * @param blogId Id de blog.<br>
     * @return Lista de calificaciones.
     */
    public List<CalificacionEntity> getCalificaciones(Long blogId) 
    {
        BlogEntity entity = getBlog(blogId);
        return entity.getCalificaciones();
    }
    /**
     * Agrega una calificación.<br>
     * @param grupoId Id del grupo.<br>
     * @param blogId Id del blog.<br>
     * @param c Entidad.<br>
     * @return Calificación agregada.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException  Excepción de no encontrado.
     */
    public CalificacionEntity addCalificacion(Long grupoId, Long blogId, CalificacionEntity c) throws BusinessException
    {
        BlogEntity blog = getBlog(blogId);
        if(blog.getCalificaciones()==null) {
            blog.setCalificaciones(new ArrayList<>());
        }
        int index=blog.getCalificaciones().indexOf(c);
        if(index>=0) 
        {
            throw new BusinessException("Ya existe la calificación dada.");
        }
        for(CalificacionEntity cal: blog.getCalificaciones())
       {
           if(c.getCalificador().getId().equals(cal.getCalificador().getId()))
           {
               throw new BusinessException("Ya ese usuario calificó con el id dado.");
           }
       }
        int numero=blog.getCalificaciones().size();
        if(blog.getPromedio()==null) {
            blog.setPromedio(0.0);
        }
        blog.setPromedio(((blog.getPromedio()*numero)+c.getCalificacion())/(numero+1));
        c.setBlog(blog);
        calificacionLogic.updateEntity(c.getId(), c);
        blog.getCalificaciones().add(c);
        updateBlog(blog,grupoId);
        return blog.getCalificaciones().get(blog.getCalificaciones().size()-1);
    }
    /**
     * Actualiza una calificación.<br>
     * @param grupoId Id del grupo.<br>
     * @param blogId Id del blog.<br>
     * @param calificacionId Id de la calificación.<br>
     * @param nueva Nueva calificación.<br>
     * @return Calificación actualizada.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException  Excepción de no encontrado.
     */
    public CalificacionEntity updateCalificacion(Long grupoId, Long blogId, Long calificacionId,CalificacionEntity nueva) throws BusinessException
    {
        BlogEntity blog = getBlog(blogId);
        nueva.setId(calificacionId);
        int index=blog.getCalificaciones().indexOf(nueva);
        if(index<0) {
            throw new NotFoundException("La calificación buscada no existe");
        }
        CalificacionEntity updated=calificacionLogic.updateEntity(calificacionId, nueva);
        blog.getCalificaciones().set(index, updated);
        updateBlog(blog,grupoId);
        return blog.getCalificaciones().get(index);
    }
    /**
     * Quita una calificación dada.<br>
     * @param grupoId Id del grupo.<br>
     * @param blogId Id del blog.<br>
     * @param calificacionId Id de calificación.<br>
     * @throws BusinessException Excepción de negocio.
     */
    public void removeCalificacino(Long grupoId,Long blogId, Long calificacionId) throws BusinessException
    {
      BlogEntity blog = getBlog(blogId);
      CalificacionEntity calificacion=calificacionLogic.getEntity(calificacionId);
      int index=blog.getCalificaciones().indexOf(calificacion);
      if(index<0) {
          throw new BusinessException("La calificación a eliminar no existe");
      }
      int tam=blog.getCalificaciones().size();
      if(blog.getPromedio()==null) {
          blog.setPromedio(0.0);
      }
      if(tam==1) {
          blog.setPromedio(0.0);
      }
      else
      {
          blog.setPromedio(((blog.getPromedio()*tam)-calificacion.getCalificacion())/(tam-1));
      }
      blog.getCalificaciones().remove(index);
      calificacionLogic.deleteEntity(calificacionId);
      updateBlog(blog,grupoId);
    }
}
