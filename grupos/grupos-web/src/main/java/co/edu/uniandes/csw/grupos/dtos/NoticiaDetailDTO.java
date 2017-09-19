/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;

/**
 * DTO detallado de Noticia
 * @author jc161
 */
public class NoticiaDetailDTO extends NoticiaDTO{
    
    /*
    
    private List<MultimediaDTO> multimedia;
    
    private UsuarioDTO autor;*/
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
        /*
        multimedia=new List<MultimediaDTO>();
        for(MultimediaEntity m: e.getMultimedia())
        {
            multimedia.add(new MultimediaDTO(m));
        }
        autor=new UsuarioDTO(e.getAutor());
        */
    }
    
    /*
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
    }*/
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
        /*
        entity.setAutor(autor.toEntity());
        List<MultimediaEntity> list= new List<MultimediaEntity>();
        for(MultimediaDTO m: multimedia)
        {
            list.add(m.toEntity());
        }
        entity.setMultimedia(list);
        */
        return entity;
    }
}
