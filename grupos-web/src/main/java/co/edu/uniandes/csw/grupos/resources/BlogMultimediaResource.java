/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.MultimediaDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.BlogLogic;
import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import javax.ws.rs.NotFoundException;
import co.edu.uniandes.csw.grupos.persistence.CalificacionPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author jc161
 */
public class BlogMultimediaResource {
    /**
     * Lógica el padre.
     */
    @Inject
    private BlogLogic blog;
    
    /**
     * Lista dtos en entidades.<br>
     * @param list Lista de dtos.<br>
     * @return Lista de entidades.
     */    
    private List<MultimediaEntity> listarDTO(List<MultimediaDetailDTO> list)
    {
        ArrayList<MultimediaEntity> m = new ArrayList<>();
        for(MultimediaDetailDTO d:list) m.add(d.toEntity());
        return m;
    }
    /**
     * Lista entidades en dtos.<br>
     * @param list Lista de entidades.<br>
     * @return Listya de dtos.
     */
    private List<MultimediaDetailDTO> toDTO(List<MultimediaEntity> list)
    {
        ArrayList<MultimediaDetailDTO> m = new ArrayList<>();
        for(MultimediaEntity d:list) m.add(new MultimediaDetailDTO(d));
        return m;
    }
    /**
     * Retorna lista de dtos.<br>
     * @param id Id del blog.<br>
     * @return Lista de dtos.<br>
     * @throws BusinessException  Excepción de negocio
     */
    @GET
    public List<MultimediaDetailDTO> getMultimedia(@PathParam("blogId")Long id) throws BusinessException
    {
        return toDTO(blog.getMultimedia(id));
    }
    /**
     * Obtiene multimedia con un link.<br>
     * @param id Id del blog.<br>
     * @param link Link de la multimedia.<br>
     * @return Multimedia.<br>
     * @throws BusinessException Errores de negocio 
     */
    @GET 
    @Path("{link: [a-zA-Z]+}")
    public MultimediaDetailDTO getMultimediaNoticia(@PathParam("blogId") Long id,@PathParam("link") String link) throws BusinessException
    {
        return new MultimediaDetailDTO(blog.getMultimedia(id,link));
    }
    /**
     * Agrega una multiemdia de grupo.<br>
     * @param grupoId Id del grupo.<br>
     * @param id Id del blog.<br>
     * @param multimedia Lista de multimedia.<br>
     * @return Lista de dtos.<br>
     * @throws BusinessException Excepción de negocio.
     */
    @POST
    public List<MultimediaDetailDTO> postMultimedia (@PathParam("grupoId") Long grupoId,@PathParam("blogId")Long id, List<MultimediaDetailDTO> multimedia) throws BusinessException
    {
        List<MultimediaEntity> mult = listarDTO(multimedia);
        return toDTO(blog.addMultipleMultimedia(grupoId, id, mult));
    }
    /**
     * Actualiza la multimedia con el dto dado.<br>
     * @param grupoId Id del grupo.<br>
     * @param id Id del blog.<br>
     * @param link Link de la multimedia.<br>
     * @param dto Dto de multimedia.<br>
     * @return Lista actualizada.<br>
     * @throws BusinessException  Excepción de negocio.
     */
    @PUT
    @Path("{link: [a-zA-Z]+}")
    public List<MultimediaDetailDTO> updateMultimedia (@PathParam("grupoId") Long grupoId, @PathParam("blogId") Long id, @PathParam("link")String link, MultimediaDetailDTO dto) throws BusinessException
    {
        MultimediaEntity mult = dto.toEntity();
       return toDTO(blog.updateMultimedia(grupoId,id, mult, link));
    }
    /**
     * +Borra una multimedia.<br>
     * @param id Id del blog.<br>
     * @param link Link de multimedia.<br>
     * @throws BusinessException Excepción de negocio.<br>
     */
    @DELETE
    @Path("{link: [a-zA-Z]+}")
    public void deleteMultimedia(@PathParam("blogId") Long id, @PathParam("link")String link) throws BusinessException
    {
        blog.deleteMultimedia(id,link);
    }
}
