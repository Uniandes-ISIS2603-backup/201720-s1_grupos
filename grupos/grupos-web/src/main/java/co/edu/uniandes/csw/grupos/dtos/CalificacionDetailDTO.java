/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.CalificacionEntity;

/**
 *
 * @author jc161
 */
public class CalificacionDetailDTO extends CalificacionDTO {
    
    /*
    private UsuarioDTO calificador;
    
    private BlogDTO blog;*/
    
    public CalificacionDetailDTO()
    {
        
    }
    
    public CalificacionDetailDTO(CalificacionEntity e)
    {
        super(e);
        /*
        calificador=new UsuarioDTO(e.getCalificador());
        blog=new BlogDTO(e.getBlog());
        */
    }
    /*

    public UsuarioDTO getCalificador() {
        return calificador;
    }

    public void setCalificador(UsuarioDTO calificador) {
        this.calificador = calificador;
    }

    public BlogDTO getBlog() {
        return blog;
    }

    public void setBlog(BlogDTO blog) {
        this.blog = blog;
    }*/
    
    public CalificacionEntity toEntity()
    {
        CalificacionEntity e= new CalificacionEntity();
        e.setCalificacion(calificacion);
        e.setFecha(fecha);
        e.setId(id);
        /*
        e.setCalificador(calificador.toEntity());
        e.setBlog(blog.toEntity());
        */
        return e;
    }
}
