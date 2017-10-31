/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.LugarEntity;

/**
 *Dto detallado de lugar.<br>
 * @author js.ramos14
 */
public class LugarDetailDTO extends LugarDTO {

    /**
     * Capacidad
     */
    private Integer capacidad;
    /**
     * Constructor vacío.
     */
    public LugarDetailDTO()
    {
        super();
    }
    /**
     * Construye un nuevo ligar a partir de la entidad dada.<br>
     * @param entity Entidad.
     */
    public LugarDetailDTO(LugarEntity entity)
    {
        super(entity);
        this.capacidad = entity.getCapacidad();
    }
    /**
     * Saca la entidad dada.<br>
     * @return Entidad del dto.
     */
    @Override
    public LugarEntity toEntity()
    {
        LugarEntity entity = super.toEntity();
        entity.setDireccion(getDireccion());
        entity.setId(getId());
        entity.setNombre(getNombre());
        entity.setCapacidad(this.getCapacidad());
        return entity;
    }

    /**
     * @return the capacidad
     */
    public Integer getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }
    
}
