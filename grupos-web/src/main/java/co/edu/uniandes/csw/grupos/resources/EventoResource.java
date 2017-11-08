/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.grupos.dtos.LugarDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.EventoLogic;
import co.edu.uniandes.csw.grupos.entities.EventoEntity;
import co.edu.uniandes.csw.grupos.entities.LugarEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * Recurso de evento.<br>
 * @author js.ramos14
 */
@Path("eventos")
@Produces("application/json")
@Consumes("application/json")
@Stateless
public class EventoResource {
    /**
     * Lógica
     */
    @Inject
            EventoLogic logic;
    /**
     * Obtiene todos los eventos.<br>
     * @return  Listado de dtos.
     */
    @GET
    public List<EventoDetailDTO> getEventos()
    {
        List<EventoDetailDTO> list = listEntityToDetailDTO(logic.getAll());
        return list;
    }
    /**
     * Obtiene el lugar del evento.<br>
     * @param eventoId id del evento.
     * @return  DTO del lugar.
     * @throws BusinessException Excepción de negocio.<br>
     */
    @GET
    @Path("{eventoId: \\d+}/lugar")
    public LugarDetailDTO getLugar(@PathParam("eventoId") Long eventoId) throws BusinessException
    {
                EventoEntity evento = logic.getEntity(eventoId);
        if (evento == null) {
            throw new WebApplicationException("El evento no existe", 404);
        }
        LugarEntity lugar = evento.getLugar();
        if(lugar == null){
            throw new WebApplicationException("El evento con id " + eventoId + " no tiene lugar asociado", 404);
        }
        return new LugarDetailDTO(lugar);
    }
    /**
     * Obtiene el dto de evento.<br>
     * @param id Id del evento.<br>
     * @return DTO del evento.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException Si no se encuentra.
     */
    @GET
    @Path("{id: \\d+}")
    public EventoDetailDTO getEvento(@PathParam("id") Long id) throws BusinessException
    {
        try
        {
            EventoEntity entity=logic.getEntity(id);
            return new EventoDetailDTO(entity);
        }
        catch(javax.ejb.EJBTransactionRolledbackException e)
        {
            throw new NotFoundException();
        }
    }
    /**
     * Actualiza el evento.<br>
     * @param id Id del evento.<r>
     * @param dto DTO a actualizar.<br>
     * @return Evento actualizado.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException  No se encuentra.
     */
    @PUT
    @Path("{id: \\d+}")
    public EventoDetailDTO updateEvento(@PathParam("id") Long id, EventoDetailDTO dto) throws BusinessException, NotFoundException
    {
        EventoEntity entity =null;
        try
        {
            entity=logic.getEntity(id);
        }
        catch(javax.ejb.EJBTransactionRolledbackException e)
        {
            throw new NotFoundException();
        }
        
        if(entity==null || dto==null) throw new NotFoundException("No existe el vento a actualizar.");
        EventoEntity newEntity=dto.toEntity();
        newEntity.setId(id);
        newEntity.setGrupo(entity.getGrupo());
        newEntity.setUsuarios(entity.getUsuarios());
        return new EventoDetailDTO(logic.updateEntity(newEntity));
    }
    /**
     * Crea un nuevo evento.<br>
     * @param Evento Evento a crear.<br>
     * @return Evento creado.<br>
     * @throws BusinessException Excepción de negocio.
     */
    @POST
    public EventoDetailDTO createEvento(EventoDetailDTO Evento) throws BusinessException
    {
        EventoEntity entity = Evento.toEntity();
        EventoEntity e=logic.createEntity(entity);
        return new EventoDetailDTO(e);
    }
    /**
     * Borra un evento con el id dado.<br>
     * @param id Id del evento.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException Si no se encuentra algo.<br>
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteEvento(@PathParam("id")Long id) throws BusinessException
    {
        EventoEntity entity = null;
         try
        {
            entity=logic.getEntity(id);
        }
        catch(javax.ejb.EJBTransactionRolledbackException e)
        {
            throw new NotFoundException();
        }
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
    public Class<EventoPatrocinioResource> getPatrocinioResource(@PathParam("id") Long id) throws BusinessException
    {
        EventoEntity entity =null;
         try
        {
            entity=logic.getEntity(id);
        }
        catch(javax.ejb.EJBTransactionRolledbackException e)
        {
            throw new NotFoundException();
        }
        if(entity == null)
        {
            throw new WebApplicationException("El recurso /eventos/" + id + "/patrocinios no existe.", 404);
        }
        return EventoPatrocinioResource.class;
    }
    /**
     * Subrecurso EventoUsuario.<br>
     * @param id Id del evento .<br>
     * @return Calse de evento usuario.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException Excepción de no encontrado.
     */
    @Path("{id: \\d+}/usuarios")
    public Class<EventoUsuariosResource> getEventoUsuarioResource(@PathParam("id") Long id) throws BusinessException, NotFoundException {
        EventoEntity entity =null;
         try
        {
            entity=logic.getEntity(id);
        }
        catch(javax.ejb.EJBTransactionRolledbackException e)
        {
            throw new NotFoundException();
        }
        if (entity == null)
        {
            throw new WebApplicationException("El recurso /eventos/" + id + "/patrocinios no existe.", 404);
        }
        return EventoUsuariosResource.class;
    }
    /**
     * Convierte las entidades en dtos.<br>
     * @param entityList Lista de entidades.<br>
     * @return  Lista en forma de dtos.
     */
    private List<EventoDetailDTO> listEntityToDetailDTO(List<EventoEntity> entityList) {
        List<EventoDetailDTO> list = new ArrayList<>();
        for (EventoEntity entity : entityList) {
            list.add(new EventoDetailDTO(entity));
        }
        return list;
    }
}
