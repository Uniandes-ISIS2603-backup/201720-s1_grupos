/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.PatrocinioPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author tefa
 */
@Stateless
public class PatrocinioLogic {
    
    @Inject
    PatrocinioPersistence per;
    
    public List<PatrocinioEntity> allPatrocinios() throws BusinessException
    {
        return per.findAll();
    }
    
}
