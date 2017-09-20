/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.ComentarioEntity;

/**
 *
 * @author se.cardenas
 */
public class ComentarioDetailDTO extends ComentarioDTO {
    
    //Aquí van las relaciones
    
    /**
     * crea un ComentarioDetailDTO vacío
     */
    public ComentarioDetailDTO() {
        
    }
    
    /**
     * crea un comentarioDetailDTO a partir de un ComentarioEntity
     * @param entity ComentarioEntity
     */
    public ComentarioDetailDTO(ComentarioEntity entity) {
        super(entity);
        //Aquí se pasan las relaciones del entity a DTOs
    }
    
    /**
     * crea un ComentarioEntity a partir de un ComentarioDetailDTO
     * @return ComentarioEntity
     */
    @Override
    public ComentarioEntity toEntity() {
        ComentarioEntity entity = super.toEntity();
        //Aquí se pasan los DTOs de las relaciones a Entities
        return entity;
    }
}
