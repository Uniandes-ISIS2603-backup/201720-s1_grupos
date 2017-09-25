/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.ComentarioEntity;
import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO detallado de Noticia
 * @author jc161
 */
public class NoticiaDetailDTO extends NoticiaDTO{
    
    
    private List<MultimediaDTO> multimedia;
    private UsuarioDTO autor;
    private List<ComentarioDTO> comentarios;
    

    /*
    /**
     * Crea un DTO detallado de noticia vacío.
     */

    public NoticiaDetailDTO()
    {
        
    }

    /**
     * Crea un nuevo DTO detallado de noticia usando la entidad dada por parámetro.<br>
     * @param e Noticia a convertir.
     */

    public NoticiaDetailDTO(NoticiaEntity e)
    {
        super(e);
        comentarios= new ArrayList<>();
        multimedia=new ArrayList<MultimediaDTO>();
        if(e.getMultimedia()!=null)
            for(MultimediaEntity m: e.getMultimedia())
            {
                multimedia.add(new MultimediaDTO(m));
            }
        if(e.getAutor()!=null)
            autor=new UsuarioDTO(e.getAutor());
        if(e.getComentarios()!=null)
            for(ComentarioEntity c:e.getComentarios())
            {
                comentarios.add(new ComentarioDTO(c));
            }
        
    }
    
    
    public List<MultimediaDTO> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<MultimediaDTO> multimedia) {
        this.multimedia = multimedia;
    }

    public UsuarioDTO getAutor() {
        return autor;
    }

    public void setAutor(UsuarioDTO autor) {
        this.autor = autor;
    }

    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }

    
    /**
     * Transforma el DTO detallado en una nueva entidad.<br>
     * @return Nueva entidad formada.
     */
    @Override
    public NoticiaEntity toEntity()
    {
        NoticiaEntity entity= new NoticiaEntity();
        entity.setId(id);
        entity.setInformacion(informacion);
        entity.setTitulo(titulo);
        entity.setAutor(autor.toEntity());
        List<MultimediaEntity> list= new ArrayList<>();
        for(MultimediaDTO m: multimedia)
        {
            list.add(m.toEntity());
        }
        entity.setMultimedia(list);
        List<ComentarioEntity> com= new ArrayList<>();
        for(ComentarioDTO c:comentarios)
        {
            com.add(c.toEntity());
        }
        return entity;
    }
}
