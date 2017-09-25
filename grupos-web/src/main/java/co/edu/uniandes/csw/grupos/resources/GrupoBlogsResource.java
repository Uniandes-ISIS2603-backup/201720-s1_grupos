/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.BlogDTO;
import co.edu.uniandes.csw.grupos.dtos.BlogDetailDTO;
import co.edu.uniandes.csw.grupos.dtos.GrupoDTO;
import co.edu.uniandes.csw.grupos.ejb.BlogLogic;
import co.edu.uniandes.csw.grupos.ejb.GrupoLogic;
import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Produces;

/**
 * URI: grupos/{gruposId: \\d+}/blogs
 * @author cm.sarmiento10
 */
@Produces("application/json")
@Consumes("application/json")
public class GrupoBlogsResource {
    /**
     * Lógica del grupo
     */
    @Inject
    private GrupoLogic grupoLogic;
    
    @Inject
    private BlogLogic blogLogic;
    
    /**
     * Convierte una lista de BlogEntity a una lista de BlogDetailDTO.
     *
     * @param entityList Lista de BlogEntity a convertir.
     * @return Lista de BlogDetailDTO convertida.
     *
     */
    private List<BlogDTO> BlogsListEntity2DTO(List<BlogEntity> entityList) {
        List<BlogDTO> list = new ArrayList<>();
        for (BlogEntity entity : entityList) {
            list.add(new BlogDTO(entity));
        }
        return list;
    }
    
    /**
     * Convierte una lista de BlogDetailDTO a una lista de BlogEntity.
     *
     * @param dtos Lista de BlogDetailDTO a convertir.
     * @return Lista de BlogEntity convertida.
     *
     */
    private List<BlogEntity> BlogsListDTO2Entity(List<BlogDetailDTO> dtos) {
        List<BlogEntity> list = new ArrayList<>();
        for (BlogDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    /**
     * Obtiene una colección de instancias de BlogDetailDTO asociadas a una
     * instancia de Grupo
     *
     * @param grupoId Identificador de la instancia de Grupo
     * @return Colección de instancias de BlogDetailDTO asociadas a la
     * instancia de Grupo
     *
     */
    @GET
    public List<BlogDTO> listBlogs(@PathParam("grupoId") Long grupoId) {
        return BlogsListEntity2DTO(blogLogic.getBlogs(grupoId));
    }
    
    /**
     * Obtiene una instancia de Blog asociada a una instancia de Grupo
     *
     * @param grupoId Identificador de la instancia de Grupo
     * @param blogId Identificador de la instancia de Blog
     * @return
     *
     */
    @GET
    @Path("{blogId: \\d+}")
    public BlogDetailDTO getBlogs(@PathParam("grupoId") Long grupoId, @PathParam("blogId") Long blogId) {
        return new BlogDetailDTO(blogLogic.getBlog(grupoId, blogId));
    }
    
    /**
     * Asocia un Blog existente a un Grupo
     *
     * @param grupoId Identificador de la instancia de Grupo
     * @param dto Blog a añadir
     * @return Instancia de BlogDetailDTO que fue asociada a Grupo
     *@throws BusinessException si no se cumple alguna regla de negocio
     */
    @POST
    public BlogDetailDTO addBlogs(@PathParam("grupoId") Long grupoId, BlogDetailDTO dto) throws BusinessException {
        return new BlogDetailDTO(blogLogic.createBlog(dto.toEntity(), grupoId));
    }
    
    /**
     * Desasocia un Blog existente de un Grupo existente
     *
     * @param grupoId Identificador de la instancia de Grupo
     * @param blogId Identificador de la instancia de Blog
     *
     */
    @DELETE
    @Path("{blogId: \\d+}")
    public void removeBlogs(@PathParam("grupoId") Long grupoId, @PathParam("blogId") Long blogId) {
        blogLogic.deleteBlog(grupoId, blogId);
    }

    @Path("{blogId: \\d+}/multimedia")
    public Class<BlogMultimediaResource> getMultimedia(@PathParam("grupoId") Long grupoId, @PathParam("blogId") Long blogId) 
    {
        blogLogic.getBlog(grupoId, blogId);
        return BlogMultimediaResource.class;
    }
    @Path("{blogId: \\d+}/calificaciones")
    public Class<BlogCalificacionResource> getCalificaciones(@PathParam("grupoId") Long grupoId, @PathParam("blogId") Long blogId) 
    {
        blogLogic.getBlog(grupoId, blogId);
        return BlogCalificacionResource.class;
    }
    
    @PUT
    @Path("{blogId: \\d+}")
    public BlogDTO updateBlog(@PathParam("grupoId") Long grupoId, @PathParam("blogId") Long blogId, BlogDetailDTO dto) {
        BlogEntity entity = dto.toEntity();
        entity.setId(blogId);
        return new BlogDetailDTO(blogLogic.updateBlog(entity, grupoId));
    }
    
    @Path("{blogId: \\d+}/comentarios")
<<<<<<< HEAD
    public Class<BlogComentarioResource> getComentarios(@PathParam("grupoId") Long grupoId,@PathParam("blogId") Long blogId) {
=======
    public Class<BlogComentarioResource> getComentarios(@PathParam("grupoId") Long grupoId, @PathParam("blogId") Long blogId) {
>>>>>>> Completa
        blogLogic.getBlog(grupoId, blogId);
        return BlogComentarioResource.class;
    }
}
