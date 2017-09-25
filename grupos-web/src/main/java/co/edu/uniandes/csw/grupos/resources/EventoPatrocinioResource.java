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
import javax.ws.rs.GET;
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
    public List<PatrocinioDetailDTO> getPatrocinios(@PathParam("usuarioId") Long eventoId) throws BusinessException, NotFoundException{
        List<PatrocinioEntity> pat = eventoLogic.getPatrocinios(eventoId);
        List<PatrocinioDetailDTO> ret = new ArrayList<PatrocinioDetailDTO>();
        for(PatrocinioEntity p : pat)
        {
            ret.add(new PatrocinioDetailDTO(p));
        }
        return ret;
    }
}