/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;

/**
 *
 * @author se.cardenas
 */
public class BlogDTO {
    
    private Long id;
    private String titulo;
    private String contenido;
    private  Double promedio;
    
    /**
     * Construye un BlogDTO vacío
     */
    public BlogDTO() {
        
    }
    
    /**
     * Construye un DTO a partir de un BlogEntity
     * @param blog Entity
     */
    public BlogDTO(BlogEntity blog) {
        this.id = blog.getId();
        this.titulo = blog.getTitulo();
        this.contenido = blog.getContenido();
        this.promedio = blog.getPromedio();
    }
    
    /**
     * 
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id nuevo valor de id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * 
     * @param titulo nuevo valor de titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * 
     * @return contenido
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * 
     * @param contenido nuevo valor de contenido
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     * 
     * @return promedio
     */
    public Double getPromedio() {
        return promedio;
    }

    /**
     * 
     * @param promedio nuevo valor para promedio
     */
    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }
    
    /**
     * Construye un BlogEntity a partir de un BlogDTO
     * @return BlogEntity
     */
    public BlogEntity toEntity() {
        BlogEntity entity = new BlogEntity();
        entity.setId(id);
        entity.setTitulo(titulo);
        entity.setContenido(contenido);
        entity.setPromedio(promedio);
        return entity;
    }
}
