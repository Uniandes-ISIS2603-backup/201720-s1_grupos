/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.ComentarioEntity;

/**
 * Representación minimum de comentario.<br>
 * @author se.cardenas
 */
public class ComentarioDTO {
    
    private Long id;
    private String autor;
    private String comentario;
    
    /**
     * Construye un ComentarioDTO vacío
     */
    public ComentarioDTO() {
        
    }
    
    /**
     * Construye un ComentarioDTO a partir de un BlogEntity
     * @param entity BlogEntity
     */
    public ComentarioDTO(ComentarioEntity entity) {
        id = entity.getId();
        autor = entity.getAutor();
        comentario = entity.getComentario();
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
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * retorna el nombre del autor
     * @return autor
     */
    public String getAutor() {
        return autor;
    }

    /**
     * cambia el autor
     * @param autor 
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * retorna el comentario
     * @return comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * cambia el comentario
     * @param comentario 
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public ComentarioEntity toEntity() {
        ComentarioEntity entity = new ComentarioEntity();
        entity.setId(id);
        entity.setAutor(autor);
        entity.setComentario(comentario);
        return entity;
    }
}
