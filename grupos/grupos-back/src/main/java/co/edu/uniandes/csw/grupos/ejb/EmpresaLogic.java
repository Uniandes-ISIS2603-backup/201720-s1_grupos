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
 *
 * @author af.lopezf
 */
@Stateless
public class EmpresaLogic {
        
    @Inject
    private EmpresaPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    
    public EmpresaEntity createEmpresa(EmpresaEntity entity) throws BusinessException{
        
        if (persistence.findByNit(entity.getNit())!= null)
            throw new BusinessException("Ya existe una Empresa con el nit \"" + entity.getNit()+"\""); 
        
        persistence.create(entity);
        
        return entity;
    }
    
    
    public List<EmpresaEntity> getEmpresas(){
        
        List<EmpresaEntity> empresas = persistence.findAll();
        
        return empresas;
        
    }
    
    
    public EmpresaEntity getEmpresaByNit(int nit){
        
        EmpresaEntity tarjeta = persistence.findByNit(nit);
        
        return tarjeta;
    }
    
    
    public EmpresaEntity update(EmpresaEntity entity){
        
        EmpresaEntity empresa = persistence.update(entity);
        
        return empresa;
    }
    
    
    public void deleteEmpresa(int nit){
        
        persistence.delete(nit);
    }
            
    
    

}
