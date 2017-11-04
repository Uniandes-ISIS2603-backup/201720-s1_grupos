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
    
    /**
     * Listado de multimedia.
     */
    private List<MultimediaDTO> multimedia;
    /**
     * Autor
     */
    private UsuarioDTO autor;
    /**
     * Listado de comentarios
     */
    private List<ComentarioDTO> comentarios;
    /**
     * Grupo de la noticia
     */
    private GrupoDTO grupo;

    /*
    /**
     * Crea un DTO detallado de noticia vacío.
     */

    public NoticiaDetailDTO()
    {
        //Constructor vacío de la clase
    }

    /**
     * Crea un nuevo DTO detallado de noticia usando la entidad dada por parámetro.<br>
     * @param e Noticia a convertir.
     */

    public NoticiaDetailDTO(NoticiaEntity e)
    {
        super(e);
        comentarios= new ArrayList<>();
        multimedia=new ArrayList<>();
        if(e.getMultimedia()!=null)
        {
            for(MultimediaEntity m: e.getMultimedia())
            {
                multimedia.add(new MultimediaDTO(m));
            }
        }
        if(e.getAutor()!=null)
        {
            autor=new UsuarioDTO(e.getAutor());
        }
        if(e.getGrupo()!=null)
        {
            grupo=new GrupoDTO(e.getGrupo());
        }
        if(e.getComentarios()!=null)
        {
            for(ComentarioEntity c:e.getComentarios())
            {
                comentarios.add(new ComentarioDTO(c));
            }
        }
    }
    
    /**
     * Obtiene la multimedia.<br>
     * @return multimedia
     */
    public List<MultimediaDTO> getMultimedia() {
        return multimedia;
    }
    /**
     * Le da el valor de multimedia a lo que le pasan por parámetro.<br>
     * @param multimedia 
     */
    public void setMultimedia(List<MultimediaDTO> multimedia) {
        this.multimedia = multimedia;
    }
    /**
     * Obtiene el autor del sistema.<br>
     * @return Autor
     */
    public UsuarioDTO getAutor() {
        return autor;
    }
    /**
     * Modifica el autor al valor dado por parámetro.<br>
     * @param autor 
     */
    public void setAutor(UsuarioDTO autor) {
        this.autor = autor;
    }
    /**
     * Obtiene el grupo de la noticia.<br>
     * @return grupo
     */
    public GrupoDTO getGrupo()
    {
        return grupo;
    }
    /**
     * Modifica el grupo al valor dado por parámetro.<br>
     * @param grupo
     */
    public void setGrupo(GrupoDTO grupo)
    {
        this.grupo=grupo;
    }
    /**
     * Obtiene los comentarios.<br>
     * @return comentarios
     */
    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }
    /**
     * Modifica los comentarios al valor dado por parámetro.<br>
     * @param comentarios 
     */
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
        if(autor!=null)
        {
            entity.setAutor(autor.toEntity());
        }
        if(grupo!=null)
        {
            entity.setGrupo(grupo.toEntity());
        }
        List<MultimediaEntity> list= new ArrayList<>();
        if(multimedia!=null)
        {
            for(MultimediaDTO m: multimedia)
            {
                list.add(m.toEntity());
            }
        }
        entity.setMultimedia(list);
        List<ComentarioEntity> com= new ArrayList<>();
        if(comentarios!=null)
        {
            for(ComentarioDTO c:comentarios)
            {
                com.add(c.toEntity());
            }
        }
        entity.setComentarios(com);
        return entity;
    }
}
