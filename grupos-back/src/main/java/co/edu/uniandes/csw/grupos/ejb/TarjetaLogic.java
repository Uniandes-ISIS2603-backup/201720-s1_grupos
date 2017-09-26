/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.TarjetaEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.TarjetaPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Lógica de tarjeta.<br>
 * @author af.lopezf
 */
@Stateless
public class TarjetaLogic {
    /**
     * Persistencia de tarjeta.
     */
    @Inject
    private TarjetaPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una entidad para persistir.<br>
     * @param entity Entidad a crear.<br>
     * @return Entidad de tarjeta persistida.<br>
     * @throws BusinessException  Excepción de negocio.
     */
    public TarjetaEntity createTarjeta(TarjetaEntity entity) throws BusinessException{
        
        if (persistence.findByNumero(entity.getNumero())!= null)
            throw new BusinessException("Ya existe una Tarjeta con el número \"" + entity.getNumero()+"\""); 
        
        persistence.create(entity);
        
        return entity;
    }
    
    /**
     * Obtiene todas las tarjetas del sistema.<br>
     * @return Listado de entidades de tarjeta.
     */
    public List<TarjetaEntity> getTarjetas(){
        
        List<TarjetaEntity> tarjetas = persistence.findAll();
        
        return tarjetas;
        
    }
    
    /**
     * Obtiene tarjeta por número.<br>
     * @param numero Número de la tarjeta.<br>
     * @return Número de la tarjeta.
     */
    public TarjetaEntity getTarjetaByNumero(int numero){
        
        TarjetaEntity tarjeta = persistence.findByNumero(numero);
        
        return tarjeta;
    }
    
    /**
     * Actualiza la entidad de tarjeta.<br>
     * @param entity Entidad a actualizar.<br>
     * @return Entidad actualizada
     */
    public TarjetaEntity update(TarjetaEntity entity){
        
        TarjetaEntity tarjeta = persistence.update(entity);
        
        return tarjeta;
    }
    
    /**
     * Borra la tarjeta con número dado.<br>
     * @param numero Número de la tarjeta.<br>
     */
    public void deleteTarjeta(int numero){
        
        persistence.delete(numero);
    }
            
    
    
}
