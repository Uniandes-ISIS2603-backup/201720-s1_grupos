/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author tefa
 */
@Path("usuarios")
@Produces("application/json")
@Consumes("application/json")
@Stateless
public class UsuarioResource {
    
    @Inject
    UsuarioLogic userLogic; //Se inyecta la logica del usuario
    
    /**
     * POST http://localhost:8080/api/usuarios
     * json: { "id": 100, "nombre":"Francisco", "apellido":"Vega", "nickname":"Pacho123", "Password":"123456","email":"pacho123@hotmail.com"}
     * @param Usuario correponde a la representación java del objeto json enviado en el llamado.
     * @return Devuelve el objeto json de entrada que contiene el id creado por
     * la base de datos y el tipo del objeto java. Ejemplo: { "type":
     * "UsuarioDetailDTO", "id": 1, "name": "Norma" }
     * @throws BusinessException
     */
    @POST
    public UsuarioDetailDTO createUser(UsuarioDetailDTO puser) throws BusinessException
    {
        UsuarioEntity user = puser.toEntity(); //Convierte el JSON a entidad
        UsuarioEntity ret = userLogic.createUser(user);
        return new UsuarioDetailDTO(ret);
    }
    
    /**
     * GET http://localhost:8080/api/usuarios
     * @return Devuelve todos los objetos de tipo Usuario de la aplicación en formato JSON
     * @throws BusinessException
     */
    @GET
    public List<UsuarioDetailDTO> allUsers() throws BusinessException
    {
        List<UsuarioDetailDTO> ret = userLogic.allUsers();
        return ret;
    }
    
    /**
     * GET http://localhost:8080/api/usuarios/id
     * ejemplo: http://localhost:8080/api/usuarios/100
     * @return Devuelve el Usuario de la aplicación con id "id" en formato JSON
     * @throws BusinessException
     */
    @GET
    @Path("{id: \\d+}")
    public UsuarioDetailDTO findUserId(@PathParam("id") Long pid) throws BusinessException
    {
        UsuarioDetailDTO ret = userLogic.findById(pid);
        return ret;
    }
    
    /**
     * PUT http://localhost:8080/grupos/api/usuarios/id
     * @return Actualiza el usuario con id ingresado segun el JSON especificado
     * @param Usuario correponde a la representación java del objeto json enviado en el llamado.
     * @throws BusinessException
     */
    @PUT
    @Path("{id: \\d+}")
    public UsuarioDetailDTO updateUser(@PathParam("id") Long pid, UsuarioDetailDTO user, @HeaderParam("nickname") String nickname) throws BusinessException
    {
        UsuarioDetailDTO ret = userLogic.updateUser(pid,user.toEntity(),nickname);
        return ret;
    }
    
}
