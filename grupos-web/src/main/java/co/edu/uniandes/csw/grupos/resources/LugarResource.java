/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.LugarDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.LugarLogic;
import co.edu.uniandes.csw.grupos.entities.LugarEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.exceptions.NotFoundException;
import java.util.ArrayList;
import java.util.List;
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

/**
 *
 * @author js.ramos14
 */
@Path("lugares")
@Produces("application/json")
@Consumes("application/json")
@Stateless
public class LugarResource {
    
    @Inject
    LugarLogic logic;
    
    @GET
    public List<LugarDetailDTO> getLugares()
    {
        List<LugarDetailDTO> list = listEntityToDetailDTO(logic.getAll());
        return list;
    }
    @GET
    @Path("{id: \\d+}")
    public LugarDetailDTO getLugar(@PathParam("id") Long id) throws BusinessException, NotFoundException
    {
        LugarEntity entity=logic.getEntity(id);
        return new LugarDetailDTO(entity);
    }
    
    @PUT
    @Path("{id: \\d+}")
    public LugarDetailDTO updateLugar(@PathParam("id") Long id) throws BusinessException, NotFoundException
    {
       LugarEntity entity =logic.getEntity(id);
       return new LugarDetailDTO(logic.updateEntity(entity, null));
       
    }
    @POST
    public LugarDetailDTO createLugar(LugarDetailDTO lugar) throws BusinessException
    {
        LugarEntity entity = lugar.toEntity();
        return new LugarDetailDTO(logic.createEntity(entity,null));
    }
    
    @DELETE
    @Path("{id: \\d+}")
    public void deleteLugar(@PathParam("id")Long id) throws BusinessException, NotFoundException
    {
        LugarEntity entity = logic.getEntity(id);
        logic.deleteEntity(entity, null);
    }
    
    private List<LugarDetailDTO> listEntityToDetailDTO(List<LugarEntity> entityList) {
        List<LugarDetailDTO> list = new ArrayList<>();
        for (LugarEntity entity : entityList) {
            list.add(new LugarDetailDTO(entity));
        }
        return list;
    }
}
