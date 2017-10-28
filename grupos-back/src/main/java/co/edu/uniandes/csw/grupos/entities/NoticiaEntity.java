/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que modela la noticia del sistema.
 * @author s.guzmanm
 */
@Entity 
public class NoticiaEntity implements Serializable {
    /**
     * Identificación de la noticia.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    /**
     * Titulo de la noticia.
     */
    private String titulo;
    /**
     * Información de la noticia.
     */
    private String informacion;
    /**
     * Lista de multimedia.
     */
    @OneToMany(cascade=CascadeType.PERSIST)
    @PodamExclude
    private List<MultimediaEntity> multimedia;
    /**
     * Autor de la noticia.
     */
    @ManyToOne
    @PodamExclude
    private UsuarioEntity autor;
    /**
     * Lista de comentarios.
     */
    @OneToMany
    @PodamExclude
    private List<ComentarioEntity> comentarios;
    
    /**
     *  Obtiene el título de la noticia.<br>
     * @return titulo
     */
    public String getTitulo() {
        return titulo;
    }
    /**
     * Modifica el título al valor dado por parámetro.<br>
     * @param titulo 
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    /**
     * Obtiene la identificación de la noticia.<br>
     * @return id
     */
   public Long getId()
   {
       return id;
   }

   /**
    * Modifica el valor de la id al dado por parámetro.<br>
    * @param n Valor dado por parámetro.
    */
   public void setId(Long n)
   {
       id=n;
   }
   /**
    * Obtiene la información de la noticia.<br>
    * @return informacion
    */
    public String getInformacion() {
        return informacion;
    }
    /**
     * Modifica la información al valor dado por parámetro.<br>
     * @param informacion 
     */
    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }
    /**
     * Obtiene la lista de multimedia de la noticia.<br>
     * @return multimedia
     */
    public List<MultimediaEntity> getMultimedia() {
        return multimedia;
    }
    /**
     * Modifica la multimedia al valor dado por parámetro.<br>
     * @param multimedia 
     */
    public void setMultimedia(List<MultimediaEntity> multimedia) {
        this.multimedia = multimedia;
    }
    /**
     * Obtiene el autor de la noticia.<br>
     * @return autor
     */
    public UsuarioEntity getAutor() {
        return autor;
    }
    /**
     * Modifica el autor al valor dado por parámetro.<br>
     * @param autor 
     */
    public void setAutor(UsuarioEntity autor) {
        this.autor = autor;
    }
    /**
     * Retorna la lista de comentarios.<br>
     * @return  comentarios
     */
    public List<ComentarioEntity> getComentarios() {
        return comentarios;
    }
    /**
     * Modifica la lista al valor dado por parámetro.<br>
     * @param comentarios 
     */
    public void setComentarios(List<ComentarioEntity> comentarios) {
        this.comentarios = comentarios;
    }
    
    /**
     * Equals de la clase.<br>
     * @param o Noticia para igualar.
     */
    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof NoticiaEntity)) return false;
        NoticiaEntity e = (NoticiaEntity)o;
        if(id.equals(e.getId())) return true;
        return false;
    }
    /**
     * Override de hashcode.<br>
     * @return  hash del id.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.id);
        return hash;
    }
}

