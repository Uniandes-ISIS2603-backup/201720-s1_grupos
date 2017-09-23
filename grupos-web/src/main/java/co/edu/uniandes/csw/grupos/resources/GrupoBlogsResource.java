/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.BlogDTO;
import co.edu.uniandes.csw.grupos.dtos.BlogDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.GrupoLogic;
import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.exceptions.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * URI: grupos/{gruposId: \\d+}/blogs
 * @author cm.sarmiento10
 */
public class GrupoBlogsResource {
    /**
     * Lógica del grupo
     */
    @Inject
    private GrupoLogic grupoLogic;
    
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
     * @param GruposId Identificador de la instancia de Grupo
     * @return Colección de instancias de BlogDetailDTO asociadas a la
     * instancia de Grupo
     *
     */
    @GET
    public List<BlogDTO> listBlogs(@PathParam("id") Long id) {
        return BlogsListEntity2DTO(grupoLogic.listBlogs(id));
    }
    
    /**
     * Obtiene una instancia de Blog asociada a una instancia de Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param BlogsId Identificador de la instancia de Blog
     * @return
     *
     */
    @GET
    @Path("{BlogId: \\d+}")
    public BlogDetailDTO getBlogs(@PathParam("grupoId") Long grupoId, @PathParam("BlogId") Long BlogId) {
        return new BlogDetailDTO(grupoLogic.getBlog(grupoId, BlogId));
    }
    
    /**
     * Asocia un Blog existente a un Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param BlogsId Identificador de la instancia de Blog
     * @return Instancia de BlogDetailDTO que fue asociada a Grupo
     *
     */
    @POST
    @Path("{BlogId: \\d+}")
    public BlogDetailDTO addBlogs(@PathParam("grupoId") Long grupoId, @PathParam("BlogId") Long BlogId) throws BusinessException, NotFoundException {
        return new BlogDetailDTO(grupoLogic.addBlog(grupoId, BlogId));
    }
    
    /**
     * Desasocia un Blog existente de un Grupo existente
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param BlogsId Identificador de la instancia de Blog
     *
     */
    @DELETE
    @Path("{BlogId: \\d+}")
    public void removeBlogs(@PathParam("grupoId") Long grupoId, @PathParam("BlogId") Long BlogId) {
        grupoLogic.removeBlog(grupoId, BlogId);
    }
}
