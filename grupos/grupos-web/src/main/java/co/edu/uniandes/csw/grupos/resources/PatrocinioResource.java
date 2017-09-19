/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.PatrocinioDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.PatrocinioLogic;
import co.edu.uniandes.csw.grupos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author tefa
 */
@Path("patrocinios")
@Produces("application/json")
@Consumes("application/json")
@Stateless
public class PatrocinioResource {
    
    /**
     * Se inyecta la logica del patrocinio
     */
    @Inject
    PatrocinioLogic patrocinioLogic;
    
    @GET
    public List<PatrocinioDetailDTO> allPatrocinios() throws BusinessException
    {
        List<PatrocinioEntity> patro = patrocinioLogic.allPatrocinios();
        List<PatrocinioDetailDTO> ret = new ArrayList<PatrocinioDetailDTO>();
        for(PatrocinioEntity p:patro)
        {
            ret.add(new PatrocinioDetailDTO(p));
        }
        return ret;
    }
}
