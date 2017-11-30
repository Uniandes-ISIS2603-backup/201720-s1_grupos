/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.EmpresaEntity;

/**
 * Representación detail de empresa.<br>
 * @author af.lopezf
 */
public class EmpresaDetailDTO extends EmpresaDTO {
    
    /**
     * Constructor por defecto
     */
    public EmpresaDetailDTO() {
        //Constructor vacío para usos de pruebas y recursos REST
    }
    
     /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param entity
     */
    public EmpresaDetailDTO(EmpresaEntity entity) {
        super(entity);
    }
    
   
}
