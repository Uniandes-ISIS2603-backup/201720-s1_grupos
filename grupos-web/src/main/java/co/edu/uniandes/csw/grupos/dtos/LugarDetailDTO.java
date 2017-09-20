/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.LugarEntity;

/**
 *
 * @author js.ramos14
 */
public class LugarDetailDTO extends LugarDTO {

    
    private Integer capacidad;
    
    public LugarDetailDTO()
    {
        
    }
    public LugarDetailDTO(LugarEntity entity)
    {
        super(entity);
        this.capacidad = entity.getCapacidad();
    }
    
    @Override
    public LugarEntity toEntity()
    {
        LugarEntity entity = new LugarEntity();
        entity.setDireccion(getDireccion());
        entity.setId(getId());
        entity.setNombre(getNombre());
        entity.setCapacidad(this.capacidad);
        return entity;
    }
}
