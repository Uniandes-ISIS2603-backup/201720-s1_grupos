/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.GrupoEntity;

/**
 *
 * @author cm.sarmiento10
 */
public class GrupoDetailDTO extends GrupoDTO{
    
    /**
     * Constructor por defecto
     */
    public GrupoDetailDTO() {
    }
    
    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param entity
     */
    public GrupoDetailDTO(GrupoEntity entity) {
        super(entity);
    }

    /**
     * Transformar un DTO a un Entity
     *
     * @return 
     */
    @Override
    public GrupoEntity toEntity() {
        GrupoEntity editorialE = super.toEntity();
        return editorialE;
    }
    
}
