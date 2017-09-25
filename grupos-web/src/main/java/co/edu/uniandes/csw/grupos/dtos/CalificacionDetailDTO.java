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
    
    
    private UsuarioDTO calificador;
    
    private BlogDTO blog;
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
        calificador=new UsuarioDTO(e.getCalificador());
        blog=new BlogDTO(e.getBlog());
        
    }
    
    /**
     * Obtiene el calificador.<br>
     * @return Calificador
     */
    public UsuarioDTO getCalificador() {
        return calificador;
    }
    /**
     * Modifica el calificador al valor dado.<br>
     * @param calificador 
     */
    public void setCalificador(UsuarioDTO calificador) {
        this.calificador = calificador;
    }
    /**
     * Obtiene el blog.<br>
     * @return blog
     */
    public BlogDTO getBlog() {
        return blog;
    }
    /**
     * Cambia el blog por el valor dado por parámetro.<br>
     * @param blog 
     */
    public void setBlog(BlogDTO blog) {
        this.blog = blog;
    }
    
    /**
     * Transforma el DTO detallado en una entidad.<br>
     * @return Enitdad formada.
     */
    @Override
    public CalificacionEntity toEntity()
    {
        CalificacionEntity e= super.toEntity();
        e.setCalificador(calificador.toEntity());
        if(blog!=null)
        {
             e.setBlog(blog.toEntity());
        }
        return e;
    }
}
