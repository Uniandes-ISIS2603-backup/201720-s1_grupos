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
 * URI: grupos/{gruposId: \\d+}/miembroos
 * @author cm.sarmiento10
 */
public class GrupoMiembrosResource {
    /**
     * Lógica del grupo
     */
    @Inject
    private GrupoLogic grupoLogic;
    
    /**
     * Convierte una lista de MiembroEntity a una lista de MiembroDetailDTO.
     *
     * @param entityList Lista de MiembroEntity a convertir.
     * @return Lista de MiembroDetailDTO convertida.
     *
     */
    private List<UsuarioDTO> MiembrosListEntity2DTO(List<UsuarioEntity> entityList) {
        List<UsuarioDTO> list = new ArrayList<>();
        for (UsuarioEntity entity : entityList) {
            list.add(new UsuarioDTO(entity));
        }
        return list;
    }
    
    /**
     * Convierte una lista de MiembroDetailDTO a una lista de MiembroEntity.
     *
     * @param dtos Lista de MiembroDetailDTO a convertir.
     * @return Lista de MiembroEntity convertida.
     *
     */
    private List<UsuarioEntity> MiembrosListDTO2Entity(List<UsuarioDetailDTO> dtos) {
        List<UsuarioEntity> list = new ArrayList<>();
        for (UsuarioDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    /**
     * Obtiene una colección de instancias de MiembroDetailDTO asociadas a una
     * instancia de Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @return Colección de instancias de MiembroDetailDTO asociadas a la
     * instancia de Grupo
     *
     */
    @GET
    public List<UsuarioDTO> listMiembros(@PathParam("grupoId") Long grupoId) {
        return MiembrosListEntity2DTO(grupoLogic.listMiembros(grupoId));
    }
    
    /**
     * Obtiene una instancia de Miembro asociada a una instancia de Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param MiembrosId Identificador de la instancia de Miembro
     * @return
     *
     */
    @GET
    @Path("{miembroId: \\d+}")
    public UsuarioDetailDTO getMiembros(@PathParam("grupoId") Long grupoId, @PathParam("miembroId") Long MiembroId) {
        UsuarioEntity u =grupoLogic.getMiembro(grupoId, MiembroId);
        if(u==null) throw new NotFoundException("No existe el administrar buscado");
        return new UsuarioDetailDTO(u);
    }
    
    /**
     * Asocia un Miembro existente a un Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param MiembrosId Identificador de la instancia de Miembro
     * @return Instancia de MiembroDetailDTO que fue asociada a Grupo
     *
     */
    @POST
    @Path("{miembroId: \\d+}")
    public UsuarioDetailDTO addMiembros(@PathParam("grupoId") Long grupoId, @PathParam("miembroId") Long miembroId) throws BusinessException {
        return new UsuarioDetailDTO(grupoLogic.addMiembro(grupoId, miembroId));
    }
    
    /**
     * Desasocia un Miembro existente de un Grupo existente
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param MiembrosId Identificador de la instancia de Miembro
     *
     */
    @DELETE
    @Path("{miembroId: \\d+}")
    public void removeMiembros(@PathParam("grupoId") Long grupoId, @PathParam("miembroId") Long miembroId) {
        grupoLogic.removeMiembro(grupoId, miembroId);
    }
}
