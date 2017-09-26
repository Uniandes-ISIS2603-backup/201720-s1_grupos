/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.EventoLogic;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.exceptions.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Recurso evento usuario.<br>
 * @author js.ramos14
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventoUsuariosResource {
    
    @Inject
    private EventoLogic logic;
    

    /**
     * Convierte una lista de UsuarioEntity a una lista de UsuarioDetailDTO.
     *
     * @param entityList Lista de UsuarioEntity a convertir.
     * @return Lista de UsuarioDetailDTO convertida.
     * 
     */
    private List<UsuarioDetailDTO> usuariosListEntity2DTO(List<UsuarioEntity> entityList) {
        List<UsuarioDetailDTO> list = new ArrayList<>();
        for (UsuarioEntity entity : entityList) {
            list.add(new UsuarioDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de UsuarioDetailDTO a una lista de UsuarioEntity.
     *
     * @param dtos Lista de UsuarioDetailDTO a convertir.
     * @return Lista de UsuarioEntity convertida.
     * 
     */
    private List<UsuarioEntity> usuariosListDTO2Entity(List<UsuarioDetailDTO> dtos) {
        List<UsuarioEntity> list = new ArrayList<>();
        for (UsuarioDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }

    /**
     * Obtiene una colección de instancias de UsuarioDetailDTO asociadas a una
     * instancia de Evento
     *
     * @param id Identificador de la instancia de Evento
     * @return Colección de instancias de UsuarioDetailDTO asociadas a la
     * instancia de Evento
     * 
     */
    @GET
    public List<UsuarioDetailDTO> listUsuarios(@PathParam("id") Long id) throws BusinessException, NotFoundException {
        return usuariosListEntity2DTO(logic.listUsuarios(id));
    }

    /**
     * Obtiene una instancia de Usuario asociada a una instancia de Evento
     *
     * @param id Identificador de la instancia de Evento
     * @param usuariosId Identificador de la instancia de Usuario
     * @return 
     * 
     */
    @GET
    @Path("{usuariosId: \\d+}")
    public UsuarioDetailDTO getUsuarios(@PathParam("id") Long id, @PathParam("usuariosId") Long usuariosId) throws BusinessException, NotFoundException {
        UsuarioEntity e= logic.getUsuario(id, usuariosId);
        if(e==null) throw new NotFoundException("No se encuentra el usuario buscado");
        return new UsuarioDetailDTO(logic.getUsuario(id, usuariosId));
    }

    /**
     * Asocia un Usuario existente a un Evento
     *
     * @param id Identificador de la instancia de Evento
     * @param usuariosId Identificador de la instancia de Usuario
     * @return Instancia de UsuarioDetailDTO que fue asociada a Evento
     * 
     */
    @POST
    @Path("{usuariosId: \\d+}")
    public UsuarioDetailDTO addUsuarios(@PathParam("id") Long id, @PathParam("usuariosId") Long usuariosId) throws BusinessException, NotFoundException {
        return new UsuarioDetailDTO(logic.addUsuario(id, usuariosId));
    }

    /**
     * Desasocia un Usuario existente de un Evento existente
     *
     * @param id Identificador de la instancia de Evento
     * @param usuariosId Identificador de la instancia de Usuario
     * 
     */
    @DELETE
    @Path("{usuariosId: \\d+}")
    public void removeUsuarios(@PathParam("id") Long id, @PathParam("usuariosId") Long usuariosId) throws BusinessException, NotFoundException {
        logic.removeUsuario(id, usuariosId);
    }
}