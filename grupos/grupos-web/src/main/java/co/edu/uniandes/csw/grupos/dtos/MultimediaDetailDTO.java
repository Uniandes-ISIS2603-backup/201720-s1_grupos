/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;

/**
 *
 * @author jc161
 */
public class MultimediaDetailDTO extends MultimediaDTO {
    
    public MultimediaDetailDTO()
    {
        
    }
    
    public MultimediaDetailDTO(MultimediaEntity e)
    {
        super(e);
    }
    
    public MultimediaEntity toEntity()
    {
        return super.toEntity();
    }
    
}
