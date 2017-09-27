/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.EmpresaDetailDTO;
import co.edu.uniandes.csw.grupos.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.EmpresaLogic;
import co.edu.uniandes.csw.grupos.entities.EmpresaEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
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
 *Recurso de empresa.<br>
 * @author af.lopezf
 */
@Path("empresas")
@Produces("application/json")
@Consumes("application/json")
@Stateless
public class EmpresaResource {
    /*
    Lógica de empresa.<br>
    */
    @Inject
    EmpresaLogic empresaLogic; //Se inyecta la logica del usuario
    
    
     /**
     * GET http://localhost:8080/gurpos-web/Stark/empresas
     * @return Devuelve todos los objetos de tipo Empresa de la aplicación en formato JSON
     * @throws BusinessException
     */
    @GET
    public List<EmpresaDetailDTO> allEmpresas() throws BusinessException
    {
        List<EmpresaEntity> empresas;
        empresas = empresaLogic.getEmpresas();
        List<EmpresaDetailDTO> ret = new ArrayList<EmpresaDetailDTO>();
        for(EmpresaEntity e:empresas)
        {
            ret.add(new EmpresaDetailDTO(e));
        }
        return ret;
    }
    
}