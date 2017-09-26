/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;


import co.edu.uniandes.csw.grupos.dtos.TarjetaDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.grupos.entities.TarjetaEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author af.lopezf
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioTarjetasResource {
    
    @Inject
    private UsuarioLogic usuarioLogic;  // Se inyecta la lógica del usuario
    
    
     /**
     * Convierte una lista de TarjetaEntity a una lista de TarjetaDetailDTO.
     *
     * @param entityList Lista de TarjetaEntity a convertir.
     * @return Lista de TarjetaDetailDTO convertida.
     * 
     */
    private List<TarjetaDetailDTO> tarjetasListEntity2DTO(List<TarjetaEntity> entityList){
        List<TarjetaDetailDTO> list = new ArrayList<>();
        for (TarjetaEntity entity : entityList) {
            list.add(new TarjetaDetailDTO(entity));
        }
        return list;
    }
    
     /**
     * Convierte una lista de TarjetaDetailDTO a una lista de TarjetaEntity.
     *
     * @param dtos Lista de TarjetaDetailDTO a convertir.
     * @return Lista de TarjetaEntity convertida.
     * 
     */
    private List<TarjetaEntity> tarjetasListDTO2Entity(List<TarjetaDetailDTO> dtos){
        List<TarjetaEntity> list = new ArrayList<>();
        for (TarjetaDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    
     /**
     * Obtiene una colección de instancias de TarjetaDetailDTO asociadas a una
     * instancia de Usuario
     *
     * @param usuarioId Identificador de la instancia de Usuario
     * @return Colección de instancias de TarjetaDetailDTO asociadas a la instancia de Usuario
     * 
     */
    @GET
    public List<TarjetaDetailDTO> listTarjetas(@PathParam("usuarioId") Long usuarioId) throws BusinessException {
        return tarjetasListEntity2DTO(usuarioLogic.listTarjetas(usuarioId));
    }
    
    /**
     * Obtiene una instancia de Tarjeta asociada a una instancia de Usuario
     *
     * @param usuarioId Identificador de la instancia de Usuario
     * @param numTarjeta Identificador de la instancia de Tarjeta
     * @return Tarjeta buscada
     */
    @GET
    @Path("{numTarjeta: \\d+}")
    public TarjetaDetailDTO getTarjeta(@PathParam("usuarioId") Long usuarioId, @PathParam("numTarjeta") int numTarjeta) throws BusinessException {
        TarjetaEntity tarjeta = usuarioLogic.getTarjeta(usuarioId, numTarjeta);
        if(tarjeta == null){
            throw new WebApplicationException("La tarjeta con numero " + numTarjeta + " no existe.", 404);
        }
        return new TarjetaDetailDTO(tarjeta);
    }
    
    
     /**
     * Asocia una Tarjeta existente a un Usuario
     *
     * @param usuarioId Identificador de la instancia de Usuario
     * @param pTarjeta corresponde a la representación java del objeto json enviado en el llamado
     * @return Instancia de TarjetaDetailDTO que fue asociada a Usuario
     * 
     */
    @POST
    public TarjetaDetailDTO addTarjeta(@PathParam("usuarioId") Long usuarioId, TarjetaDetailDTO pTarjeta ) throws BusinessException {        
        return new TarjetaDetailDTO(usuarioLogic.addTarjeta(usuarioId, pTarjeta.toEntity()));
    }
    
    
     /**
     * Actualiza una instancia Tarjeta asociad a una instancia de Usuario
     *
     * @param usuarioId Identificador de la instancia de Usuario
     * @param numTarjeta Identificador de la tarjeta que se va a actualizar
     * @param pTarjeta DTO que contiene la nueva informacion de la tarjeta
     * @return DTO que contiene la nueva informacion de la tarjeta.
     * 
     */
    @PUT
    @Path("{numTarjeta: \\d+}")
    public TarjetaDetailDTO updateTarjeta(@PathParam("usuarioId") Long usuarioId, @PathParam("numTarjeta") int numTarjeta, TarjetaDetailDTO pTarjeta) throws BusinessException {
        TarjetaEntity tarjeta = usuarioLogic.getTarjeta(usuarioId, numTarjeta);
        if(tarjeta == null){
            throw new WebApplicationException("El usuario con id " + usuarioId + " no tiene ninguna tarjeta con el número " + numTarjeta);
        }
        
        if(pTarjeta.getBanco() != null)
            tarjeta.setBanco(pTarjeta.getBanco());
        if(Math.abs(pTarjeta.getDineroDisponible()- tarjeta.getDineroDisponible())<0.00001)
        {
            tarjeta.setDineroDisponible(pTarjeta.getDineroDisponible());
        }
        if(Math.abs(pTarjeta.getMaxCupo()- tarjeta.getMaxCupo())<0.00001)
        {
            tarjeta.setMaxCupo(pTarjeta.getMaxCupo());
        }
        
        
        return new TarjetaDetailDTO(usuarioLogic.updateTarjeta(usuarioId, tarjeta));
    }
    
    
     /**
     * Desasocia una Tarjeta de un Usuario existente
     *
     * @param usuarioId Identificador de la instancia de Usuario
     * @param numTarjeta Identificador de la instancia de Tarjeta
     * 
     */
    @DELETE
    @Path("{numTarjeta: \\d+}")
    public void removeTarjeta(@PathParam("usuarioId") Long usuarioId, @PathParam("numTarjeta") int numTarjeta) throws BusinessException {
        TarjetaEntity tarjeta = usuarioLogic.getTarjeta(usuarioId, numTarjeta);
        if(tarjeta == null){
            throw new WebApplicationException("El usuario con id " + usuarioId + " no tiene ninguna tarjeta con el número " + numTarjeta);
        }
        usuarioLogic.removeTarjeta(usuarioId, numTarjeta);
    }


}