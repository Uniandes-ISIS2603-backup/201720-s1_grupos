
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.entities.CategoriaEntity;
import co.edu.uniandes.csw.grupos.entities.EventoEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.GrupoPersistence;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author cm.sarmiento10
 */
public class GrupoLogic {
    
    /**
     * DB donde se guardarán los grupos
     */
    @Inject
    private GrupoPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.
    
    /**
     * Lógica de categorías
     */
    @Inject
    private CategoriaLogic categoriaLogic;
    
    /**
     * Lógica de usuarios
     */
    @Inject
    private UsuarioLogic usuarioLogic;
    
  
    /**
     * Lógica de noticias
     */
    @Inject
    private NoticiaLogic noticiaLogic;
    
    /**
     * Lógica de eventos
     */
    @Inject
    private EventoLogic eventoLogic;
    
    /**
     *
     * @param entity, entidad a guardarse en la DB
     * @param id, id del usuario que creó el grupo
     * @return el grupo recién creado
     * @throws BusinessException si ya existe un grupo con el mismo ID
     */
    public GrupoEntity createGrupo(GrupoEntity entity) throws BusinessException {
        // Verifica la regla de negocio que dice que no puede haber dos Grupoes con el mismo nombre
        if (persistence.findByNombre(entity.getNombre()) != null) {
            throw new BusinessException("Ya existe un grupo con el nombre \"" + entity.getNombre() + "\"");
        }
        // Invoca la persistencia para crear la Grupo
        persistence.create(entity);
        return entity;
    }
    
    /**
     *
     * @return todos los grupos guardados
     */
    public List<GrupoEntity> getGrupos() {
        
        List<GrupoEntity> cities = persistence.findAll();
        return cities;
    }
    
    /**
     *
     * @param id, id del grupo a buscar
     * @return grupo con el id dado por parámetro
     */
    public GrupoEntity getGrupo(Long id) {
        System.out.println("la ID " + id);
        GrupoEntity grupo = persistence.find(id);
        if(grupo==null)
        {
            throw new NotFoundException("No se encontró un grupo con el id: " + id);
        }
        return grupo;
    }
    
    /**
     *
     * @param entity, grupo modificado a guardarse
     * @return el grupo recién modificado
     */
    public GrupoEntity updateGrupo(GrupoEntity entity) {
        GrupoEntity grupo= persistence.find(entity.getId());
        if(grupo==null)
        {
            throw new NotFoundException("No se encontró un grupo con el id: " + entity.getId());
        }
        GrupoEntity newEntity = persistence.update(entity);
        return newEntity;
    }
    
    /**
     *
     * @param id, id del grupo a borrarse
     */
    public void deleteGrupo(Long id) {
        GrupoEntity grupo= persistence.find(id);
        if(grupo==null)
        {
            throw new NotFoundException("No se encontró un grupo con el id: " + id);
        }
        persistence.delete(id);
        
    }
    
    /**
     *
     * @param nombre, nombre del grupo a buscar
     * @return el grupo con el nombre dado
     * @throws NotFoundException, excepción si no encuentra el grupo
     */
    public GrupoEntity getGrupo(String nombre) {
        GrupoEntity entity= persistence.findByNombre(nombre);
        if(entity==null)
        {
            throw new NotFoundException("No se encontró un grupo con el nombre: " + nombre);
        }
        return entity;
    }
    
    /**
     *
     * @param id, id del grupo a buscarle las categorías
     * @return lista de categorías asociadas al grupo
     */
    public List<CategoriaEntity> listCategorias(Long id) {
        return getGrupo(id).getCategorias();
    }
    
