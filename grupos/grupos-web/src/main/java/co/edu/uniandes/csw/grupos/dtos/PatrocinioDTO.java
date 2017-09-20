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
public class PatrocinioDTO {
    
    private Long id;
    private double pago;
    
    /**
     * Constructor vacio 
     */
    public PatrocinioDTO(){
    }
    
    
    /**
     * Constructor a partir de un entity
     */
    public PatrocinioDTO(PatrocinioEntity pe){
        this.id= pe.getId();
        this.pago = pe.getPago();
    }
    
    public Long getId(){
        return id;
    }
    
    public double getPago(){
        return pago;
    }
    
    public void setId(Long pId){
        id= pId;
    }
    
    public void setPago(double nPago){
        pago= nPago;
    }
    
    /**
     * Convierte un PatrocinioDTO a un PatrocinioEntity
     * @return PatrocinioEntity
     */
    public PatrocinioEntity toEntity(){
        PatrocinioEntity patro = new PatrocinioEntity();
        patro.setId(id);
        patro.setPago(pago);
        return patro;
    }
}
