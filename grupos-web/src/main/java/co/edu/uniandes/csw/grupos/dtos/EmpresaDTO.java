/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.EmpresaEntity;

/**
 * Representación minimum de empresa.<br>
 * @author af.lopezf
 */
public class EmpresaDTO {
    /**
     * Nit
     */
    private int nit;
    /**
     * Nombre de la empresa
     */
    private String nombre;
    /**
     * Logo de la empresa
     */
    private String logo;
    
     /**
     * Constructor por defecto
     */
    public EmpresaDTO() {
    }
    
     /**
     * Conviertir Entity a DTO
     * (Crea un nuevo DTO con los valores que recibe en  la entidad que viene de argumento.
     * @param empresa: Es la entidad que se va a convertir a DTO 
     */
    public EmpresaDTO(EmpresaEntity empresa) {
        this.nit = empresa.getNit();
        this.nombre = empresa.getNombre();
        this.logo = empresa.getLogo();
    }

    /**
     * @return the nit
     */
    public int getNit() {
        return nit;
    }

    /**
     * @param nit the nit to set
     */
    public void setNit(int nit) {
        this.nit = nit;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * @param logo the logo to set
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    /**
     * Convertir DTO a Entity
     * @return Un Entity con los valores del DTO 
     */
    public EmpresaEntity toEntity() {
        EmpresaEntity entity = new EmpresaEntity();
        entity.setNit(this.nit);
        entity.setNombre(this.nombre);
        entity.setLogo(this.logo);
        return entity;
    }
    
    
}
