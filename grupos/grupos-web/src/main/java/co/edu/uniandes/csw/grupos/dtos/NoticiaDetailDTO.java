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
public class NoticiaDetailDTO extends NoticiaDTO{
    
    /*
    @OneToMany
    private List<MultimediaDTO> multimedia;
    @Id
    private UsuarioDTO autor;*/

    public NoticiaDetailDTO()
    {
        
    }
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
