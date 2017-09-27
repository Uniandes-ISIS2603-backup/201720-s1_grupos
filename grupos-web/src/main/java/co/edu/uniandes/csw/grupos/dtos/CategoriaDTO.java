/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.CategoriaEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Representación minimum de categoría.<br>
 * @author cm.sarmiento10
 */
public class CategoriaDTO {

   /**
    * Id de la categoria
    */
    private Long id;
    
    /**
     * Tipo/nombre de la categoría
     */
    private String tipo;
    
    /**
     * Descripción del grupo
     */
    private String descripcion;
    
    /**
     * ruta de la imagen de la categoría
     */
    private String rutaIcono;

    /**
     * Constructor vacío del DTO
     */
    public CategoriaDTO()
    {
    }
    
    /**
     * 
     * @param categoria, categoría a ser convertir a DTO
     */
    public CategoriaDTO(CategoriaEntity categoria)
    {
        this.id = categoria.getId();
        this.tipo = categoria.getTipo();
        this.descripcion = categoria.getDescripcion();
        this.rutaIcono= categoria.getRutaIcono();
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
     * @param id, id a ser puesto en el DTO
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return, tipo del DTO
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * 
     * @param tipo, tipo a ser puesto en el DTO
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
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
     * @param descripcion, descripcion a ser puesta en el DTO
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * 
     * @return ruta de la imagen de la categoría del DTO
     */
    public String getRutaIcono() {
        return rutaIcono;
    }

    /**
     * 
     * @param rutaIcono, ruta de la imagen de la categoría a ser puesta en el DTO
     */
    public void setRutaIcono(String rutaIcono) {
        this.rutaIcono = rutaIcono;
    }
    
    /**
     * Convertir DTO a Entity
     * @return Un Entity con los valores del DTO 
     */
    public CategoriaEntity toEntity() {
        CategoriaEntity entity = new CategoriaEntity();
        entity.setId(this.id);
        entity.setTipo(this.tipo);
        entity.setDescripcion(this.descripcion);
        entity.setRutaIcono(this.rutaIcono);
        return entity;
    }
}
