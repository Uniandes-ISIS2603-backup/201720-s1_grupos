/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.entities.CategoriaEntity;
import co.edu.uniandes.csw.grupos.entities.EventoEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * Representación detail de grupo.<br>
 * @author cm.sarmiento10
 */
public class GrupoDetailDTO extends GrupoDTO{
    
    /**
     * Lista de miembros del grupo
     */    
    private List<UsuarioDTO> miembros;
    
    /**
     * Lista de administradores del grupo
     */
    private List<UsuarioDTO> administradores;
    
    /**
     * Lista de categorias del grupo
     */
    private List<CategoriaDTO> categorias;
    
    /**
     * Lista de blogs del grupo
     */
    private List<BlogDTO> blogsGrupo;
    
    /**
     * Lista de noticias del grupo
     */
    private List<NoticiaDTO> noticiasGrupo;
    
    /**
     * Lista de eventos del grupo
     */
    private List<EventoDTO> eventosGrupo;
    
    /**
     * Constructor del grupoDTO con relaciones
     */
    public GrupoDetailDTO() {
        super();
    }
    
    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param entity, grupo a conventir a DTO
     */
    public GrupoDetailDTO(GrupoEntity entity) {
        //Crea un GrupoDTO con el entity que llega
        super(entity);
        if (entity != null) {
            //Crea la lista de categorias y le adiciona las que tiene el entity
            categorias = new ArrayList<>();
            for (CategoriaEntity entityCategoria : entity.getCategorias()) {
                categorias.add(new CategoriaDTO(entityCategoria));
            }
             //Crea la lista de miembros y le adiciona las que tiene el entity
            miembros = new ArrayList<>();
            for (UsuarioEntity entityCategoria : entity.getMiembros()) {
                miembros.add(new UsuarioDTO(entityCategoria));
            }
             //Crea la lista de administradores y le adiciona las que tiene el entity
            administradores = new ArrayList<>();
            for (UsuarioEntity entityCategoria : entity.getAdministradores()) {
                administradores.add(new UsuarioDTO(entityCategoria));
            }
             //Crea la lista de blogs y le adiciona las que tiene el entity
            blogsGrupo = new ArrayList<>();
            for (BlogEntity entityCategoria : entity.getBlogsGrupo()) {
                blogsGrupo.add(new BlogDTO(entityCategoria));
            }
             //Crea la lista de noticias y le adiciona las que tiene el entity
            noticiasGrupo = new ArrayList<>();
            for (NoticiaEntity entityCategoria : entity.getNoticiasGrupo()) {
                noticiasGrupo.add(new NoticiaDTO(entityCategoria));
            }
             //Crea la lista de eventos y le adiciona las que tiene el entity
            eventosGrupo = new ArrayList<>();
            for (EventoEntity entityCategoria : entity.getEventosGrupo()) {
                eventosGrupo.add(new EventoDTO(entityCategoria));
            }
        }
        
    }
    
    /**
     * Transformar un DTO a un Entity
     *
     * @return un GrupoEntity con base en el DTO actual
     */
    @Override
    public GrupoEntity toEntity() {
        GrupoEntity entity = super.toEntity();
        //Le adiciona la lista de categorias del DTO al entity
        if (categorias != null) {
            List<CategoriaEntity> categoriasEntity = new ArrayList<>();
            for (CategoriaDTO dtoCategoria : categorias) {
                categoriasEntity.add(dtoCategoria.toEntity());
            }
            entity.setCategorias(categoriasEntity);
        }
         //Le adiciona la lista de administradores del DTO al entity
        if (administradores != null) {
            List<UsuarioEntity> administradoresEntity = new ArrayList<>();
            for (UsuarioDTO dtoAdmins : administradores) {
                administradoresEntity.add(dtoAdmins.toEntity());
            }
            entity.setAdministradores(administradoresEntity);
        }
         //Le adiciona la lista de miembros del DTO al entity
        if (miembros != null) {
            List<UsuarioEntity> miembrosEntity = new ArrayList<>();
            for (UsuarioDTO dtoMiembro : miembros) {
                miembrosEntity.add(dtoMiembro.toEntity());
            }
            entity.setMiembros(miembrosEntity);
        }
         //Le adiciona la lista de blogs del DTO al entity
        if (blogsGrupo != null) {
            List<BlogEntity> blogsEntity = new ArrayList<>();
            for (BlogDTO dtoBlog : blogsGrupo) {
                blogsEntity.add(dtoBlog.toEntity());
            }
            entity.setBlogsGrupo(blogsEntity);
        }
         //Le adiciona la lista de noticias del DTO al entity
        if (noticiasGrupo != null) {
            List<NoticiaEntity> noticiasEntity = new ArrayList<>();
            for (NoticiaDTO dtoNoticia : noticiasGrupo) {
                noticiasEntity.add(dtoNoticia.toEntity());
            }
            entity.setNoticiasGrupo(noticiasEntity);
        }
         //Le adiciona la lista de eventos del DTO al entity
        if (eventosGrupo != null) {
            List<EventoEntity> eventosEntity = new ArrayList<>();
            for (EventoDTO dtoEvento : eventosGrupo) {
                eventosEntity.add(dtoEvento.toEntity());
            }
            entity.setEventosGrupo(eventosEntity);
        }
        return entity;
    }

    /**
     * 
     * @return la lista de miembros
     */
    public List<UsuarioDTO> getMiembros() {
        return miembros;
    }

    /**
     * 
     * @param miembros, lista de miembros a poner en el DTO
     */
    public void setMiembros(List<UsuarioDTO> miembros) {
        this.miembros = miembros;
    }

    /**
     * 
     * @return la lista de administradores
     */
    public List<UsuarioDTO> getAdministradores() {
        return administradores;
    }

    /**
     * 
     * @param administradores, lista de administradores a poner en el DTO
     */
    public void setAdministradores(List<UsuarioDTO> administradores) {
        this.administradores = administradores;
    }

    /**
     * 
     * @return Lista de categorías
     */
    public List<CategoriaDTO> getCategorias() {
        return categorias;
    }

    /**
     * 
     * @param categorias, lista de categorias a poner en el DTO
     */
    public void setCategorias(List<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }

    /**
     * 
     * @return la lista de blogs
     */
    public List<BlogDTO> getBlogsGrupo() {
        return blogsGrupo;
    }

    /**
     * 
     * @param blogsGrupo, lista de blogs para poner en el DTO
     */
    public void setBlogsGrupo(List<BlogDTO> blogsGrupo) {
        this.blogsGrupo = blogsGrupo;
    }

    /**
     * 
     * @return la lista de noticias
     */
    public List<NoticiaDTO> getNoticiasGrupo() {
        return noticiasGrupo;
    }

    /**
     * 
     * @param noticiasGrupo, lista de noticias para poner en el DTO
     */
    public void setNoticiasGrupo(List<NoticiaDTO> noticiasGrupo) {
        this.noticiasGrupo = noticiasGrupo;
    }

    /**
     * 
     * @return la lista de eventos
     */
    public List<EventoDTO> getEventosGrupo() {
        return eventosGrupo;
    }

    /**
     * 
     * @param eventosGrupo lista de eventos para poner en el DTO
     */
    public void setEventosGrupo(List<EventoDTO> eventosGrupo) {
        this.eventosGrupo = eventosGrupo;
    }
    
    
}
