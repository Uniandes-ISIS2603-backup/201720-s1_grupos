/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;

/**
 *
 * @author jc161
 */
public class MultimediaDTO {
    private String nombre;
    
    private String descripcion;
    
    private String link;
    
    public MultimediaDTO()
    {
        
    }

    public MultimediaDTO(MultimediaEntity e)
    {
        nombre=e.getNombre();
        link=e.getLink();
        descripcion=e.getDescripcion();
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    public MultimediaEntity toEntity()
    {
        MultimediaEntity e= new MultimediaEntity();
        e.setDescripcion(descripcion);
        e.setLink(link);
        e.setNombre(nombre);
        return e;
    }
}
