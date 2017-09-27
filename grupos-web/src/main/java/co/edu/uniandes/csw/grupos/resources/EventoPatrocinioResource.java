/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.PatrocinioDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.EventoLogic;
import co.edu.uniandes.csw.grupos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Lógica de evento.<br>
 * @author tefa
 */
public class EventoPatrocinioResource {
    /**
     * Se inyecta la logica del usuario
     */
    @Inject
    private EventoLogic eventoLogic;
    
    /**
     * Da todos los patrocinios asociados con el evento
     * @param eventoId identificador del evento
     * @return la lista con todos los patrocinios del evento
     * @throws BusinessException 
     */
    @GET
     public List<PatrocinioDetailDTO> getPatrocinios(@PathParam("id") Long eventoId) throws BusinessException{
        List<PatrocinioEntity> pat = eventoLogic.listPatrocinios(eventoId);
        List<PatrocinioDetailDTO> ret = new ArrayList<PatrocinioDetailDTO>();
        for(PatrocinioEntity p : pat)
        {
            ret.add(new PatrocinioDetailDTO(p));
        }
        return ret;
    }
    /**
     * Obtiene un patrocinio específico.<br>
     * @param id Id del evento.<br>
     * @param patId Id del patrocinio.<br>
     * @return DTO del patrocinio.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException SI no se encuentra.
     */
    @GET
    @Path("{patId:\\d+}")
    public PatrocinioDetailDTO getPatrocinio(@PathParam("id") Long id, @PathParam("patId") Long patId) throws BusinessException{
        PatrocinioEntity e= eventoLogic.getPatrocinio(id, patId);
        if(e==null) throw new NotFoundException("No se encuentra el patrocinio buscado");
        return new PatrocinioDetailDTO(e);
    }
    /**
     * Agrega un nuevo patrocinio.<br>
     * @param id Id del evento.<br>
     * @param patId Patrocinio creado.<br>
     * @return DTO del patrocinio.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException Si no se encuentra algo.
     */
    @POST
    @Path("{patId: \\d+}")
    public PatrocinioDetailDTO addPatrocinios(@PathParam("id") Long id, @PathParam("patId") Long patId) throws BusinessException {
        return new PatrocinioDetailDTO(eventoLogic.addPatrocinio(id, patId));
    }
    /**
     * Remueve el patrocinio del evento.<br>
     * @param id Id del evento.<br>
     * @param patId Id del patrocinio.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException Si no se encuentra.
     */
     @DELETE
    @Path("{patId: \\d+}")
    public void removePatrocinio(@PathParam("id") Long id, @PathParam("patId") Long patId) throws BusinessException{
        eventoLogic.removePatrocinio(id, patId);
    }
}