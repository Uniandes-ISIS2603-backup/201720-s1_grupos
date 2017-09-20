/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.CalificacionEntity;

/**
 * Representación detallada del DTO
 * @author jc161
 */
public class CalificacionDetailDTO extends CalificacionDTO {
    
    /*
    private UsuarioDTO calificador;
    
    private BlogDTO blog;*/
    /**
     * COnstructor vacío.
     */
    public CalificacionDetailDTO()
    {
        
    }

    /**
     * Constructor de un DTO detallado que recibe una entidad como parámetro.<br>
     * @param e  Entidad de parámetro.
     */
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
    
    /**
     * Transforma el DTO detallado en una entidad.<br>
     * @return Enitdad formada.
     */
    @Override
    public CalificacionEntity toEntity()
    {
        CalificacionEntity e= super.toEntity();
        /*
        e.setCalificador(calificador.toEntity());
        e.setBlog(blog.toEntity());
        */
        return e;
    }
}
