/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entidad de comentario.<br>
 * @author se.cardenas
 */
@Entity
public class ComentarioEntity implements Serializable{
    /**
     * Id del comentario
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Autor del comentario.
     */
    private String autor;
    /**
     * COmentario.
     */
    private String comentario;
    /**
     * Obtiene el id.<br>
     * @return 
     */
    public Long getId() {
        return id;
    }
    /**
     * Configura el id al dado por parámetro.
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Obtiene el autor.<br>
     * @return autor
     */
    public String getAutor() {
        return autor;
    }
    /**
     * Modifica el autor al valor dado por parámetro.<br>
     * @param autor 
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }
    /**
     * Obtiene el comentario.<br>
     * @return comentario
     */
    public String getComentario() {
        return comentario;
    }
    /**
     * Modifica el valor al dado por parámetro.<br>
     * @param comentario 
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    /**
     * Override el equals.<br>
     * @param obj Objeto a comparar.<br>
     * @return Si los objetos son iguales o no.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ComentarioEntity other = (ComentarioEntity) obj;
        return Objects.equals(this.id, other.id);
    }
    /**
     * Override del hash.<br>
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
}
