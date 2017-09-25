/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.UsuarioDTO;
import co.edu.uniandes.csw.grupos.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.GrupoLogic;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
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
 * URI: grupos/{gruposId: \\d+}/administradores
 * @author cm.sarmiento10
 */
public class GrupoAdministradoresResource {
    /**
     * Lógica del grupo
     */
    @Inject
    private GrupoLogic grupoLogic;
    
    /**
     * Convierte una lista de AdministradoreEntity a una lista de AdministradoreDetailDTO.
     *
     * @param entityList Lista de AdministradoreEntity a convertir.
     * @return Lista de AdministradoreDetailDTO convertida.
     *
     */
    private List<UsuarioDTO> AdministradoresListEntity2DTO(List<UsuarioEntity> entityList) {
        List<UsuarioDTO> list = new ArrayList<>();
        for (UsuarioEntity entity : entityList) {
            list.add(new UsuarioDTO(entity));
        }
        return list;
    }
    
    /**
     * Convierte una lista de AdministradoreDetailDTO a una lista de AdministradoreEntity.
     *
     * @param dtos Lista de AdministradoreDetailDTO a convertir.
     * @return Lista de AdministradoreEntity convertida.
     *
     */
    private List<UsuarioEntity> AdministradoresListDTO2Entity(List<UsuarioDetailDTO> dtos) {
        List<UsuarioEntity> list = new ArrayList<>();
        for (UsuarioDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    /**
     * Obtiene una colección de instancias de AdministradoreDetailDTO asociadas a una
     * instancia de Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @return Colección de instancias de AdministradoreDetailDTO asociadas a la
     * instancia de Grupo
     *
     */
    @GET
    public List<UsuarioDTO> listAdministradores(@PathParam("grupoId") Long grupoId) {
        return AdministradoresListEntity2DTO(grupoLogic.listAdmins(grupoId));
    }
    
    /**
     * Obtiene una instancia de Administradore asociada a una instancia de Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param AdministradoresId Identificador de la instancia de Administradore
     * @return
     *
     */
    @GET
    @Path("{administradorId: \\d+}")
    public UsuarioDetailDTO getAdministradores(@PathParam("grupoId") Long grupoId, @PathParam("administradorId") Long administradorId) {
        UsuarioEntity u =grupoLogic.getAdmin(grupoId, administradorId);
        if(u==null) throw new NotFoundException("No existe el administrar buscado");
        return new UsuarioDetailDTO(u);
    }
    
    /**
     * Asocia un Administradore existente a un Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param AdministradoresId Identificador de la instancia de Administradore
     * @return Instancia de AdministradoreDetailDTO que fue asociada a Grupo
     *
     */
    @POST
    @Path("{administradorId: \\d+}")
    public UsuarioDetailDTO addAdministradores(@PathParam("grupoId") Long grupoId, @PathParam("administradorId") Long administradorId) throws BusinessException {
        return new UsuarioDetailDTO(grupoLogic.addAdmin(grupoId, administradorId));
    }
    
    /**
     * Desasocia un Administradore existente de un Grupo existente
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param AdministradoresId Identificador de la instancia de Administradore
     *
     */
    @DELETE
    @Path("{administradorId: \\d+}")
    public void removeAdministradores(@PathParam("grupoId") Long grupoId, @PathParam("administradorId") Long administradorId) {
        grupoLogic.removeAdmin(grupoId, administradorId);
    }
}
