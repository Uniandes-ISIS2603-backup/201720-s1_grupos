/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.ComentarioDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.ComentarioLogic;
import co.edu.uniandes.csw.grupos.entities.ComentarioEntity;
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
import javax.ws.rs.Produces;

/**
 * URI: grupos/{grupoId: \\d+}/blogs/{blogId: \\d+}/comentarios
 * @author se.cardenas
 */
@Produces("application/json")
@Consumes("application/json")
public class BlogComentarioResource {
    /**
     * Lógica de comentario
     */
    @Inject
    ComentarioLogic comentarioLogic;
    /**
     * Lista de dtos de comentarios a partir de entidades.<br>
     * @param list Listado de entidades.<br>
     * @return Lista de detail dtos
     */
    private List<ComentarioDetailDTO> comentarioListEntity2DTO(List<ComentarioEntity> list) {
        List<ComentarioDetailDTO> coments = new ArrayList<>();
        for(ComentarioEntity entity : list) {
            coments.add(new ComentarioDetailDTO(entity));
        }
        return coments;
    }
    /**
     * Obtiene una lista de dtos de los comentarios.<br>
     * @param grupoId Id de grupo.<br>
     * @param blogId Id de blog.<br>
     * @return Listado de dtos.
     */
    @GET
    public List<ComentarioDetailDTO> getComentarios(@PathParam("grupoId") Long grupoId, @PathParam("blogId") Long blogId) {
        return comentarioListEntity2DTO(comentarioLogic.getComentariosBlog(grupoId, blogId));
    }
    /**
     * Obtiene un dto específico de tipo comentario.<br>
     * @param grupoId Id de grupo.<br>
     * @param blogId Id de blog.<br>
     * @param comentarioId Id de comentario.<br>
     * @return  DTo de comentario
     */
    @GET
    @Path("{comentarioId: \\d+}")
    public ComentarioDetailDTO getComentario(@PathParam("grupoId") Long grupoId, @PathParam("blogId") Long blogId, @PathParam("comentarioId") Long comentarioId) {
        return new ComentarioDetailDTO(comentarioLogic.getComentarioBlog(grupoId, blogId, comentarioId));
    }
    /**
     * Crea un nuevo comentario.<br>
     * @param grupoId Id de grupo.<br>
     * @param blogId Id de blog.<br>
     * @param comentario Comentario a crear.<br>
     * @return Comentario en dto creado.<br>
     * @throws BusinessException Si hay excepciones de negocio.
     */
    @POST
    public ComentarioDetailDTO createComentario(@PathParam("grupoId") Long grupoId, @PathParam("blogId") Long blogId, ComentarioDetailDTO comentario) throws BusinessException {
        return new ComentarioDetailDTO(comentarioLogic.createComentarioBlog(grupoId, blogId, comentario.toEntity()));
    }
    /**
     * Actualiza el dto de comentario al valor dado por parámetro.<br>
     * @param grupoId Id de grupo.<br>
     * @param blogId Id de blog.<br>
     * @param comentarioId Id de comentario.<br>
     * @param comentario Comentario.<br>
     * @return Dto de comentario.
     */
    @PUT
    @Path("{comentarioId: \\d+}")
    public ComentarioDetailDTO updateComentario(@PathParam("grupoId") Long grupoId, @PathParam("blogId") Long blogId, @PathParam("comentarioId") Long comentarioId, ComentarioDetailDTO comentario) {
        ComentarioEntity entity = comentario.toEntity();
        entity.setId(comentarioId);
        return new ComentarioDetailDTO(comentarioLogic.updateComentarioBlog(grupoId, blogId, entity));
    }
    /**
     * Borra un comentario con id dado.<br>
     * @param grupoId Id de grupo.<br>
     * @param blogId Id de blog.<br>
     * @param comentarioId  Id de comentario.<br>
     */
    @DELETE
    @Path("{comentarioId: \\d+}")
    public void deleteComentario(@PathParam("grupoId") Long grupoId, @PathParam("blogId") Long blogId, @PathParam("comentarioId") Long comentarioId) {
        comentarioLogic.deleteComentarioBlog(grupoId, blogId, comentarioId);
    }
}
