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
 *
 * @author se.cardenas
 */
@Produces("application/json")
@Consumes("application/json")
public class NoticiaComentarioResource {
    
    @Inject
    private ComentarioLogic comentarioLogic;
    
    private List<ComentarioDetailDTO> comentarioListEntity2DTO(List<ComentarioEntity> list) {
        List<ComentarioDetailDTO> coments = new ArrayList<>();
        for(ComentarioEntity entity : list) {
            coments.add(new ComentarioDetailDTO(entity));
        }
        return coments;
    }
    
    @GET
    public List<ComentarioDetailDTO> getComentarios( @PathParam("noticiaId") Long noticiaId) throws BusinessException {
        return comentarioListEntity2DTO(comentarioLogic.getComentariosNoticia(noticiaId));
    }
    
    @GET
    @Path("{comentarioId: \\d+}")
    public ComentarioDetailDTO getComentario( @PathParam("noticiaId") Long noticiaId, @PathParam("comentarioId") Long comentarioId) throws BusinessException {
        return new ComentarioDetailDTO(comentarioLogic.getComentarioNoticia( noticiaId, comentarioId));
    }
    
    @POST
    public ComentarioDetailDTO createComentario( @PathParam("noticiaId") Long noticiaId, ComentarioDetailDTO comentario) throws BusinessException {
        return new ComentarioDetailDTO(comentarioLogic.createComentarioNoticia( noticiaId, comentario.toEntity()));
    }
    
    @PUT
    @Path("{comentarioId: \\d+}")
    public ComentarioDetailDTO updateComentario( @PathParam("noticiaId") Long noticiaId, @PathParam("comentarioId") Long comentarioId, ComentarioDetailDTO comentario) throws BusinessException {
        ComentarioEntity entity = comentario.toEntity();
        entity.setId(comentarioId);
        return new ComentarioDetailDTO(comentarioLogic.updateComentarioNoticia(noticiaId, entity));
    }
    
    @DELETE
    @Path("{comentarioId: \\d+}")
    public void deleteComentario(@PathParam("noticiaId") Long noticiaId, @PathParam("comentarioId") Long comentarioId) throws BusinessException {
        comentarioLogic.deleteComentarioNoticia(noticiaId, comentarioId);
    }
}
