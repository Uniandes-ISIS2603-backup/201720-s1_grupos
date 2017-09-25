/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.PatrocinioDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author tefa
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioPatrocinio {
    
    /**
     * Se inyecta la logica del usuario
     */
    @Inject
    private UsuarioLogic usuarioLogic; 
    
    @GET
    public List<PatrocinioDetailDTO> getEmpresa(@PathParam("usuarioId") Long usuarioId) throws BusinessException{
        return null;
    }
}
