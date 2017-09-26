/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.EmpresaDetailDTO;
import co.edu.uniandes.csw.grupos.dtos.TarjetaDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.grupos.entities.EmpresaEntity;
import co.edu.uniandes.csw.grupos.entities.TarjetaEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author af.lopezf
 */
@Path("/usuarios/{usuarioId: \\d+}/empresa")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioEmpresaResource {
    
    @Inject
    private UsuarioLogic usuarioLogic;  // Se inyecta la lógica del usuario
    
    
     /**
     * Convierte una lista de EmpresaEntity a una lista de EmpresaDetailDTO.
     *
     * @param entityList Lista de EmpresaEntity a convertir.
     * @return Lista de EmpresaDetailDTO convertida.
     * 
     */
    private List<EmpresaDetailDTO> empresasListEntity2DTO(List<EmpresaEntity> entityList){
        List<EmpresaDetailDTO> list = new ArrayList<>();
        for (EmpresaEntity entity : entityList) {
            list.add(new EmpresaDetailDTO(entity));
        }
        return list;
    }
    
     /**
     * Convierte una lista de EmpresaDetailDTO a una lista de EmpresaEntity.
     *
     * @param dtos Lista de EmpresaDetailDTO a convertir.
     * @return Lista de EmpresaEntity convertida.
     * 
     */
    private List<EmpresaEntity> empresasListDTO2Entity(List<EmpresaDetailDTO> dtos){
        List<EmpresaEntity> list = new ArrayList<>();
        for (EmpresaDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    
     /**
     * Obtiene la instancia de EmpresaDetailDTO asociadas a una
     * instancia de Usuario
     *
     * @param usuarioId Identificador de la instancia de Usuario
     * @return Colección de instancias de TarjetaDetailDTO asociadas a la instancia de Usuario
     * 
     */
    @GET
    public EmpresaDetailDTO getEmpresa(@PathParam("usuarioId") Long usuarioId) throws BusinessException {
        UsuarioEntity usuario = usuarioLogic.findById(usuarioId);
        if (usuario == null) {
            throw new WebApplicationException("El usuario no existe", 404);
        }
        EmpresaEntity empresa = usuario.getEmpresa();
        if(empresa == null){
            throw new WebApplicationException("El usuario con id " + usuarioId + " no tiene empresa asociada", 404);
        }
        return new EmpresaDetailDTO(empresa);
    }    
    
     /**
     * Asocia una Empresa existente a un Usuario
     *
     * @param usuarioId Identificador de la instancia de Usuario
     * @param pEmpresa corresponde a la representación java del objeto json enviado en el llamado
     * @return Instancia de EmpresaDetailDTO que fue asociada a Usuario
     * 
     */
    @POST
    public EmpresaDetailDTO addEmpresa(@PathParam("usuarioId") Long usuarioId, EmpresaDetailDTO pEmpresa ) throws BusinessException {
        UsuarioEntity usuario = usuarioLogic.findById(usuarioId);
        if (usuario == null) {
            throw new WebApplicationException("El usuario no existe", 404);
        } 
        EmpresaEntity empresa = usuarioLogic.getEmpresa(usuarioId);
        if(empresa != null){
            throw new BusinessException("El usuario con id " + usuarioId + " ya tiene una empresa asociada, solo se puede tener una empresa.");
        }
        return new EmpresaDetailDTO(usuarioLogic.addEmpresa(usuarioId, pEmpresa.toEntity()));
    }    
    
     /**
     * Actualiza una instancia Empresa asociad a una instancia de Usuario
     *
     * @param usuarioId Identificador de la instancia de Usuario
     * @param pEmpresa DTO que contiene la nueva informacion de la empresa
     * @return DTO que contiene la nueva informacion de la empresa.
     * 
     */
    @PUT
    public EmpresaDetailDTO updateEmpresa(@PathParam("usuarioId") Long usuarioId, EmpresaDetailDTO pEmpresa) throws BusinessException {
        UsuarioEntity usuario = usuarioLogic.findById(usuarioId);
        if (usuario == null) {
            throw new WebApplicationException("El usuario no existe", 404);
        }
        EmpresaEntity empresa = usuarioLogic.getEmpresa(usuarioId);
        if(empresa == null){
            throw new WebApplicationException("El usuario con id " + usuarioId + " no tiene empresa asociada", 404);
        }
        return new EmpresaDetailDTO(usuarioLogic.updateEmpresa(usuarioId, pEmpresa.toEntity()));
    }
    
    
     /**
     * Desasocia una Empresa de un Usuario existente
     *
     * @param usuarioId Identificador de la instancia de Usuario
     * 
     */
    @DELETE
    public void removeEmpresa(@PathParam("usuarioId") Long usuarioId) throws BusinessException {
        UsuarioEntity usuario = usuarioLogic.findById(usuarioId);
        if (usuario == null) {
            throw new WebApplicationException("El usuario no existe", 404);
        }
        EmpresaEntity empresa = usuarioLogic.getEmpresa(usuarioId);
        if(empresa == null){
            throw new WebApplicationException("El usuario con id " + usuarioId + " no tiene empresa asociada", 404);
        }
        usuarioLogic.removeEmpresa(usuarioId);
    }

    
}