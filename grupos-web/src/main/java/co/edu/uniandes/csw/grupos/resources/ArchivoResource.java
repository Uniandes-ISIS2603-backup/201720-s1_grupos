/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.ArchivoDTO;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *Recurso de archivo.<br>
 * @author af.lopezf
 */
@Path("archivos")
@Produces("application/json")
@Consumes("application/json")
@Stateless
public class ArchivoResource {
    
    
    
     /**
     * GET http://localhost:8080/gurpos-web/Stark/archivos
     * @return Devuelve todos los objetos de tipo Empresa de la aplicación en formato JSON
     * @throws BusinessException
     */
    @GET
    public List<ArchivoDTO> allArchivos() throws BusinessException
    {
     return null;   
    }
    
}
