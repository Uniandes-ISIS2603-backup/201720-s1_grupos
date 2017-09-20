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
 *
 * @author cm.sarmiento10
 */
public class CategoriaDTO {

   
    private Long id;
    private String tipo;
    private String descripcion;
    private String rutaIcono;

    public CategoriaDTO()
    {
        
    }
    
    public CategoriaDTO(CategoriaEntity categoria)
    {
        this.id = categoria.getId();
        this.tipo = categoria.getTipo();
        this.descripcion = categoria.getDescripcion();
        this.rutaIcono= categoria.getRutaIcono();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRutaIcono() {
        return rutaIcono;
    }

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
