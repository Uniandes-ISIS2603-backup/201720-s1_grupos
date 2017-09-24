/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.EmpresaEntity;
import co.edu.uniandes.csw.grupos.entities.TarjetaEntity;
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
     * Se inyecta la logica de tarjeta
     */
    @Inject
    TarjetaLogic tarjetaLogic;
    
    /**
     * Se inyecta la logica de empresa
     */
    @Inject
    EmpresaLogic empresaLogic;
    
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
    
    /**
     * Metodo que retorna las tarjetas de un usuario
     * @param id identificador del usuario que posee las tarjetas
     * @return lista de tarjetas del usuario
     */
    public List<TarjetaEntity> listTarjetas(Long id) throws BusinessException{
        return findById(id).getTarjetas();
    }
    
    /**
     * Método que retorna una tarjeta específica de un usuario
     * @param id Identificador del usuario
     * @param pNumTarjeta Numero que identifica la tarjeta que se desea buscar.
     * @return 
     */
    public TarjetaEntity getTarjeta(Long id, int pNumTarjeta) throws BusinessException{
        List<TarjetaEntity> tarjetasUsuario = findById(id).getTarjetas();
        TarjetaEntity tarjetaBuscada = null;
        
        for(TarjetaEntity tarjeta : tarjetasUsuario){
            if(tarjeta.getNumero() == pNumTarjeta){
                tarjetaBuscada = tarjeta;
                break;
            }
         
        }
        
        return tarjetaBuscada;
    }
    
    /**
     * Método que relaciona una nueva tarjeta a un usuario
     * @param id Identificador del usuario
     * @param entity Nueva tarjeta
     * @return Nueva Tarjeta
     * @throws BusinessException Cuando se trata de adicionar una tarjeta que ya existe.
     */
    public TarjetaEntity addTarjeta(Long id, TarjetaEntity entity) throws BusinessException{
        TarjetaEntity tarjetaNueva = tarjetaLogic.createTarjeta(entity);
        UsuarioEntity usuario = findById(id);
        usuario.getTarjetas().add(tarjetaNueva);
        return tarjetaLogic.getTarjetaByNumero(entity.getNumero());
        
    }

    /**
     * Méetodo que modifica una tarjeta asociada a un usuario
     * @param id Identificador del usuario dueno de la tarjeta
     * @param entity Entidad que representa la tarjeta con la nueva informacion
     * @return Tarjeta modificada
     * @throws BusinessException En caso de que se trate de modificar una tarjeta que no exista.
     */
    public TarjetaEntity updateTarjeta(Long id, TarjetaEntity entity) throws BusinessException{
        TarjetaEntity tarjetaNueva = tarjetaLogic.update(entity);
        UsuarioEntity usuario = findById(id);
        
        List<TarjetaEntity> tarjetas = usuario.getTarjetas();
        
        for(TarjetaEntity tarjeta : tarjetas){
            if(tarjeta.getNumero() == tarjetaNueva.getNumero()){
                tarjetas.remove(tarjeta);
                tarjetas.add(tarjetaNueva);
                break;
            }
        }
        
        return tarjetaNueva;
    }
    
    /**
     * Método que elimina una tarjeta asociada a un usuario
     * @param id Identificador del usuario dueno de la tarjeta
     * @param pNumTarjeta Número que identifica a la tarjeta 
     * @throws BusinessException En caso de que se trate de borrar una tarjeta que no existe.
     */
    public void removeTarjeta(Long id, int pNumTarjeta) throws BusinessException{
        UsuarioEntity usuario = findById(id);
        TarjetaEntity tarjetaBorrar = getTarjeta(id, pNumTarjeta);
        
        usuario.getTarjetas().remove(tarjetaBorrar);
        tarjetaLogic.deleteTarjeta(pNumTarjeta);
         
    }
    
    
     /**
     * Método que retorna la empresa de un usuario
     * @param id Identificador del usuario
     * @return Empresa del usuario
     */
    public EmpresaEntity getEmpresa(Long id) throws BusinessException{
        EmpresaEntity empresa = findById(id).getEmpresa();        
        return empresa;
    }
    
    
    
     /**
     * Método que relaciona una nueva empresa a un usuario
     * @param id Identificador del usuario
     * @param entity Nueva empresa
     * @return Nueva Empresa
     * @throws BusinessException Cuando se trata de adicionar una empresa que ya existe.
     */
    public EmpresaEntity addEmpresa(Long id, EmpresaEntity entity) throws BusinessException{
        EmpresaEntity empresaNueva = empresaLogic.createEmpresa(entity);
        UsuarioEntity usuario = findById(id);
        usuario.setEmpresa(empresaNueva);
        return empresaLogic.getEmpresaByNit(empresaNueva.getNit());
        
    }
    
    
        /**
     * Méetodo que modifica una empresa asociada a un usuario
     * @param id Identificador del usuario dueno de la tarjeta
     * @param entity Entidad que representa la empresa con la nueva informacion
     * @return Empresa modificada
     * @throws BusinessException En caso de que se trate de modificar una empresa que no existe.
     */
    public EmpresaEntity updateEmpresa(Long id, EmpresaEntity entity) throws BusinessException{
        EmpresaEntity empresaNueva = empresaLogic.update(entity);
        UsuarioEntity usuario = findById(id);
        
        usuario.setEmpresa(empresaNueva);   
        
        return empresaNueva;
    }
    
    
     /**
     * Método que elimina una empresa asociada a un usuario
     * @param id Identificador del usuario dueno de la tarjeta
     * @throws BusinessException En caso de que se trate de borrar una empresa que no existe.
     */
    public void removeEmpresa(Long id) throws BusinessException{
        UsuarioEntity usuario = findById(id);
        EmpresaEntity empresa = usuario.getEmpresa();
        usuario.setEmpresa(null);
        empresaLogic.deleteEmpresa(empresa.getNit());
         
    }
}
