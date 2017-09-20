/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.GrupoEntity;

/**
 *
 * @author cm.sarmiento10
 */
public class GrupoDTO {
    
    private Long id;
    private String nombre;
    private String descripcion;

    public GrupoDTO()
    {
        
    }
    
    public GrupoDTO(GrupoEntity grupo) {
        this.id = grupo.getId();
        this.nombre = grupo.getNombre();
        this.descripcion = grupo.getDescripcion();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * Convertir DTO a Entity
     * @return Un Entity con los valores del DTO 
     */
    public GrupoEntity toEntity() {
        GrupoEntity entity = new GrupoEntity();
        entity.setId(this.id);
        entity.setNombre(this.nombre);
        entity.setDescripcion(this.descripcion);
        return entity;
    }
}

