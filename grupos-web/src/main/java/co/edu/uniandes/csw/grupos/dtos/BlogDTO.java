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
    private String nombreAutor;
    private Double promedio;
    
    /**
     * Construye un BlogDTO vacío
     */
    public BlogDTO() {
        
    }
    
    /**
     * Construye un BlogDTO a partir de un BlogEntity
     * @param entity BlogEntity
     */
    public BlogDTO(BlogEntity entity) {
        this.id = entity.getId();
        this.titulo = entity.getTitulo();
        this.contenido = entity.getContenido();
        this.promedio = entity.getPromedio();
    }
    
    /**
     * retorna el id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * cambia el id
     * @param id nuevo valor de id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * retorna el titulo
     * @return titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * cambia el titulo
     * @param titulo nuevo valor de titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * retorna el contenido
     * @return contenido
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * cambia el contenido
     * @param contenido nuevo valor de contenido
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }
    
    /**
     * retorna el promedio
     * @return promedio
     */
    public Double getPromedio() {
        return promedio;
    }

    /**
     * cambia el promedio
     * @param promedio nuevo valor de promedio
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
