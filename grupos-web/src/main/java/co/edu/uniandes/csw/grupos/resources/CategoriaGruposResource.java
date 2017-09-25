/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.GrupoDTO;
import co.edu.uniandes.csw.grupos.dtos.GrupoDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.CategoriaLogic;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import javax.ws.rs.Path;
/**
 * URI: categorias/{id: \\d+}/grupos
 *
 * @Grupo ISIS2603
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoriaGruposResource {
    /**
     * Lógica de la categoría
     */
    @Inject
    private CategoriaLogic categoriaLogic;
    
    /**
     * Convierte una lista de GrupoEntity a una lista de GrupoDetailDTO.
     *
     * @param entityList Lista de GrupoEntity a convertir.
     * @return Lista de GrupoDetailDTO convertida.
     *
     */
    private List<GrupoDTO> GruposListEntity2DTO(List<GrupoEntity> entityList) {
        List<GrupoDTO> list = new ArrayList<>();
        for (GrupoEntity entity : entityList) {
            list.add(new GrupoDTO(entity));
        }
        return list;
    }
    
    /**
     * Obtiene una colección de instancias de GrupoDTO asociadas a una
     * instancia de Categoria
     *
     * @param categoriaId Identificador de la instancia de Categoria
     * @return Colección de instancias de GrupoDetailDTO asociadas a la
     * instancia de Book
     *
     */
    @GET
    public List<GrupoDTO> listGrupos(@PathParam("categoriaId") Long categoriaId) {
        return GruposListEntity2DTO(categoriaLogic.listGrupos(categoriaId));
    }
    
    /**
     * 
     * @param categoriasId, id de la categoría que tiene la lista de grupos
     * @param gruposId, id del grupo a
     * @return 
     */
    @GET
    @Path("{grupoId: \\d+}")
    public GrupoDetailDTO getGrupo(@PathParam("categoriaId") Long categoriasId, @PathParam("grupoId") Long gruposId) {
        return new GrupoDetailDTO(categoriaLogic.getGrupo(gruposId, categoriasId));
    }
}
