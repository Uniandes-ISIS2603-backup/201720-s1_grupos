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
 * Subrecurso de noticiaComentario.<br>
 * @author se.cardenas
 */
@Produces("application/json")
@Consumes("application/json")
public class NoticiaComentarioResource {
    /**
     * ComentarioLogic
     */
    @Inject
    private ComentarioLogic comentarioLogic;
    /**
     * Lista entidades a dtos.<br>
     * @param list Listado de entidades.<br>
     * @return  Listado a forma de dtos.
     */
    private List<ComentarioDetailDTO> comentarioListEntity2DTO(List<ComentarioEntity> list) {
        List<ComentarioDetailDTO> coments = new ArrayList<>();
        for(ComentarioEntity entity : list) {
            coments.add(new ComentarioDetailDTO(entity));
        }
        return coments;
    }
    /**
     * Obtiene los comentarios de una noticia.<br>
     * @param noticiaId Id de noticia.<br>
     * @return Comentarios de una noticia.<br>
     * @throws BusinessException Excepción de negocio.
     */
    @GET
    public List<ComentarioDetailDTO> getComentarios( @PathParam("noticiaId") Long noticiaId) throws BusinessException {
        return comentarioListEntity2DTO(comentarioLogic.getComentariosNoticia(noticiaId));
    }
    /**
     * Obtiene un comentario de una noticia.<br>
     * @param noticiaId Id de noticia.<br>
     * @param comentarioId Id de comentario.<br>
     * @return Id de comentario.<br>
     * @throws BusinessException Excepción de negocio.
     */
    @GET
    @Path("{comentarioId: \\d+}")
    public ComentarioDetailDTO getComentario( @PathParam("noticiaId") Long noticiaId, @PathParam("comentarioId") Long comentarioId) throws BusinessException {
        return new ComentarioDetailDTO(comentarioLogic.getComentarioNoticia( noticiaId, comentarioId));
    }
    /**
     * Crea un nuevo comentario para la noticia.<br>
     * @param noticiaId Id de noticia.<br>
     * @param comentario COmentario de la noticia.<br>
     * @return DTO.<br>
     * @throws BusinessException Excepción de negocio.
     */
    @POST
    public ComentarioDetailDTO createComentario( @PathParam("noticiaId") Long noticiaId, ComentarioDetailDTO comentario) throws BusinessException {
        return new ComentarioDetailDTO(comentarioLogic.createComentarioNoticia( noticiaId, comentario.toEntity()));
    }
    /**
     * Actualiza un comentario de noticia.<br>
     * @param noticiaId Id de noticia.<br>
     * @param comentarioId Id de comentario.<br>
     * @param comentario Comentario para actualizar.<br>
     * @return DTO detallado.<br>
     * @throws BusinessException Excepción de negocio.
     */
    @PUT
    @Path("{comentarioId: \\d+}")
    public ComentarioDetailDTO updateComentario( @PathParam("noticiaId") Long noticiaId, @PathParam("comentarioId") Long comentarioId, ComentarioDetailDTO comentario) throws BusinessException {
        ComentarioEntity entity = comentario.toEntity();
        entity.setId(comentarioId);
        return new ComentarioDetailDTO(comentarioLogic.updateComentarioNoticia(noticiaId, entity));
    }
    /**
     * Borra de comentario por id.<br>
     * @param noticiaId Id de noticia.<br>
     * @param comentarioId Id de comentario.<br>
     * @throws BusinessException Excepción de negocio.
     */
    @DELETE
    @Path("{comentarioId: \\d+}")
    public void deleteComentario(@PathParam("noticiaId") Long noticiaId, @PathParam("comentarioId") Long comentarioId) throws BusinessException {
        comentarioLogic.deleteComentarioNoticia(noticiaId, comentarioId);
    }
}
