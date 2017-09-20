/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;

/**
 *
 * @author se.cardenas
 */
public class BlogDetailDTO extends BlogDTO{
    
    //Aquí van las relaciones
    
    /**
     * Construye un BlogDetailDTO vacío
     */
    public BlogDetailDTO() {
        
    }
    
    /**
     * Construye un BlogDetailDTO a partir de un BlogEntity
     * @param entity BlogEntity
     */
    public BlogDetailDTO(BlogEntity entity) {
        super(entity);
        //Aquí va el manejo de relaciones
    }
    
    /**
     * Construye un BlogEntity a partir de un BlogDetailDTO
     * @return BlogEntity
     */
    @Override
    public BlogEntity toEntity() {
        BlogEntity entity = super.toEntity();
        //Aquí va el manejo de relaciones
        return entity;
    }
}
