/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.PatrocinioDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.PatrocinioLogic;
import co.edu.uniandes.csw.grupos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.grupos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author tefa
 */
@Path("/usuarios/{usuarioId: \\d+}/patrocinios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioPatrocinioResource {
    
    /**
     * Se inyecta la logica del usuario
     */
    @Inject
    private UsuarioLogic usuarioLogic; 
    
    /**
     * Retorna todos los patrocinios que ha realizado un usuario
     * @param usuarioId identificador del usuario
     * @return la lista de patrocinios
     * @throws BusinessException 
     */
    @GET
    public List<PatrocinioDetailDTO> getPatrocinios(@PathParam("usuarioId") Long usuarioId) throws BusinessException{
        List<PatrocinioEntity> pat = usuarioLogic.getPatrocinios(usuarioId);
        List<PatrocinioDetailDTO> ret = new ArrayList<PatrocinioDetailDTO>();
        for(PatrocinioEntity p : pat)
        {
            ret.add(new PatrocinioDetailDTO(p));
        }
        return ret;
    }
    
    /**
     * Agrega un patrocinio al usuario dado por el id
     * @param usuarioId identificador del usuario
     * @param newp nuevo patrocinio
     * @return patrocinio añadido
     * @throws BusinessException 
     */
    @POST
    public PatrocinioDetailDTO addPatrocinio(@PathParam("usuarioId") Long usuarioId, PatrocinioDetailDTO newp) throws BusinessException{
        PatrocinioEntity change = newp.toEntity();
        //PatrocinioEntity nuevecito = PatrocinioLogic.addUsuario(usuarioId, change);
        PatrocinioEntity nuevo = usuarioLogic.addPatrocinio(usuarioId, change);
        return new PatrocinioDetailDTO(nuevo);
    }
    
    /**
     * Actualiza un patrocinio al usuario dado por id
     * @param usuarioId identificador del usuario
     * @param newp patrocinio a cambiar
     * @return patrocinio actualizado
     * @throws BusinessException 
     */
    @PUT
    @Path("{patrocinioId: \\d+}")
    public PatrocinioDetailDTO updatePatrocinio(@PathParam("usuarioId") Long usuarioId, PatrocinioDetailDTO newp, @PathParam("patrocinioId") Long patrocinioId) throws BusinessException{
        PatrocinioEntity pe = newp.toEntity();
        PatrocinioEntity cambio = usuarioLogic.updatePatrocinio(usuarioId, patrocinioId, pe);
        return new PatrocinioDetailDTO(cambio);
    }
    
}