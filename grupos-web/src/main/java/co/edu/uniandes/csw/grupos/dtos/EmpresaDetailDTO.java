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
    }
    
     /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param entity
     */
    public EmpresaDetailDTO(EmpresaEntity entity) {
        super(entity);
    }
    
     /**
     * Transformar un DTO a un Entity
     *
     * @return 
     */
    @Override
    public EmpresaEntity toEntity() {
        EmpresaEntity tarjetaE = super.toEntity();
        return tarjetaE;
    }
   
}
