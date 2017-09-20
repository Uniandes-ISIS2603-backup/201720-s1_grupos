/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.PatrocinioEntity;

/**
 *
 * @author tefa
 */
public class PatrocinioDetailDTO extends PatrocinioDTO{
    
    /**
     * Constructor vacio
     */
    public PatrocinioDetailDTO(){
    }
    
    /**
     * Constructor a partir de un entity
     */
    public PatrocinioDetailDTO(PatrocinioEntity pe){
        super(pe);
    }
    
    public PatrocinioEntity toEntity(){
        PatrocinioEntity pe = super.toEntity();
        return pe;
    }
}
