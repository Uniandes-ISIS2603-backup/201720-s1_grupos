/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
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
            
            List<ComentarioDTO> coments = new ArrayList<>();
            for(ComentarioEntity com : entity.getComentarios()) {
                comentarios.add(new ComentarioDTO(com));
            }
            this.comentarios = coments;
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
    
    /**
     * Construye un BlogEntity a partir de un BlogDetailDTO
     * @return BlogEntity
     */
    @Override
    public BlogEntity toEntity() {
        BlogEntity entity = super.toEntity();
        List<ComentarioEntity> coments = new ArrayList<>();
        for(ComentarioDTO com : comentarios) {
            coments.add(com.toEntity());
        }
        entity.setComentarios(coments);
        entity.setGrupo(grupo.toEntity());
        return entity;
    }
    
}
