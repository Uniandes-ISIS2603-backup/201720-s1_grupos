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
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author js.ramos14
 */
@Path("eventos")
@Produces("application/json")
@Consumes("application/json")
@Stateless
public class EventoResource {
    
    @Inject
    EventoLogic logic;
    
    @GET
    public List<EventoDetailDTO> getEventos()
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
    public EventoDetailDTO updateEvento(@PathParam("id") Long id, EventoDetailDTO dto) throws BusinessException, NotFoundException
    {
       EventoEntity entity =logic.getEntity(id);
       if(entity==null || dto==null) throw new NotFoundException("No existe el vento a actualizar.");
       EventoEntity newEntity=dto.toEntity();
       newEntity.setId(id);
       newEntity.setGrupo(entity.getGrupo());
       newEntity.setUsuarios(entity.getUsuarios());
       return new EventoDetailDTO(logic.updateEntity(newEntity));
    }
    
    @POST
    public EventoDetailDTO createEvento(EventoDetailDTO Evento) throws BusinessException
    {
        EventoEntity entity = Evento.toEntity();
        EventoEntity e=logic.createEntity(entity);
        return new EventoDetailDTO(e);
    }
    
    @DELETE
    @Path("{id: \\d+}")
    public void deleteEvento(@PathParam("id")Long id) throws BusinessException, NotFoundException
    {
        EventoEntity entity = logic.getEntity(id);
        logic.deleteEntity(entity);
    }
    
    /**
     * Inicializa el subrecurso de patrocinios
     * @param pid identificador del evento
     * @return clase
     * @throws WebApplicationException
     * @throws BusinessException
     * @throws NotFoundException 
     */
    @Path("{id: \\d+}/patrocinios")
    public Class<EventoPatrocinioResource> getPatrocinioResource(@PathParam("id") Long id) throws BusinessException, NotFoundException
    {
        EventoEntity entity = logic.getEntity(id);
        if(entity == null)
        {
            throw new WebApplicationException("El recurso /eventos/" + id + "/patrocinios no existe.", 404);
        }
        return EventoPatrocinioResource.class;
    }
    
    @Path("{id: \\d+}/usuarios")
    public Class<EventoUsuariosResource> getEventoUsuarioResource(@PathParam("id") Long id) throws BusinessException, NotFoundException {
        EventoEntity entity = logic.getEntity(id);
        if (entity == null) 
        {
            throw new WebApplicationException("El recurso /eventos/" + id + "/patrocinios no existe.", 404);
        }
        return EventoUsuariosResource.class;
    }

    private List<EventoDetailDTO> listEntityToDetailDTO(List<EventoEntity> entityList) {
        List<EventoDetailDTO> list = new ArrayList<>();
        for (EventoEntity entity : entityList) {
            list.add(new EventoDetailDTO(entity));
        }
        return list;
    }
}