    /**
     *
     * @param grupoId, id del grupo donde se buscará la categoría
     * @param categoriaId, id de la categoría a buscar
     * @return categoría asociada con cierto grupo
     */
    public CategoriaEntity getCategoria(Long grupoId, Long categoriaId) {
        List<CategoriaEntity> list = getGrupo(grupoId).getCategorias();
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(categoriaId);
        int index = list.indexOf(categoriaEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }
    
    /**
     *
     * @param grupoId, id del grupo a agregarle una categoría
     * @param categoriaId, categoría a ser vinculada con el grupo
     * @return la categoría recién asociada al grupo
     */
    public CategoriaEntity addCategoria(Long grupoId, Long categoriaId) {
        GrupoEntity grupoEntity = getGrupo(grupoId);
        CategoriaEntity categoriaEntity = categoriaLogic.getCategoria(categoriaId);
        grupoEntity.getCategorias().add(categoriaEntity);
        updateGrupo(grupoEntity);
        return getCategoria(grupoId, categoriaId);
    }
    
    /**
     *
     * @param grupoId, id del grupo donde se quitará la categoría
     * @param categoriaId, id de la categoría a desvincularse del grupo
     */
    public void removeCategoria(Long grupoId, Long categoriaId) {
        GrupoEntity entity = getGrupo(grupoId);
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(categoriaId);
        entity.getCategorias().remove(categoriaEntity);
        updateGrupo(entity);
    }
    
    /**
     *
     * @param id del grupo a buscarle los miembros
     * @return lista de miembros del grupo con id dado
     */
    public List<UsuarioEntity> listMiembros(Long id) {
        return getGrupo(id).getMiembros();
    }
    
    /**
     *
     * @param grupoId, id del grupo al que se le buscará un miembro
     * @param miembroId, id del usuario a agregarse al grupo
     * @return el usuario recién asociado al grupo
     */
    public UsuarioEntity getMiembro(Long grupoId, Long miembroId) {
        List<UsuarioEntity> list = getGrupo(grupoId).getMiembros();
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(miembroId);
        int index = list.indexOf(usuarioEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }
    
    /**
     *
     * @param grupoId, id del grupo al que se le adicionará un miembro
     * @param usuarioId, id del usuario a adicionar como miembro
     * @return el usuario recién añadido como miembro
     * @throws BusinessException, si el miembro ya existe
     */
    public UsuarioEntity addMiembro(Long grupoId, Long usuarioId) throws BusinessException {
        GrupoEntity grupoEntity = getGrupo(grupoId);
        UsuarioEntity usuarioEntity = usuarioLogic.findById(usuarioId);
        grupoEntity.getMiembros().add(usuarioEntity);
        updateGrupo(grupoEntity);
        return getMiembro(grupoId, usuarioId);
    }
    
    /**
     *
     * @param grupoId, id del grupo al que se le desvinculará un miembro
     * @param miembroId, id del miembro que se desvinculará
     */
    public void removeMiembro(Long grupoId, Long miembroId) {
        GrupoEntity entity = getGrupo(grupoId);
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(miembroId);
        entity.getMiembros().remove(usuarioEntity);
        updateGrupo(entity);
    }
    
    /**
     *
     * @param id, id del grupo al que se le buscará los admins
     * @return lista de admins del grupo con id dado
     */
    public List<UsuarioEntity> listAdmins(Long id) {
        return getGrupo(id).getAdministradores();
    }
    
    /**
     *
     * @param grupoId, id del grupo al que se le adicionará un admin
     * @param adminId, id del usuario a buscar
     * @return
     */
    public UsuarioEntity getAdmin(Long grupoId, Long adminId) {
        List<UsuarioEntity> list = getGrupo(grupoId).getAdministradores();
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(adminId);
        int index = list.indexOf(usuarioEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }
    
    /**
     *
     * @param grupoId, id del grupo al que se le asociará un admin
     * @param adminId, id del usuario a asociar como admin
     * @return el usuario recién asociado como admin
     * @throws BusinessException, si no hay un usuario con ese id
     */
    public UsuarioEntity addAdmin(Long grupoId, Long adminId) throws BusinessException {
        GrupoEntity grupoEntity = getGrupo(grupoId);
        UsuarioEntity usuarioEntity = usuarioLogic.findById(adminId);
        grupoEntity.getAdministradores().add(usuarioEntity);
        GrupoEntity g= updateGrupo(grupoEntity);
        return getAdmin(grupoId, adminId);
    }
    
    /**
     *
     * @param grupoId, id del grupo al que se le desvinculará un admin
     * @param adminId, id del usuario a desvincular como admin
     */
    public void removeAdmin(Long grupoId, Long adminId) {
        GrupoEntity entity = getGrupo(grupoId);
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(adminId);
        entity.getAdministradores().remove(usuarioEntity);
        updateGrupo(entity);
    }
    
    /**
     *
     * @param id, id del grupo a buscarle los blogs
     * @return la lista de blogs relacionadas al grupo con id dado
     */
    public List<BlogEntity> listBlogs(Long id) {
        return getGrupo(id).getBlogsGrupo();
    }
    
    /**
     *
     * @param grupoId, id del grupo al que se le asociará un blog
     * @param blogId, id del blog a buscar en el grupo
     * @return el blog con id dado asociado al grupo
     */
    public BlogEntity getBlog(Long grupoId, Long blogId) {
        List<BlogEntity> list = getGrupo(grupoId).getBlogsGrupo();
        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setId(blogId);
        int index = list.indexOf(blogEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }
    
 
    
    /**
     *
     * @param grupoId, id del grupo al que se le desvinculará el blog
     * @param blogId, id del blog a desvincular
     */
    public void removeBlog(Long grupoId, Long blogId) {
        GrupoEntity entity = getGrupo(grupoId);
        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setId(blogId);
        entity.getBlogsGrupo().remove(blogEntity);
        updateGrupo(entity);
    }
    
    /**
     *
     * @param id, id del grupo del que se obtendrán las noticias asociadas
     * @return noticias asociadas al grupo con id dado
     */
    public List<NoticiaEntity> listNoticias(Long id) {
        return getGrupo(id).getNoticiasGrupo();
    }
    
    /**
     *
     * @param grupoId, id del grupo que tiene la noticia asociada
     * @param noticiaId, id de la noticia a buscar
     * @return la noticia con id dado asociada al blog con id dado
     */
    public NoticiaEntity getNoticia(Long grupoId, Long noticiaId) {
        List<NoticiaEntity> list = getGrupo(grupoId).getNoticiasGrupo();
        NoticiaEntity noticiaEntity = new NoticiaEntity();
        noticiaEntity.setId(noticiaId);
        int index = list.indexOf(noticiaEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }
    
    /**
     *
     * @param grupoId, id del grupo al que se le adicionará la noticia
     * @param noticiaId, id de la noticia a asociar con el grupo
     * @return noticia recién asociada al grupo
     * @throws BusinessException si no hay una noticia con id dado
     */
    public NoticiaEntity addNoticia(Long grupoId, Long noticiaId) throws BusinessException {
        GrupoEntity grupoEntity = getGrupo(grupoId);
        NoticiaEntity noticiaEntity = noticiaLogic.getEntity(noticiaId);
        grupoEntity.getNoticiasGrupo().add(noticiaEntity);
        updateGrupo(grupoEntity);
        return getNoticia(grupoId, noticiaId);
    }
    
    public NoticiaEntity createNoticia(Long grupoId, NoticiaEntity entity) throws BusinessException {
        GrupoEntity grupoEntity = getGrupo(grupoId);
        NoticiaEntity noticiaEntity = noticiaLogic.createEntity(entity);
        grupoEntity.getNoticiasGrupo().add(noticiaEntity);
        updateGrupo(grupoEntity);
        return getNoticia(grupoId, noticiaEntity.getId());
    }
    
    public NoticiaEntity updateNoticia(Long grupoId, Long id, NoticiaEntity entity) throws BusinessException
    {
        NoticiaEntity noticia=noticiaLogic.getEntity(id);
        GrupoEntity grupoEntity = getGrupo(grupoId);
        int index=grupoEntity.getNoticiasGrupo().indexOf(noticia);
        if(index<0) throw new NotFoundException("No existe la noticia en el grupo");
        entity.setId(id);
        NoticiaEntity noticiaEntity = noticiaLogic.updateEntity(id,entity);
        grupoEntity.getNoticiasGrupo().set(index, noticiaEntity);
        updateGrupo(grupoEntity);
        return getNoticia(grupoId,noticiaEntity.getId());
    }
    /**
     *
     * @param grupoId, id del grupo al que se le desvinculará la noticia
     * @param noticiaId, id de la noticia a desvincular del grupo
     */
    public void removeNoticia(Long grupoId, Long noticiaId) {
        GrupoEntity entity = getGrupo(grupoId);
        NoticiaEntity noticiaEntity = new NoticiaEntity();
        noticiaEntity.setId(noticiaId);
        entity.getNoticiasGrupo().remove(noticiaEntity);
        updateGrupo(entity);
    }
    
    /**
     *
     * @param id, id del grupo al que se le buscarán los eventos
     * @return la lista de eventos asociadas al grupo
     */
    public List<EventoEntity> listEventos(Long id) {
        return getGrupo(id).getEventosGrupo();
    }
    
    /**
     *
     * @param grupoId, id del grupo del que se obtendrá el evento
     * @param eventoId, id del evento asociado con el grupo a obtener
     * @return evento asociado con el grupo a buscar
     */
    public EventoEntity getEvento(Long grupoId, Long eventoId) {
        List<EventoEntity> list = getGrupo(grupoId).getEventosGrupo();
        EventoEntity eventoEntity= new EventoEntity();
        eventoEntity.setId(eventoId);
        int index = list.indexOf(eventoEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }
    
    /**
     *
     * @param grupoId, id del grupo al que se le vinculará el evento
     * @param eventoId, id del evento a vincularse con el grupo
     * @return el evento recién vinculado
     * @throws BusinessException, excepcion si no encuentra el evento
     * @throws co.edu.uniandes.csw.grupos.exceptions.NotFoundException, excepción si no encuentra el evento
     */
    public EventoEntity addEvento(Long grupoId, Long eventoId) throws BusinessException, co.edu.uniandes.csw.grupos.exceptions.NotFoundException {
        GrupoEntity grupoEntity = getGrupo(grupoId);
        EventoEntity eventoEntity = eventoLogic.getEntity(eventoId);
        grupoEntity.getEventosGrupo().add(eventoEntity);
        updateGrupo(grupoEntity);
        return getEvento(grupoId, eventoId);
    }
    
    /**
     *
     * @param grupoId, id del grupo al que se le desvinculará el evento
     * @param eventoId, id del evento a desvincular del grupo
     */
    public void removeEvento(Long grupoId, Long eventoId) {
        GrupoEntity entity = getGrupo(grupoId);
        EventoEntity eventoEntity = new EventoEntity();
        eventoEntity.setId(eventoId);
        entity.getEventosGrupo().remove(eventoEntity);
        updateGrupo(entity);
    }
}
