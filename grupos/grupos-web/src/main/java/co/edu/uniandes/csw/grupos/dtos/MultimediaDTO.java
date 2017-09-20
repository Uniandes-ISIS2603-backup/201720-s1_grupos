/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;

/**
 * DTO de la multimedia
 * @author jc161
 */
public class MultimediaDTO {
    /**
     * Nombre
     */
    private String nombre;
    /**
     * Descripción de la multimedia
     */
    private String descripcion;
    /**
     * Link de la multimedia
     */
    private String link;
    /**
     * Constructor vacío
     */
    public MultimediaDTO()
    {
        
    }
    /**
     * Construye un nuevo DTO apartir de una entidad
     * @param e 
     */
    public MultimediaDTO(MultimediaEntity e)
    {
        nombre=e.getNombre();
        link=e.getLink();
        descripcion=e.getDescripcion();
    }
    /**
     * Obtiene el nombre de la multimedia.
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Modifica el nombre por el valor dado por parámetro.<br>
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Obtiene la descripción de la multimedia.<br>
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Modifica la descripción de la multimedia por el valor dado por parámetro.<br>
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
     * Modifica el link por el valor dado por parámetro.<br>
     * @param link 
     */
    public void setLink(String link) {
        this.link = link;
    }
    /**
     * Construye una nueva entidad a partir del DTo.
     * @return Entidad multimedia creada
     */
    public MultimediaEntity toEntity()
    {
        MultimediaEntity e= new MultimediaEntity();
        e.setDescripcion(descripcion);
        e.setLink(link);
        e.setNombre(nombre);
        return e;
    }
}
