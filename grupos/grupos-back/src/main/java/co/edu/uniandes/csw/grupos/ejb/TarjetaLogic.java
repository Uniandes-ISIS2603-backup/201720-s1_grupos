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
 *
 * @author af.lopezf
 */
@Stateless
public class TarjetaLogic {
    
    @Inject
    private TarjetaPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    
    public TarjetaEntity createTarjeta(TarjetaEntity entity) throws BusinessException{
        
        if (persistence.findByNumero(entity.getNumero())!= null)
            throw new BusinessException("Ya existe una Tarjeta con el número \"" + entity.getNumero()+"\""); 
        
        persistence.create(entity);
        
        return entity;
    }
    
    
    public List<TarjetaEntity> getTarjetas(){
        
        List<TarjetaEntity> tarjetas = persistence.findAll();
        
        return tarjetas;
        
    }
    
    
    public TarjetaEntity getTarjetaByNumero(int numero){
        
        TarjetaEntity tarjeta = persistence.findByNumero(numero);
        
        return tarjeta;
    }
    
    
    public TarjetaEntity update(TarjetaEntity entity){
        
        TarjetaEntity tarjeta = persistence.update(entity);
        
        return tarjeta;
    }
    
    
    public void deleteTarjeta(int numero){
        
        persistence.delete(numero);
    }
            
    
    
}
