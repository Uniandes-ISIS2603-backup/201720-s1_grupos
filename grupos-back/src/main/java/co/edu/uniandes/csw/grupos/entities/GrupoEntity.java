/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.grupos.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;
/**
 *
 * @author cm.sarmiento10
 */
@Entity
public class GrupoEntity implements Serializable{
    
    /**
     * Id del grupo, PK de la base de datos
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Nombre del grupo
     */
    private String nombre;
    
    /**
     * Descripción del grupo
     */
    private String descripcion;
    
    /**
     * Lista de miembros
     */
    @PodamExclude
    @ManyToMany
    private List<UsuarioEntity> miembros= new ArrayList<UsuarioEntity>();
    
    /**
     * Lista de administradores
     */
    @PodamExclude
    @ManyToMany
    private List<UsuarioEntity> administradores= new ArrayList<UsuarioEntity>();
    
    /**
     * Lista de categorias
     */
    @PodamExclude
    @ManyToMany
    private List<CategoriaEntity> categorias= new ArrayList<CategoriaEntity>();
    
    /**
     * Lista de blogs
     */
    @PodamExclude
    @OneToMany
    private List<BlogEntity> blogsGrupo= new ArrayList<BlogEntity>();
    
    /**
     * Lista de noticias
     */
    @PodamExclude
    @OneToMany
    private List<NoticiaEntity> noticiasGrupo= new ArrayList<NoticiaEntity>();
    
    /**
     * Lista de eventos
     */
    @PodamExclude
    @OneToMany
    private List<EventoEntity> eventosGrupo= new ArrayList<EventoEntity>();
    
    /**
     * 
     * @return el id del grupo
     */
    public Long getId() {
        return id;
    }
    
    /**
     * 
     * @param id, id del grupo a colocarse
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * 
     * @return el nombre del grupo
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * 
     * @param nombre, nombre del grupo a colocarse
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * 
     * @return descripcion del grupo
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * 
     * @param descripcion, descripción del grupo a colocarse
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * 
     * @return Hash del entity
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
    /**
     * 
     * @param obj, objeto a comprarse
     * @return true si tienen el mismo id, false de lo contrario
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
        final GrupoEntity other = (GrupoEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    /**
     * 
     * @return lista de categorías
     */
    public List<CategoriaEntity> getCategorias() {
        return categorias;
    }
    
    /**
     * 
     * @return lista de miembros
     */
    public List<UsuarioEntity> getMiembros() {
        return miembros;
    }
    
    /**
     * 
     * @return lista de administradores
     */
    public List<UsuarioEntity> getAdministradores() {
        return administradores;
    }
    
    /**
     * 
     * @return lista de blogs
     */
    public List<BlogEntity> getBlogsGrupo() {
        return blogsGrupo;
    }
    
    /**
     * 
     * @return lista de noticias 
     */
    public List<NoticiaEntity> getNoticiasGrupo() {
        return noticiasGrupo;
    }
    
    /**
     * 
     * @return lista de eventos 
     */
    public List<EventoEntity> getEventosGrupo() {
        return eventosGrupo;
    }

    /**
     * 
     * @param miembros, lista de miembros a colocar
     */
    public void setMiembros(List<UsuarioEntity> miembros) {
        this.miembros = miembros;
    }

    /**
     * 
     * @param administradores, lista de administradores a colocar
     */
    public void setAdministradores(List<UsuarioEntity> administradores) {
        this.administradores = administradores;
    }

    /**
     * 
     * @param categorias, lista de categorías a colocar
     */
    public void setCategorias(List<CategoriaEntity> categorias) {
        this.categorias = categorias;
    }

    /**
     * 
     * @param blogsGrupo lista de blogs a colocar
     */
    public void setBlogsGrupo(List<BlogEntity> blogsGrupo) {
        this.blogsGrupo = blogsGrupo;
    }

    /**
     * 
     * @param noticiasGrupo lista de noticias a colocar
     */
    public void setNoticiasGrupo(List<NoticiaEntity> noticiasGrupo) {
        this.noticiasGrupo = noticiasGrupo;
    }

    /**
     * 
     * @param eventosGrupo lista de eventos a colocar
     */
    public void setEventosGrupo(List<EventoEntity> eventosGrupo) {
        this.eventosGrupo = eventosGrupo;
    }

    
    
    
    
    
}
