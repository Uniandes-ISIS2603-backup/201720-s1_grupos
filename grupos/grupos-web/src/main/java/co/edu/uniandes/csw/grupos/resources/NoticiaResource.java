/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.NoticiaDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.NoticiaLogic;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.exceptions.*;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author jc161
 */
@Path("noticias")
@Produces("application/json")
@Consumes("application/json")
@Stateless
public class NoticiaResource {
    @Inject
     NoticiaLogic logic;
    
    @GET
    public List<NoticiaDetailDTO> getNoticias()
    {
        List<NoticiaDetailDTO> noticias=listEntity2DetailDTO(logic.getAll());
        return noticias;
    }
    
    @GET
    @Path("{id: \\d+}")
    public NoticiaDetailDTO getNoticia(@PathParam("id") Long id) throws BusinessException, NotFoundException
    {
        NoticiaEntity entity=logic.getEntity(id);
        return new NoticiaDetailDTO(entity);
    }
    
    
    private List<NoticiaDetailDTO> listEntity2DetailDTO(List<NoticiaEntity> entityList) {
        List<NoticiaDetailDTO> list = new ArrayList<>();
        for (NoticiaEntity entity : entityList) {
            list.add(new NoticiaDetailDTO(entity));
        }
        return list;
    }
}
