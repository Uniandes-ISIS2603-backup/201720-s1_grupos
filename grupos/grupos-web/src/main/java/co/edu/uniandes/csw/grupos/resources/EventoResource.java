/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.EventoLogic;
import co.edu.uniandes.csw.grupos.entities.EventoEntity;
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
@Path("Eventoes")
@Produces("application/json")
@Consumes("application/json")
@Stateless
public class EventoResource {
    
        @Inject
    EventoLogic logic;
    
    @GET
    public List<EventoDetailDTO> getEventoes()
    {
        List<EventoDetailDTO> list = listEntityToDetailDTO(logic.getAll());
        return list;
    }
    @GET
    @Path("{id: \\d+}")
    public EventoDetailDTO getEvento(@PathParam("id") Long id) throws BusinessException, NotFoundException
    {
        EventoEntity entity=logic.getEntity(id);
        return new EventoDetailDTO(entity);
    }
    
    @PUT
    @Path("{id: \\d+}")
    public EventoDetailDTO updateEvento(@PathParam("id") Long id) throws BusinessException, NotFoundException
    {
       EventoEntity entity =logic.getEntity(id);
       return new EventoDetailDTO(logic.updateEntity(entity, null));
       
    }
    @POST
    public EventoDetailDTO createEvento(EventoDetailDTO Evento) throws BusinessException
    {
        EventoEntity entity = Evento.toEntity();
        return new EventoDetailDTO(logic.createEntity(entity,null));
    }
    
    @DELETE
    @Path("{id: \\d+}")
    public void deleteEvento(@PathParam("id")Long id) throws BusinessException, NotFoundException
    {
        EventoEntity entity = logic.getEntity(id);
        logic.deleteEntity(entity, null);
    }
    
    private List<EventoDetailDTO> listEntityToDetailDTO(List<EventoEntity> entityList) {
        List<EventoDetailDTO> list = new ArrayList<>();
        for (EventoEntity entity : entityList) {
            list.add(new EventoDetailDTO(entity));
        }
        return list;
    }
}
