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
    
    @Inject
    private BlogLogic blog;
    
       private static final Logger LOGGER = Logger.getLogger(CalificacionPersistence.class.getName());
        
    private List<MultimediaEntity> listarDTO(List<MultimediaDetailDTO> list)
    {
        ArrayList<MultimediaEntity> m = new ArrayList<>();
        for(MultimediaDetailDTO d:list) m.add(d.toEntity());
        return m;
    }
    
    private List<MultimediaDetailDTO> toDTO(List<MultimediaEntity> list)
    {
        ArrayList<MultimediaDetailDTO> m = new ArrayList<>();
        for(MultimediaEntity d:list) m.add(new MultimediaDetailDTO(d));
        return m;
    }
    
    @GET
    public List<MultimediaDetailDTO> getMultimedia(@PathParam("blogId")Long id) throws BusinessException
    {
        return toDTO(blog.getMultimedia(id));
    }
    
    @GET 
    @Path("{link: [a-zA-Z]+}")
    public MultimediaDetailDTO getMultimediaNoticia(@PathParam("blogId") Long id,@PathParam("link") String link) throws BusinessException
    {
        return new MultimediaDetailDTO(blog.getMultimedia(id,link));
    }
    
    @POST
    public List<MultimediaDetailDTO> postMultimedia (@PathParam("grupoId") Long grupoId,@PathParam("blogId")Long id, List<MultimediaDetailDTO> multimedia) throws BusinessException
    {
        List<MultimediaEntity> mult = listarDTO(multimedia);
        return toDTO(blog.addMultipleMultimedia(grupoId, id, mult));
    }
    @PUT
    @Path("{link: [a-zA-Z]+}")
    public List<MultimediaDetailDTO> updateMultimedia (@PathParam("grupoId") Long grupoId, @PathParam("blogId") Long id, @PathParam("link")String link, MultimediaDetailDTO dto) throws BusinessException
    {
        MultimediaEntity mult = dto.toEntity();
       return toDTO(blog.updateMultimedia(grupoId,id, mult, link));
    }
    
    @DELETE
    @Path("{link: [a-zA-Z]+}")
    public void deleteMultimedia(@PathParam("blogId") Long id, @PathParam("link")String link) throws BusinessException
    {
        blog.deleteMultimedia(id,link);
    }
}
