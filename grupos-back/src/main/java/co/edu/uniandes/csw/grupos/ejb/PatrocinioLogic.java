/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;
import co.edu.uniandes.csw.grupos.entities.*;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;


/**
 * Lógica de patrocinio
 * @author tefa
 */
@Stateless
public class PatrocinioLogic {
    
    /**
     * Inyecta la persistencia de patrocinio
     */
    @Inject
    PatrocinioPersistence per;
    
    /**
     * Devuelve todos los patrocinios registrados en la aplicación.
     * @return lista con todos los patrocinios
     */
    public List<PatrocinioEntity> allPatrocinios()
    {
        return per.findAll();
    }
    
    /**
     * Crea un patrocinio
     * @param ppat nuevo patrocinio
     * @return patrocinio agregado
     */
    public PatrocinioEntity createPatrocinio(PatrocinioEntity ppat)
    {
        return per.createEntity(ppat);
    }
    
    /**
     * Actualiza el patrocinio con id ingresado por parametro
     * @param idPat identificador del patrocinio
     * @param pPat patrocinio que se quiere actualizar
     * @return patrocinio actualizado
     * @throws BusinessException 
     */
    public PatrocinioEntity updatePatrocinio(Long idPat, PatrocinioEntity pPat) throws BusinessException
    {
        PatrocinioEntity found = per.find(idPat);
        if(found == null)
        {
            throw new BusinessException("No existe el usuario que se quiere actualizar");
        }
        else
        {
            return per.updateEntity(pPat);
        }
    }
}
