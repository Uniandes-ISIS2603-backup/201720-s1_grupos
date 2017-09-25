/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.CategoriaDTO;
import co.edu.uniandes.csw.grupos.dtos.CategoriaDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.GrupoLogic;
import co.edu.uniandes.csw.grupos.entities.CategoriaEntity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @Categoria cm.sarmiento10
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GrupoCategoriasResource {
    /**
     * Lógica del grupo
     */
    @Inject
    private GrupoLogic grupoLogic;
    
    /**
     * Convierte una lista de CategoriaEntity a una lista de CategoriaDTO.
     *
     * @param entityList Lista de CategoriaEntity a convertir.
     * @return Lista de CategoriaDetailDTO convertida.
     *
     */
    private List<CategoriaDTO> CategoriasListEntity2DTO(List<CategoriaEntity> entityList) {
        List<CategoriaDTO> list = new ArrayList<>();
        for (CategoriaEntity entity : entityList) {
            list.add(new CategoriaDTO(entity));
        }
        return list;
    }
    
    /**
     * Convierte una lista de CategoriaDetailDTO a una lista de CategoriaEntity.
     *
     * @param dtos Lista de CategoriaDetailDTO a convertir.
     * @return Lista de CategoriaEntity convertida.
     *
     */
    private List<CategoriaEntity> CategoriasListDTO2Entity(List<CategoriaDetailDTO> dtos) {
        List<CategoriaEntity> list = new ArrayList<>();
        for (CategoriaDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    /**
     * Obtiene una colección de instancias de CategoriaDetailDTO asociadas a una
     * instancia de Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @return Colección de instancias de CategoriaDetailDTO asociadas a la
     * instancia de Grupo
     *
     */
    @GET
    public List<CategoriaDTO> listCategorias(@PathParam("grupoId") Long grupoId) {
        System.out.println("id " + grupoId);
        return CategoriasListEntity2DTO(grupoLogic.listCategorias(grupoId));
    }
    
    /**
     * Obtiene una instancia de Categoria asociada a una instancia de Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param CategoriasId Identificador de la instancia de Categoria
     * @return
     *
     */
    @GET
    @Path("{categoriaId: \\d+}")
    public CategoriaDetailDTO getCategorias(@PathParam("grupoId") Long grupoId, @PathParam("categoriaId") Long categoriaId) {
         CategoriaEntity u =grupoLogic.getCategoria(grupoId, categoriaId);
        if(u==null) throw new NotFoundException("No existe el administrar buscado");
        return new CategoriaDetailDTO(u);
    }
    
    /**
     * Asocia un Categoria existente a un Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param CategoriasId Identificador de la instancia de Categoria
     * @return Instancia de CategoriaDetailDTO que fue asociada a Grupo
     *
     */
    @POST
    @Path("{categoriaId: \\d+}")
    public CategoriaDetailDTO addCategorias(@PathParam("grupoId") Long grupoId, @PathParam("categoriaId") Long categoriaId) {
        System.out.println("sniff ");
        return new CategoriaDetailDTO(grupoLogic.addCategoria(grupoId, categoriaId));
    }
    
    /**
     * Desasocia un Categoria existente de un Grupo existente
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param CategoriasId Identificador de la instancia de Categoria
     *
     */
    @DELETE
    @Path("{categoriaId: \\d+}")
    public void removeCategorias(@PathParam("grupoId") Long grupoId, @PathParam("categoriaId") Long categoriaId) {
        grupoLogic.removeCategoria(grupoId, categoriaId);
    }
}
