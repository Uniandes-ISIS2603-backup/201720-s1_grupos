/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.MultimediaDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.MultimediaLogic;
import co.edu.uniandes.csw.grupos.ejb.NoticiaLogic;
import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.CalificacionPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author jc161
 */
@Consumes (MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NoticiaMultimediaResource {
    
    @Inject
    private NoticiaLogic noticia;
    
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
    public List<MultimediaDetailDTO> getMultimedia(@PathParam("noticiaid")Long id) throws BusinessException
    {
        return toDTO(noticia.getMultimedia(id));
    }
    
    @GET 
    @Path("{link: [a-zA-Z]+}")
    public MultimediaDetailDTO getMultimediaNoticia(@PathParam("noticiaid") Long id,@PathParam("link") String link) throws BusinessException
    {
        return new MultimediaDetailDTO(noticia.getMultimedia(id, link));
    }
    
    @POST
    public List<MultimediaDetailDTO> postMultimedia (@PathParam("noticiaid")Long id, List<MultimediaDetailDTO> multimedia) throws BusinessException
    {
        List<MultimediaEntity> mult = listarDTO(multimedia);
        return toDTO(noticia.addMultimedia(id, mult));
    }
    @PUT
    @Path("{link: [a-zA-Z]+}")
    public List<MultimediaDetailDTO> updateMultimedia (@PathParam("noticiaid") Long id, @PathParam("link")String link, MultimediaDetailDTO dto) throws BusinessException
    {
        MultimediaEntity mult = dto.toEntity();
       return toDTO(noticia.updateMultimedia(id, mult, link));
    }
    
    @DELETE
    @Path("{link: [a-zA-Z]+}")
    public void deleteMultimedia(@PathParam("noticiaid") Long id, @PathParam("link")String link) throws BusinessException
    {
        noticia.deleteMultimedia(id,link);
    }
}
