/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.UsuarioPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author tefa
 */
@Stateless
public class UsuarioLogic {
    /**
     * Se inyecta la persistencia del usuario
     */
    @Inject
    UsuarioPersistence per;
    
    /**
     * Metodo que crea un nuevo usuario
     * @param puser entidad nueva a crear
     * @return entidad creada
     * @throws BusinessException si ya existe un usuario con el id especificado. 
     */
    public UsuarioEntity createUser(UsuarioEntity puser) throws BusinessException
    {
        //Verifica si ya existe un usuario
        UsuarioEntity found = per.find(puser.getId()); 
        if(found != null && found == puser)
        {
            throw new BusinessException("Ya existe un usuario con las especificaciones dadas.");
        }
        else 
        {
            UsuarioEntity add = per.createEntity(puser);
            return add;
        }
    }
    
    /**
     * Método que busca todos los usuarios registrados en la aplicación.
     * @return lista con todos los usuarios de la aplicación
     * @throws BusinessException 
     */
    public List<UsuarioEntity> allUsers() throws BusinessException
    {
        return per.findAll();
    }
    
    /**
     * Busca un usuario por su id
     * @param pid id del usuario a buscar
     * @return Entidad que tiene el id especificado por parametro
     * @throws BusinessException 
     */
    public UsuarioEntity findById(Long pid) throws BusinessException
    {
        return per.find(pid);
    }
    
    /**
     * Método que actualiza un usuario
     * @param id identificador del usuario que se quiere actualizar.
     * @param puser nueva información del usuario
     * @return usuario actualizado
     * @throws BusinessException si no existe un usuario con el id especificado.
     */
    public UsuarioEntity updateUser(Long id, UsuarioEntity puser) throws BusinessException
    {
        UsuarioEntity found = per.find(id);
        if(found == null)
        {
            throw new BusinessException("No existe el usuario que se quiere actualizar");
        }
        else
        {
            return per.updateEntity(puser);
        }
    }
}
