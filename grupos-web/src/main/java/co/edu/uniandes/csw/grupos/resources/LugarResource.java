/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.LugarDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.LugarLogic;
import co.edu.uniandes.csw.grupos.entities.LugarEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Recurso de lugar.<br>
 * @author js.ramos14 
 */
@Path("lugares")
@Produces("application/json")
@Consumes("application/json")
@Stateless
public class LugarResource {
    /**
     * Lógica de lugar.
     */
    @Inject
    LugarLogic logic;
    /**
     * Retorna todos los lugares en dtos.<br>
     * @return  Listado de dtos.
     */
    @GET
    public List<LugarDetailDTO> getLugares()
    {
        List<LugarDetailDTO> list = listEntityToDetailDTO(logic.getAll());
        return list;
    }
    /**
     * Retorna un lugar con id dado.<br>
     * @param id Id dado.<br>
     * @return DTO.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException Excepción de no encontrado.
     */
    @GET
    @Path("{id: \\d+}")
    public LugarDetailDTO getLugar(@PathParam("id") Long id) throws BusinessException
    {
        LugarEntity entity=logic.getEntity(id);
        return new LugarDetailDTO(entity);
    }
    /**
     * Actualiza un lufar con el id dado.<br>
     * @param id Id.<br>
     * @return Lugar actualizado en DTO.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException Si no encuentra algo.
     */
    @PUT
    @Path("{id: \\d+}")
    public LugarDetailDTO updateLugar(@PathParam("id") Long id) throws BusinessException
    {
       LugarEntity entity =logic.getEntity(id);
       return new LugarDetailDTO(logic.updateEntity(entity, null));
       
    }
    /**
     * Crea un nuevo lugar.<br>
     * @param lugar DTO.<br>
     * @return Lugar creado.<br>
     * @throws BusinessException Si hay excepción de negocio. 
     */
    @POST
    public LugarDetailDTO createLugar(LugarDetailDTO lugar) throws BusinessException
    {
        LugarEntity entity = lugar.toEntity();
        return new LugarDetailDTO(logic.createEntity(entity));
    }
    /**
     * Borra un lugar con id dado.<br>
     * @param id Id del lugar.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException Excepción de no encontrado.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteLugar(@PathParam("id")Long id) throws BusinessException
    {
        LugarEntity entity = logic.getEntity(id);
        logic.deleteEntity(entity);
    }
    /**
     * Pasa un listado de entidades a dtos.<br>
     * @param entityList Listado de entidades.<br>
     * @return Listado en forma de dtos.
     */
    private List<LugarDetailDTO> listEntityToDetailDTO(List<LugarEntity> entityList) {
        List<LugarDetailDTO> list = new ArrayList<>();
        for (LugarEntity entity : entityList) {
            list.add(new LugarDetailDTO(entity));
        }
        return list;
    }
}
