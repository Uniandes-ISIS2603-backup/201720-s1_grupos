/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;
import co.edu.uniandes.csw.grupos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.grupos.entities.*;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.*;
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
    
    @Inject
    UsuarioPersistence userPer;
    
    public List<PatrocinioEntity> allPatrocinios() throws BusinessException
    {
        return per.findAll();
    }
    
    public List<PatrocinioEntity> allPatrociniosUser(Long pId) throws BusinessException
    {
        //TODO: verificar que el usuario exista
        UsuarioEntity user = userPer.find(pId);
        List<PatrocinioEntity> lista = allPatrocinios();
        return per.findAll();
    }
}
