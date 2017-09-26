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
 *
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
     public List<PatrocinioDetailDTO> getPatrocinios(@PathParam("id") Long eventoId) throws BusinessException, NotFoundException{
        List<PatrocinioEntity> pat = eventoLogic.listPatrocinios(eventoId);
        List<PatrocinioDetailDTO> ret = new ArrayList<PatrocinioDetailDTO>();
        for(PatrocinioEntity p : pat)
        {
            ret.add(new PatrocinioDetailDTO(p));
        }
        return ret;
    }
     
    @GET
    @Path("{patId:\\d+}")
    public PatrocinioDetailDTO getPatrocinio(@PathParam("id") Long id, @PathParam("patId") Long patId) throws BusinessException, NotFoundException {
        PatrocinioEntity e= eventoLogic.getPatrocinio(id, patId);
        if(e==null) throw new NotFoundException("No se encuentra el patrocinio buscado");
        return new PatrocinioDetailDTO(e);
    }
    
    @POST
    @Path("{patId: \\d+}")
    public PatrocinioDetailDTO addPatrocinios(@PathParam("id") Long id, @PathParam("patId") Long patId) throws BusinessException, NotFoundException {
        return new PatrocinioDetailDTO(eventoLogic.addPatrocinio(id, patId));
    }
    
     @DELETE
    @Path("{patId: \\d+}")
    public void removePatrocinio(@PathParam("id") Long id, @PathParam("patId") Long patId) throws BusinessException, NotFoundException {
        eventoLogic.removePatrocinio(id, patId);
    }
}