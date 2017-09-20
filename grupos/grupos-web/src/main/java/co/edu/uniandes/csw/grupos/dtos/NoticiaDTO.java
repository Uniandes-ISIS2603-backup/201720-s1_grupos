/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;

/**
 *
 * @author jc161
 */
public class NoticiaDTO {
    protected Long id;
    
    protected String titulo;
    
    protected String informacion;
    
    public NoticiaDTO()
    {
        
    }
    
    public NoticiaDTO(NoticiaEntity e)
    {
        titulo=e.getTitulo();
        informacion=e.getInformacion();
        id=e.getId();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    
   public Long getId()
   {
       return id;
   }
   
   public void setId(Long n)
   {
       id=n;
   }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }
    
    public NoticiaEntity toEntity()
    {
        NoticiaEntity entity= new NoticiaEntity();
        entity.setId(id);
        entity.setInformacion(informacion);
        entity.setTitulo(titulo);
        return entity;
    }

}
