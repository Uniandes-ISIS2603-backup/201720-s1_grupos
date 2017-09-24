/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.entities.CalificacionEntity;
import co.edu.uniandes.csw.grupos.entities.ComentarioEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author se.cardenas
 */
public class BlogDetailDTO extends BlogDTO{
    
    private GrupoDTO grupo;
    
    private List<ComentarioDTO> comentarios;
    
    private List<CalificacionDTO> calificaciones;
    
    /**
     * Construye un BlogDetailDTO vacío
     */
    public BlogDetailDTO() {
        
    }
    
    /**
     * Construye un BlogDetailDTO a partir de un BlogEntity
     * @param entity BlogEntity
     */
    public BlogDetailDTO(BlogEntity entity) {
        super(entity);
        if(entity != null) {
            grupo = new GrupoDTO(entity.getGrupo());
            
            comentarios = new ArrayList<>();
            calificaciones = new ArrayList<>();
            for(ComentarioEntity com : entity.getComentarios()) {
                comentarios.add(new ComentarioDTO(com));
            }
            for(CalificacionEntity com : entity.getCalificaciones()) {
                calificaciones.add(new CalificacionDTO(com));
            }
        }
        //Aquí va el manejo de relaciones
    }

    public GrupoDTO getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoDTO grupo) {
        this.grupo = grupo;
    }

    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }

    public List<CalificacionDTO> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<CalificacionDTO> calificaciones) {
        this.calificaciones = calificaciones;
    }
    
    /**
     * Construye un BlogEntity a partir de un BlogDetailDTO
     * @return BlogEntity
     */
    @Override
    public BlogEntity toEntity() {
        BlogEntity entity = super.toEntity();
        List<ComentarioEntity> coments = new ArrayList<>();
        List<CalificacionEntity> califs = new ArrayList<>();
        for(ComentarioDTO com : comentarios) {
            coments.add(com.toEntity());
        }
        for(CalificacionDTO cal : calificaciones) {
            califs.add(cal.toEntity());
        }
        entity.setGrupo(grupo.toEntity());
        entity.setComentarios(coments);
        entity.setCalificaciones(califs);
        return entity;
    }
    
}
