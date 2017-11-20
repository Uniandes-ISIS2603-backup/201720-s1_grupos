/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Entidad de blog.<br>
 * @author se.cardenas
 */
@Entity
public class BlogEntity implements Serializable {
    /**
     * Id del blog.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Título del blog.
     */
    private String titulo;
    /**
     * Contenido del blog.
     */
    private String contenido;
    /**
     * Promedio del blog.
     */
    private Double promedio;
    /**
     * Listado de comentarios
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @PodamExclude
    private List<ComentarioEntity> comentarios;
    /**
     * Grupo
     */
    @ManyToOne
    @PodamExclude
    private GrupoEntity grupo;
    /**
     * Grupo
     */
    @ManyToMany(mappedBy="blogsFavoritos") 
    @PodamExclude
    private List<UsuarioEntity> usuarios;
    /**
     * Calificaciones
     */
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    @PodamExclude
    private List<CalificacionEntity> calificaciones;
    /**
     * Listado de multimedia
     */
    @ManyToMany(cascade=CascadeType.PERSIST)
    @PodamExclude
    private List<MultimediaEntity> multimedia;
    /**
     * Id del blog.<br>
     * @return Id
     */
    public Long getId() {
        return id;
    }
    /**
     * Remplaza el valor del id por el dado en parámetro.<br>
     * @param id  Id remplazada.
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Obtiene el título del blog.<br>
     * @return titulo
     */
    public String getTitulo() {
        return titulo;
    }
    /**
     * Modifica el título al valor dado por parámetro.<br>
     * @param titulo Título del blog
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    /**
     * Obtiene el contenido del blog.<br>
     * @return contenido
     */
    public String getContenido() {
        return contenido;
    }
    /**
     * Modifica el contenido al valor dado por parámetro.<br>
     * @param contenido Contenido a modificar.
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    /**
     * Obtiene el promedio.<br>
     * @return Promedio del blog
     */
    public Double getPromedio() {
        return promedio;
    }
    /**
     * Modifica el promedio al valor dado por parámetro.<br>
     * @param promedio Valor dado
     */
    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }
    /**
     * Obtiene todos los cometnarios.<br>
     * @return Listado de entidades de cometnario
     */
    public List<ComentarioEntity> getComentarios() {
        return comentarios;
    }
    /**
     * Modifica los comentarios al valor dado por parámetro.<br>
     * @param comentarios 
     */
    public void setComentarios(List<ComentarioEntity> comentarios) {
        this.comentarios = comentarios;
    }
    /**
     * Obtiene el grupo.<br>
     * @return grupo
     */
    public GrupoEntity getGrupo() {
        return grupo;
    }
    /**
     * Modifica el grupo al valor dado por parámetro.<br>
     * @param grupo 
     */
    public void setGrupo(GrupoEntity grupo) {
        this.grupo = grupo;
    }
    /**
     * Obtiene las calificaciones del blog.<br>
     * @return calificaciones
     */
    public List<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }
    /**
     * Modifica las calificaciones al valor dado por parámetro.<br>
     * @param calificaciones 
     */
    public void setCalificaciones(List<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }
    /**
     * Obtiene la multimedia.<br>
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
     * Lista de usuarios
     * @return usuarios
     */
    public List<UsuarioEntity> getUsuarios() {
        return usuarios;
    }

    /**
     * Cambia la lista de usuarios
     * @param usuarios 
     */
    public void setUsuarios(List<UsuarioEntity> usuarios) {
        this.usuarios = usuarios;
    }
    
    
    /**
     * Equals de la clase.<br>
     * @param obj Objeto a comparar.<br>
     * @return Si es o no igual.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BlogEntity other = (BlogEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    /**
     * hashcode generado.<br>
     * @return hash generado.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
    
    
}
