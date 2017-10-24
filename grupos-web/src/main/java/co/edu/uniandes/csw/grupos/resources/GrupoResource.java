/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.GrupoDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.GrupoLogic;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
@Path("grupos")
@Produces("application/json")
@Consumes("application/json")
@Stateless
public class GrupoResource {
    
    /**
     * Lógica del grupo
     */
    @Inject
            GrupoLogic grupoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    /**
     * POST http://localhost:8080/backstepbystep-web/api/Grupos Ejemplo
     * json: { "name":"Norma" }
     *
     * @param Grupo correponde a la representación java del objeto json
     * enviado en el llamado.
     * @return Devuelve el objeto json de entrada que contiene el id creado por
     * la base de datos y el tipo del objeto java. Ejemplo: { "type":
     * "GrupoDetailDTO", "id": 1, "name": "Norma" }
     * @throws BusinessException
     */
    @POST
    public GrupoDetailDTO createGrupo(GrupoDetailDTO grupo) throws BusinessException {
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        GrupoEntity entity= grupo.toEntity();
        // Invoca la lógica para crear la Grupo nueva
        GrupoEntity nuevoGrupo = grupoLogic.createGrupo(entity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        return new GrupoDetailDTO(nuevoGrupo);
    }
    
    /**
     * GET para todas las Grupoes.
     * http://localhost:8080/backstepbystep-web/api/Grupos
     *
     * @return la lista de todas las Grupoes en objetos json DTO.
     * @throws BusinessException
     */
    @GET
    public List<GrupoDetailDTO> getGrupos() {
        System.out.println("llega");
        return listEntity2DetailDTO(grupoLogic.getGrupos());
    }
    
    /**
     * GET para una Grupo
     * http://localhost:8080/backstepbystep-web/api/Grupos/1
     *
     * @param id corresponde al id de la Grupo buscada.
     * @return La Grupo encontrada. Ejemplo: { "type": "GrupoDetailDTO",
     * "id": 1, "name": "Norma" }
     * @throws BusinessException
     *
     * En caso de no existir el id de la Grupo buscada se retorna un 404 con
     * el mensaje.
     */
    @GET
    @Path("{id: \\d+}")
    public GrupoDetailDTO getGrupo(@PathParam("id") Long id) {
        GrupoEntity entity = grupoLogic.getGrupo(id);
        System.out.println("prueba prueba " + entity.getCategorias().size());
        GrupoDetailDTO grup= new GrupoDetailDTO(entity);
        return new GrupoDetailDTO(entity);
        
    }
    
    /**
     * GET para una Grupo con nombre dado por parametro
     * http://localhost:8080/backstepbystep-web/api/Grupos/1
     *
     * @param id corresponde al id de la Grupo buscada.
     * @return La Grupo encontrada. Ejemplo: { "type": "GrupoDetailDTO",
     * "id": 1, "name": "Norma" }
     * @throws BusinessException
     *
     * En caso de no existir el id de la Grupo buscada se retorna un 404 con
     * el mensaje.
     */
    @GET
    @Path("{nombre: [A-Za-z]+}")
    public GrupoDetailDTO getGrupo(@QueryParam("nombre") String nombre) {
        GrupoEntity entity = grupoLogic.getGrupo(nombre);
        
        return new GrupoDetailDTO(entity);
    }
    
    /**
     * PUT http://localhost:8080/backstepbystep-web/api/Grupos/1 Ejemplo
     * json { "id": 1, "name": "cambio de nombre" }
     *
     * @param id corresponde a la Grupo a actualizar.
     * @param Grupo corresponde a al objeto con los cambios que se van a
     * realizar.
     * @return La Grupo actualizada.
     * @throws BusinessException
     *
     * En caso de no existir el id de la Grupo a actualizar se retorna un
     * 404 con el mensaje.
     */
    @PUT
    @Path("{id: \\d+}")
    public GrupoDetailDTO updateGrupo(@PathParam("id") Long id, GrupoDetailDTO grupo) throws BusinessException {
        grupo.setId(id);
        GrupoEntity grupoBuscado=grupoLogic.getGrupo(grupo.getNombre());
        if (grupoBuscado != null && grupoBuscado.getId()!=id) {
            throw new BusinessException("Ya existe un grupo con el nombre \"" + grupo.getNombre() + "\"");
        }
        GrupoEntity entity = grupoLogic.updateGrupo(grupo.toEntity());
        
        return new GrupoDetailDTO(entity);
    }
    
    /**
     * DELETE http://localhost:8080/backstepbystep-web/api/Grupos/1
     *
     * @param id corresponde a la Grupo a borrar.
     * @throws BusinessException
     *
     * En caso de no existir el id de la Grupo a actualizar se retorna un
     * 404 con el mensaje.
     *
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteGrupo(@PathParam("id") Long id)  {
        grupoLogic.deleteGrupo(id);
    }
    
    /**
     *
     * @param id, id del grupo asociado con las categorías
     * @return el recurso para manejar la asociación grupos-categorias
     */
    @Path("{grupoId: \\d+}/categorias")
    public Class<GrupoCategoriasResource> getGrupoCategoriasResource(@PathParam("grupoId") Long id) {
        System.out.println("llega2");
        GrupoEntity entity = grupoLogic.getGrupo(id);
        
        return GrupoCategoriasResource.class;
    }
    
    /**
     *
     * @param id, id del grupo asociado con los miembros
     * @return el recurso para manejar la asociación grupos-miembros
     */
    @Path("{grupoId: \\d+}/miembros")
    public Class<GrupoMiembrosResource> getGrupoMiembrosResource(@PathParam("grupoId") Long id) {
        GrupoEntity entity = grupoLogic.getGrupo(id);
        
        return GrupoMiembrosResource.class;
    }
    
    /**
     *
     * @param id, id del grupo asociado con los administradores
     * @return el recurso para manejar la asociación grupos-administradores
     */
    @Path("{grupoId: \\d+}/administradores")
    public Class<GrupoAdministradoresResource> getGrupoAdministradoresResource(@PathParam("grupoId") Long grupoId) {
        GrupoEntity entity = grupoLogic.getGrupo(grupoId);
        
        return GrupoAdministradoresResource.class;
    }
    
    /**
     *
     * @param id, id del grupo asociado con los blogs
     * @return el recurso para manejar la asociación grupos-blogs
     */
    @Path("{grupoId: \\d+}/blogs")
    public Class<GrupoBlogsResource> getGrupoBlogsResource(@PathParam("grupoId") Long id) {
        GrupoEntity entity = grupoLogic.getGrupo(id);
        
        return GrupoBlogsResource.class;
    }
    
    /**
     *
     * @param id, id del grupo asociado con las noticias
     * @return el recurso para manejar la asociación grupos-noticias
     */
    @Path("{grupoId: \\d+}/noticias")
    public Class<GrupoNoticiasResource> getGrupoNoticiasResource(@PathParam("grupoId") Long id) {
        GrupoEntity entity = grupoLogic.getGrupo(id);
        return GrupoNoticiasResource.class;
    }
    
    /**
     *
     * @param id, id del grupo asociado con los eventos
     * @return el recurso para manejar la asociación grupos-eventos
     */
    @Path("{grupoId: \\d+}/eventos")
    public Class<GrupoEventosResource> getGrupoEventosResource(@PathParam("grupoId") Long id) {
        GrupoEntity entity = grupoLogic.getGrupo(id);
        
        return GrupoEventosResource.class;
    }
    
    /**
     *
     * lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos GrupoEntity a una lista de
     * objetos GrupoDetailDTO (json)
     *
     * @param entityList corresponde a la lista de Grupoes de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de Grupoes en forma DTO (json)
     */
    private List<GrupoDetailDTO> listEntity2DetailDTO(List<GrupoEntity> entityList) {
        List<GrupoDetailDTO> list = new ArrayList<>();
        for (GrupoEntity entity : entityList) {
            list.add(new GrupoDetailDTO(entity));
        }
        return list;
    }
}

