/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.entities.CalificacionEntity;
import co.edu.uniandes.csw.grupos.entities.ComentarioEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;
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
    
    private List<MultimediaDTO> multimedia;
    
    /**
     * Construye un BlogDetailDTO vacío
     */
    public BlogDetailDTO() {
      super();
    }
    
    /**
     * Construye un BlogDetailDTO a partir de un BlogEntity
     * @param entity BlogEntity
     */
    public BlogDetailDTO(BlogEntity entity) {
        super(entity);
        if(entity != null) {
            if(entity.getGrupo()!=null)
            grupo = new GrupoDTO(entity.getGrupo());
            else grupo=new GrupoDetailDTO();
            comentarios = new ArrayList<>();
            calificaciones = new ArrayList<>();
            multimedia = new ArrayList<>();
            if(entity.getComentarios()!=null)
                for(ComentarioEntity com : entity.getComentarios()) {
                    comentarios.add(new ComentarioDTO(com));
                }
            if(entity.getCalificaciones()!=null)
                for(CalificacionEntity com : entity.getCalificaciones()) {
                    calificaciones.add(new CalificacionDTO(com));
                }
            if(entity.getMultimedia()!=null)
                for(MultimediaEntity mul : entity.getMultimedia()) {
                    multimedia.add(new MultimediaDTO(mul));
                }
        }
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

    public List<MultimediaDTO> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<MultimediaDTO> multimedia) {
        this.multimedia = multimedia;
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
        List<MultimediaEntity> multi = new ArrayList<>();
        for(ComentarioDTO com : comentarios) {
            coments.add(com.toEntity());
        }
        for(CalificacionDTO cal : calificaciones) {
            califs.add(cal.toEntity());
        }
        for(MultimediaDTO mul : multimedia) {
            multi.add(mul.toEntity());
        }
        GrupoEntity grupoEntity = grupo==null?null:grupo.toEntity();
        entity.setGrupo(grupoEntity);
        entity.setComentarios(coments);
        entity.setCalificaciones(califs);
        entity.setMultimedia(multi);
        return entity;
    }
    
}
