/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.CategoriaEntity;

/**
 *
 * @author cm.sarmiento10
 */
public class CategoriaDetailDTO extends CategoriaDTO{
    
    /**
     * Constructor por defecto
     */
    public CategoriaDetailDTO() {
    }
    
    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param entity
     */
    public CategoriaDetailDTO(CategoriaEntity entity) {
        super(entity);
    }

    /**
     * Transformar un DTO a un Entity
     *
     * @return 
     */
    @Override
    public CategoriaEntity toEntity() {
        CategoriaEntity editorialE = super.toEntity();
        return editorialE;
    }
}
