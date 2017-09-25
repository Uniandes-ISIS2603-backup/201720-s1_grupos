/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.EventoDTO;
import co.edu.uniandes.csw.grupos.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.GrupoLogic;
import co.edu.uniandes.csw.grupos.entities.EventoEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.exceptions.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * URI: grupos/{gruposId: \\d+}/eventos
 * @author cm.sarmiento10
 */
public class GrupoEventosResource {
    /**
     * Lógica del grupo
     */
    @Inject
    private GrupoLogic grupoLogic;
    
    /**
     * Convierte una lista de EventoEntity a una lista de EventoDetailDTO.
     *
     * @param entityList Lista de EventoEntity a convertir.
     * @return Lista de EventoDetailDTO convertida.
     *
     */
    private List<EventoDTO> EventosListEntity2DTO(List<EventoEntity> entityList) {
        List<EventoDTO> list = new ArrayList<>();
        for (EventoEntity entity : entityList) {
            list.add(new EventoDTO(entity));
        }
        return list;
    }
    
    /**
     * Convierte una lista de EventoDetailDTO a una lista de EventoEntity.
     *
     * @param dtos Lista de EventoDetailDTO a convertir.
     * @return Lista de EventoEntity convertida.
     *
     */
    private List<EventoEntity> EventosListDTO2Entity(List<EventoDetailDTO> dtos) {
        List<EventoEntity> list = new ArrayList<>();
        for (EventoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    /**
     * Obtiene una colección de instancias de EventoDetailDTO asociadas a una
     * instancia de Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @return Colección de instancias de EventoDetailDTO asociadas a la
     * instancia de Grupo
     *
     */
    @GET
    public List<EventoDTO> listEventos(@PathParam("grupoId") Long grupoId) {
        return EventosListEntity2DTO(grupoLogic.listEventos(grupoId));
    }
    
    /**
     * Obtiene una instancia de Evento asociada a una instancia de Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param EventosId Identificador de la instancia de Evento
     * @return
     *
     */
    @GET
    @Path("{EventoId: \\d+}")
    public EventoDetailDTO getEventos(@PathParam("grupoId") Long grupoId, @PathParam("EventoId") Long EventoId) {
        return new EventoDetailDTO(grupoLogic.getEvento(grupoId, EventoId));
    }
    
    /**
     * Asocia un Evento existente a un Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param EventosId Identificador de la instancia de Evento
     * @return Instancia de EventoDetailDTO que fue asociada a Grupo
     *
     */
    @POST
    @Path("{EventoId: \\d+}")
    public EventoDetailDTO addEventos(@PathParam("grupoId") Long grupoId, @PathParam("EventoId") Long EventoId) throws BusinessException, NotFoundException {
        return new EventoDetailDTO(grupoLogic.addEvento(grupoId, EventoId));
    }
    
    /**
     * Desasocia un Evento existente de un Grupo existente
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param EventosId Identificador de la instancia de Evento
     *
     */
    @DELETE
    @Path("{EventoId: \\d+}")
    public void removeEventos(@PathParam("grupoId") Long grupoId, @PathParam("EventoId") Long EventoId) {
        grupoLogic.removeEvento(grupoId, EventoId);
    }
}
