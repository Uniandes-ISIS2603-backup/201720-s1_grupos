/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;


import co.edu.uniandes.csw.grupos.ejb.CategoriaLogic;
import co.edu.uniandes.csw.grupos.dtos.CategoriaDetailDTO;
import co.edu.uniandes.csw.grupos.entities.CategoriaEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author cm.sarmiento10
 */
@Path("categorias")
@Produces("application/json")
@Consumes("application/json")
@Stateless
public class CategoriaResource implements Serializable {

    @Inject
    CategoriaLogic categoriaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    /**
     * POST http://localhost:8080/backstepbystep-web/api/Categorias Ejemplo
     * json: { "name":"Norma" }
     *
     * @param Categoria correponde a la representación java del objeto json
     * enviado en el llamado.
     * @return Devuelve el objeto json de entrada que contiene el id creado por
     * la base de datos y el tipo del objeto java. Ejemplo: { "type":
     * "CategoriaDetailDTO", "id": 1, "name": "Norma" }
     * @throws BusinessException
     */
    @POST
    public CategoriaDetailDTO createCategoria(CategoriaDetailDTO grupo, @HeaderParam("nickname") String nickname) throws BusinessException {
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        CategoriaEntity entity= grupo.toEntity();
        // Invoca la lógica para crear la Categoria nueva
        CategoriaEntity nuevoCategoria = categoriaLogic.createCategoria(entity, nickname);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        return new CategoriaDetailDTO(nuevoCategoria);
    }

    /**
     * GET para todas las Categoriaes.
     * http://localhost:8080/backstepbystep-web/api/Categorias
     *
     * @return la lista de todas las Categoriaes en objetos json DTO.
     * @throws BusinessException
     */
    @GET
    public List<CategoriaDetailDTO> getCategorias() {
        return listEntity2DetailDTO(categoriaLogic.getCategorias());
    }

    /**
     * GET para una Categoria
     * http://localhost:8080/backstepbystep-web/api/Categorias/1
     *
     * @param id corresponde al id de la Categoria buscada.
     * @return La Categoria encontrada. Ejemplo: { "type": "CategoriaDetailDTO",
     * "id": 1, "name": "Norma" }
     * @throws BusinessException
     *
     * En caso de no existir el id de la Categoria buscada se retorna un 404 con
     * el mensaje.
     */
    @GET
    @Path("{id: \\d+}")
    public CategoriaDetailDTO getCategoria(@PathParam("id") Long id) {
        CategoriaEntity entity = categoriaLogic.getCategoria(id);
       
        return new CategoriaDetailDTO(entity);
    }
    
     /**
     * GET para una Categoria con nombre dado por parametro
     * http://localhost:8080/backstepbystep-web/api/Categorias/1
     *
     * @param id corresponde al id de la Categoria buscada.
     * @return La Categoria encontrada. Ejemplo: { "type": "CategoriaDetailDTO",
     * "id": 1, "name": "Norma" }
     * @throws BusinessException
     *
     * En caso de no existir el id de la Categoria buscada se retorna un 404 con
     * el mensaje.
     */
    @GET
    @Path("{nombre: [A-Za-z]+}")
    public CategoriaDetailDTO getCategoria(@QueryParam("tipo") String tipo) {
        CategoriaEntity entity = categoriaLogic.getCategoria(tipo);

       
        return new CategoriaDetailDTO(entity);
    }

    /**
     * PUT http://localhost:8080/backstepbystep-web/api/Categorias/1 Ejemplo
     * json { "id": 1, "name": "cambio de nombre" }
     *
     * @param id corresponde a la Categoria a actualizar.
     * @param Categoria corresponde a al objeto con los cambios que se van a
     * realizar.
     * @return La Categoria actualizada.
     * @throws BusinessException
     *
     * En caso de no existir el id de la Categoria a actualizar se retorna un
     * 404 con el mensaje.
     */
    @PUT
    @Path("{id: \\d+}")
    public CategoriaDetailDTO updateCategoria(@PathParam("id") Long id, CategoriaDetailDTO grupo, @HeaderParam("nickname") String nickname) {
        grupo.setId(id);
        CategoriaEntity entity = categoriaLogic.updateCategoria(grupo.toEntity(), nickname);
        
        return new CategoriaDetailDTO(entity);
    }

    /**
     * DELETE http://localhost:8080/backstepbystep-web/api/Categorias/1
     *
     * @param id corresponde a la Categoria a borrar.
     * @throws BusinessException
     *
     * En caso de no existir el id de la Categoria a actualizar se retorna un
     * 404 con el mensaje.
     *
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteCategoria(@PathParam("id") Long id,@HeaderParam("nickname") String nickname) {
        categoriaLogic.deleteCategoria(id, nickname);
    }

    /**
     *
     * lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos CategoriaEntity a una lista de
     * objetos CategoriaDetailDTO (json)
     *
     * @param entityList corresponde a la lista de Categoriaes de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de Categoriaes en forma DTO (json)
     */
    private List<CategoriaDetailDTO> listEntity2DetailDTO(List<CategoriaEntity> entityList) {
        List<CategoriaDetailDTO> list = new ArrayList<>();
        for (CategoriaEntity entity : entityList) {
            list.add(new CategoriaDetailDTO(entity));
        }
        return list;
    }
    
}
