/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.TarjetaEntity;

/**
 *
 * @author af.lopezf
 */
public class TarjetaDetailDTO extends TarjetaDTO {

    /**
     * Constructor por defecto
     */
    public TarjetaDetailDTO() {
    }
    
     /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param entity
     */
    public TarjetaDetailDTO(TarjetaEntity entity) {
        super(entity);
    }
    
     /**
     * Transformar un DTO a un Entity
     *
     * @return 
     */
    @Override
    public TarjetaEntity toEntity() {
        TarjetaEntity tarjetaE = super.toEntity();
        return tarjetaE;
    }
    
}
