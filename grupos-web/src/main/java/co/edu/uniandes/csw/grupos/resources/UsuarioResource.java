/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.*;
import co.edu.uniandes.csw.grupos.ejb.*;
import co.edu.uniandes.csw.grupos.entities.*;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * Recurso de usuario.<br>
 * @author tefa
 */
@Path("usuarios")
@Produces("application/json")
@Consumes("application/json")
@Stateless
public class UsuarioResource {
    
    @Inject
    UsuarioLogic userLogic; //Se inyecta la logica del usuario
    
    @Inject
    PatrocinioLogic patrocinioLogic; //Se inyecta la logica del patrocinio
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
        List<UsuarioEntity> users = userLogic.allUsers();
        List<UsuarioDetailDTO> ret = new ArrayList<UsuarioDetailDTO>();
        for(UsuarioEntity e:users)
        {
            ret.add(new UsuarioDetailDTO(e));
        }
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
        UsuarioEntity found = userLogic.findById(pid);
        if(found==null)
        {
            throw new NotFoundException("No existe el usuario buscado");
        }
        UsuarioDetailDTO ret = new UsuarioDetailDTO(found);
        return ret;
    }
    
    @GET
    @Path("login")
    public UsuarioDetailDTO loginUser(@HeaderParam("email") String email, @HeaderParam("password") String password) throws BusinessException {
        UsuarioEntity entity= new UsuarioEntity();
        entity.setEmail(email);
        entity.setPassword(password);
        UsuarioEntity result = userLogic.findUserEmailPassword(entity);
        if (result == null) {
            throw new NotFoundException("correo electrónico o contraseña incorrectos. Inténtelo nuevamente.");
        }
        return new UsuarioDetailDTO(result);
        
    }
    
    /**
     * PUT http://localhost:8080/grupos/api/usuarios/id
     * @return Actualiza el usuario con id ingresado segun el JSON especificado
     * @param Usuario correponde a la representación java del objeto json enviado en el llamado.
     * @throws BusinessException
     */
    @PUT
    @Path("{usuarioId: \\d+}")
    public UsuarioDetailDTO updateUser(@PathParam("usuarioId") Long pid, UsuarioDetailDTO user) throws BusinessException
    {
        UsuarioEntity found = userLogic.findById(pid);
        if(found==null)
        {
            throw new NotFoundException("No existe el usuario buscado");
        }
        return new UsuarioDetailDTO(userLogic.updateUser(pid,user.toEntity()));
    }
    
    /**
     * Inicializa la clase para conectar usuarios y patrocinio
     * @param pid id del usuario
     * @return
     * @throws BusinessException 
     */
    @Path("{id: \\d+}/patrocinios")
    public Class<UsuarioPatrocinioResource> findPatrocinios(@PathParam("id") Long pid) throws BusinessException
    {
        UsuarioEntity entity = userLogic.findById(pid);
        if (entity == null) {
            throw new WebApplicationException("El usuario no existe", 404);
        }
        return UsuarioPatrocinioResource.class;
    }
    /**
     * Retorna las tarjetas de un usuario dado.<br>
     * @param usuarioId Id de usuario.<br>
     * @return UsuarioTarjetasResource.<br>
     * @throws BusinessException Excepción de negocio.
     */
    @Path("{usuarioId: \\d+}/tarjetas")
    public Class<UsuarioTarjetasResource> getUsuarioTarjetasResource(@PathParam("usuarioId") Long usuarioId) throws BusinessException {
        UsuarioEntity entity = userLogic.findById(usuarioId);
        if (entity == null) {
            throw new WebApplicationException("El usuario no existe", 404);
        }
        return UsuarioTarjetasResource.class;
    }
    
    @Path("{usuarioId: \\d+}/empresas")
    public Class<UsuarioEmpresaResource> getUsuarioEmpresaResource(@PathParam("usuarioId") Long usuarioId) throws BusinessException {
        UsuarioEntity entity = userLogic.findById(usuarioId);
        if (entity == null) {
            throw new WebApplicationException("El usuario no existe", 404);
        }
        return UsuarioEmpresaResource.class;
    }
    /**
     * Obtiene las noticias de un usuario.<br>
     * @param usuarioId Id de usuario.<br>
     * @return UsuarioNoticiaResource.<br>
     * @throws BusinessException  Excepción de negocio.
     */
    @Path("{usuarioId: \\d+}/noticias")
    public Class<UsuarioNoticiaResource> getUsuarioNoticiaResource(@PathParam("usuarioId") Long usuarioId) throws BusinessException {
        UsuarioEntity entity = userLogic.findById(usuarioId);
        if (entity == null) {
            throw new WebApplicationException("El usuario no existe", 404);
        }
        return UsuarioNoticiaResource.class;
    }
     /**
     * Obtiene los blogs favoritos de un usuario.<br>
     * @param usuarioId Id de usuario.<br>
     * @return UsuarioBlogResource.<br>
     * @throws BusinessException  Excepción de negocio.
     */
    @Path("{usuarioId: \\d+}/blogs")
    public Class<UsuarioBlogResource> getUsuarioBlogResource(@PathParam("usuarioId") Long usuarioId) throws BusinessException {
        UsuarioEntity entity = userLogic.findById(usuarioId);
        if (entity == null) {
            throw new WebApplicationException("El usuario no existe", 404);
        }
        return UsuarioBlogResource.class;
    }
    
}
