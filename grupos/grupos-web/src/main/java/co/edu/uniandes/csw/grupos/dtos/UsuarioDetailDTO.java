/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;

/**
 *
 * @author tefa
 */
public class UsuarioDetailDTO extends UsuarioDTO {
    
    /**
     * Constructor vacio
     */
    public UsuarioDetailDTO(){
        
    }
    
    /**
     * Constructor a partir de un entity
     * @param ue Usuario Entity
     */
    public UsuarioDetailDTO(UsuarioEntity ue){
        super(ue);
    }
    
    public UsuarioEntity toEntity(){
        UsuarioEntity ue = super.toEntity();
        return ue;
    }
}
