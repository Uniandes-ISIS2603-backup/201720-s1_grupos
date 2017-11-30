/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.GrupoEntity;

/**
 * Representación minimum de grupo.<br>
 * @author cm.sarmiento10
 */
public class GrupoDTO {
    
    /**
     * id del grupo
     */
    private Long id;
    
    /**
     * nombre del grupo
     */
    private String nombre;
    
    /**
     * descripcion del grupo
     */
    private String descripcion;

    /**
     * Constructor vacío
     */
    public GrupoDTO()
    {
        //Constructor vacío
    }
    
    /**
     * 
     * @param grupo, entidad a ser convertida en DTO
     */
    public GrupoDTO(GrupoEntity grupo) {
        this.id = grupo.getId();
        this.nombre = grupo.getNombre();
        this.descripcion = grupo.getDescripcion();
    }
    
    /**
     * 
     * @return id del DTO
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id, id a ser puesta en el DTO 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return nombre del DTO
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @param nombre nombre a ser puesto en el DTO
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * 
     * @return descripción del DTO
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * 
     * @param descripcion, descripción a ser puesta en el DTO
     */
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

