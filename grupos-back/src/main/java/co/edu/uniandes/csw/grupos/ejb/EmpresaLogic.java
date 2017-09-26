/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.EmpresaEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.EmpresaPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Lógica de empresa
 * @author af.lopezf
 */
@Stateless
public class EmpresaLogic {
    /**
     * Persistencia de empresa.
     */
    @Inject
    private EmpresaPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una nueva empresa con la entidad dada.<br>
     * @param entity Entidad.<br>
     * @return Entidad persistida.<br>
     * @throws BusinessException Excepción de negocio.
     */
    public EmpresaEntity createEmpresa(EmpresaEntity entity) throws BusinessException{
        
        if (persistence.findByNit(entity.getNit())!= null)
            throw new BusinessException("Ya existe una Empresa con el nit \"" + entity.getNit()+"\""); 
        
        persistence.create(entity);
        
        return entity;
    }
    
    /**
     * Obtiene todas las empresas.<br>
     * @return Entidad de empresa.
     */
    public List<EmpresaEntity> getEmpresas(){
        
        List<EmpresaEntity> empresas = persistence.findAll();
        
        return empresas;
        
    }
    
    /**
     * Obtiene empresa por nit.<br>
     * @param nit Código nit.<br>
     * @return Entidad de empresa.
     */
    public EmpresaEntity getEmpresaByNit(int nit){
        
        EmpresaEntity tarjeta = persistence.findByNit(nit);
        
        return tarjeta;
    }
    
    /**
     * Actualiza la entidad dada por parámetro.<br>
     * @param entity Entidad.<br>
     * @return Entidad actualizada
     */
    public EmpresaEntity update(EmpresaEntity entity){
        
        EmpresaEntity empresa = persistence.update(entity);
        
        return empresa;
    }
    
    /**
     * Borra una empresa con el nit dado.<br>
     * @param nit Nit de la empresa.
     */
    public void deleteEmpresa(int nit){
        
        persistence.delete(nit);
    }
            
    
    

}
