/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.BlogDTO;
import co.edu.uniandes.csw.grupos.dtos.BlogDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.BlogLogic;
import co.edu.uniandes.csw.grupos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.TransactionRolledbackLocalException;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Recurso usuarioblog
 * @author cm.sarmiento10
 */
public class UsuarioBlogResource {
    /**
     * Lógica del usuario
     */
    @Inject
    private UsuarioLogic usuarioLogic;
     /**
     * Lógica del usuario
     */
    @Inject
    private BlogLogic blogLogic;
    
    /**
     * Convierte una lista de blogEntity a una lista de blogDetailDTO.
     *
     * @param entityList Lista de blogEntity a convertir.
     * @return Lista de blogDetailDTO convertida.
     *
     */
    private List<BlogDTO> blogsListEntity2DTO(List<BlogEntity> entityList) {
        List<BlogDTO> list = new ArrayList<>();
        for (BlogEntity entity : entityList) {
            list.add(new BlogDTO(entity));
        }
        return list;
    }
    
    /**
     * Convierte una lista de blogDetailDTO a una lista de blogEntity.
     *
     * @param dtos Lista de blogDetailDTO a convertir.
     * @return Lista de blogEntity convertida.
     *
     */
    private List<BlogEntity> blogsListDTO2Entity(List<BlogDetailDTO> dtos) {
        List<BlogEntity> list = new ArrayList<>();
        for (BlogDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    /**
     * Obtiene una colección de instancias de blogDetailDTO asociadas a una
     * instancia de usuario
     *
     * @param usuariosId Identificador de la instancia de usuario
     * @return Colección de instancias de blogDetailDTO asociadas a la
     * instancia de usuario
     *
     */
    @GET
    public List<BlogDTO> listBlogs(@PathParam("usuarioId") Long id) throws BusinessException {
        try
        {
            return blogsListEntity2DTO(usuarioLogic.listBlogs(id));

        }
        catch(javax.ejb.EJBTransactionRolledbackException e)
        {
            throw new NotFoundException("El usuario no existe.");
        }     
       
    }
    
    /**
     * Obtiene una instancia de blog asociada a una instancia de usuario
     *
     * @param usuariosId Identificador de la instancia de usuario
     * @param blogsId Identificador de la instancia de blog
     * @return
     *
     */
    @GET
    @Path("{blogId: \\d+}")
    public BlogDetailDTO getBlogs(@PathParam("usuarioId") Long usuarioId, @PathParam("blogId") Long blogId) {
        return new BlogDetailDTO(usuarioLogic.getBlog(usuarioId, blogId));
    }
    
    /**
     * Asocia un blog existente a un usuario
     *
     * @param usuariosId Identificador de la instancia de usuario
     * @param blogsId Identificador de la instancia de blog
     * @return Instancia de blogDetailDTO que fue asociada a usuario
     *
     */
    @POST
    @Path("{blogId: \\d+}")
    public BlogDetailDTO addBlogs(@PathParam("usuarioId") Long usuarioId, @PathParam("blogId") Long blogId) throws BusinessException {
        usuarioLogic.addBlog(usuarioId, blogLogic.getBlog(blogId));
        return new BlogDetailDTO(blogLogic.getBlog(blogId));
    }
    
    /**
     * Desasocia un blog existente de un usuario existente
     *
     * @param usuariosId Identificador de la instancia de usuario
     * @param blogsId Identificador de la instancia de blog
     *
     */
    @DELETE
    @Path("{blogId: \\d+}")
    public void removeBlogs(@PathParam("usuarioId") Long usuarioId, @PathParam("blogId") Long blogId) {
       usuarioLogic.removeBlog(usuarioId, blogId);
    }
}
