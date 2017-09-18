/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Clase que modela la noticia del sistema.<br>
 * @author s.guzmanm
 */
@Entity
public class MultimediaEntity implements Serializable {
    /**
     * Nombre de la multimedia
     */
    private String nombre;
    /**
     * Descripción de la multimedia.
     */
    private String descripcion;
    /**
     * Link dela multimedia.
     */
    @Id
    private String link;
    /**
     * Obtiene el nombre de la multimedia.<br>
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Modifica el valor del nombre al dado por parámetro.<br>
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Obtiene la descripción de la multimedia.
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Modifica la descripción al valor dado por parámetro.<br>
     * @param descripcion 
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     * Obtiene el link de la multimedia.<br>
     * @return link
     */
    public String getLink() {
        return link;
    }
    /**
     * Modifica el link al valor dado por parámetro.<br>
     * @param link 
     */
    public void setLink(String link) {
        this.link = link;
    }
    
}
