/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.EventoEntity;
import co.edu.uniandes.csw.grupos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.EventoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

/**
 * Lógica de un evento.<br>
 * @author js.ramos14
 */
@Stateless
public class EventoLogic {
    /**
     * Persistencia de un evento.<br>
     */
    @Inject
            EventoPersistence persistence;
    /**
     * Lógica de usuario.
     */
    @Inject
            UsuarioLogic usuario;
    /**
     * Lógica de patrocinio.
     */
    @Inject
            PatrocinioLogic patrocinio;
    
    /**
     * Obtiene una entidad con el id dado.<br>
     * @param id Id de entidad.<br>
     * @return Entidad dada.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException Excepción de no encontrada.
     */
    public EventoEntity getEntity(Long id) throws BusinessException
    {
        if(id == null) throw new BusinessException("La id solicitada no puede estar vacia o nula");
        EventoEntity entity = persistence.find(id);
        if(entity== null) throw new NotFoundException("No existe un evento con la id " + id +" solicitada");
        return entity;
    }
    
    /**
     * Obtiene todas las entidades.<br>
     * @return Lista de eventos.
     */
    public List<EventoEntity> getAll()
    {
        return persistence.findAll();
    }
    
    /**
     * Crea un nuevo evento.<br>
     * @param entity Entidad a persistir.<br>
     * @return Entidad persistida.<br>
     * @throws BusinessException Excepción de negocio
     */
    public EventoEntity createEntity(EventoEntity entity) throws BusinessException
    {
        if(entity == null) throw new BusinessException("No se puede agregar algo nulo");
        if(entity.getFechaInicio().after(entity.getFechaFin())) throw new BusinessException("Las fechas no coinciden");
        return persistence.create(entity);
    }
    
    /**
     * Actualiza el evento al valor dado.<br>
     * @param entity Entidad a actualizar.<br>
     * @return Entidad actualizada.<br>
     * @throws NotFoundException Si no se encuentra.<br>
     * @throws BusinessException Excepción de negocio.
     */
    public EventoEntity updateEntity(EventoEntity entity) throws BusinessException
    {
        EventoEntity Evento= persistence.find(entity.getId());
        if(Evento==null)
        {
            throw new NotFoundException("No se encontró una Evento con el id: " + entity.getId());
        }
        EventoEntity newEntity = persistence.update(entity);
        return newEntity;
    }
    /**
     * Borra la entidad de evento.<br>
     * @param entity Entidad dada.<br>
     * @throws BusinessException Excepción de negocio.<br>
     * @throws NotFoundException  Si no se encuentra.
     */
    public void deleteEntity(EventoEntity entity) throws BusinessException
    {
        EventoEntity eventoEntity = persistence.find(entity.getId());
        if(eventoEntity==null)
        {
            throw new NotFoundException("No se encontró una Evento con el id: " + entity.getId());
        }
        /*
        if(eventoEntity.getPatrocinios()!=null)
        {
        for(PatrocinioEntity p:eventoEntity.getPatrocinios())
        {
        removePatrocinio(eventoEntity.getId(),p.getId());
        }
        }*/
        persistence.delete(entity.getId());
    }
    
    /**
     * instancia de Evento
     *
     * @param EntityId Identificador de la instancia de Evento
     * @return Colección de instancias de UsuarioEntity asociadas a la instancia
     * de Evento
     *
     */
    public List<UsuarioEntity> listUsuarios(Long EntityId) throws BusinessException {
        return getEntity(EntityId).getUsuarios();
    }
    
    /**
     * Obtiene una instancia de UsuarioEntity asociada a una instancia de Book
     *
     * @param entityId Identificador de la instancia de Evento
     * @param usuariosId Identificador de la instancia de Usuario
     *
     */
    public UsuarioEntity getUsuario(Long entityId, Long usuariosId) throws BusinessException {
        List<UsuarioEntity> list = getEntity(entityId).getUsuarios();
        UsuarioEntity usuariosEntity = new UsuarioEntity();
        usuariosEntity.setId(usuariosId);
        int index = list.indexOf(usuariosEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }
    
    /**
     * Asocia un Usuario existente a un Evento
     *
     * @param entityId Identificador de la instancia de Evento
     * @param usuarioId Identificador de la instancia de Usuario
     * @return Instancia de UsuarioEntity que fue asociada a Evento
     *
     */
    public UsuarioEntity addUsuario(Long entityId, Long usuarioId) throws BusinessException {
        EventoEntity entity = getEntity(entityId);
        UsuarioEntity usuarioEntity = usuario.findById(usuarioId);
        int index=entity.getUsuarios().indexOf(usuarioEntity);
        if(index>=0) throw new BusinessException("Ya existe el usuario");
        usuarioEntity.getEventos().add(entity);
        usuario.updateUser(usuarioEntity.getId(), usuarioEntity);
        entity.getUsuarios().add(usuarioEntity);
        updateEntity(entity);
        return getUsuario(entityId, usuarioId);
    }
    
    /**
     * Desasocia un Usuario existente de un Evento existente
     *
     * @param entityId Identificador de la instancia de Evento
     * @param usuarioId Identificador de la instancia de Usuario
     *
     */
    public void removeUsuario(Long entityId, Long usuarioId) throws BusinessException {
        EventoEntity entity = getEntity(entityId);
        UsuarioEntity usuarioEntity = usuario.findById(usuarioId);
        int index=usuarioEntity.getEventos().indexOf(entity);
        if(index<0) throw new BusinessException("No existe el usuario");
        usuarioEntity.getEventos().remove(index);
        usuario.updateUser(usuarioEntity.getId(), usuarioEntity);
    }
    
    /**
     * Obtiene una colección de instancias de PatrocinioEntity asociadas a una
     * instancia de Evento
     *
     * @param EntityId Identificador de la instancia de Evento
     * @return Colección de instancias de PatrocinioEntity asociadas a la instancia
     * de Evento
     *
     */
    public List<PatrocinioEntity> listPatrocinios(Long EntityId) throws BusinessException {
        return getEntity(EntityId).getPatrocinios();
    }
    
    /**
     * Obtiene una instancia de PatrocinioEntity asociada a una instancia de Book
     *
     * @param entityId Identificador de la instancia de Evento
     * @param patrociniosId Identificador de la instancia de Patrocinio
     *
     */
    public PatrocinioEntity getPatrocinio(Long entityId, Long patrociniosId) throws BusinessException{
        List<PatrocinioEntity> list = getEntity(entityId).getPatrocinios();
        PatrocinioEntity patrociniosEntity = new PatrocinioEntity();
        patrociniosEntity.setId(patrociniosId);
        int index = list.indexOf(patrociniosEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }
    
    /**
     * Asocia un Patrocinio existente a un Evento
     *
     * @param entityId Identificador de la instancia de Evento
     * @param patrocinioId Identificador de la instancia de Patrocinio
     * @return Instancia de PatrocinioEntity que fue asociada a Evento
     *
     */
    public PatrocinioEntity addPatrocinio(Long entityId, Long patrocinioId) throws BusinessException {
        EventoEntity entity = getEntity(entityId);
        PatrocinioEntity patrocinioEntity = getSpecificPatrocinio(patrocinioId);
        if(patrocinioEntity == null) throw new NotFoundException("No existe la entidad buscada");
        int index=entity.getPatrocinios().indexOf(patrocinioEntity);
        if(index>=0) throw new BusinessException("Ya existe el patrocinio");
        entity.getPatrocinios().add(patrocinioEntity);
        patrocinioEntity.setEvento(entity);
        patrocinio.updatePatrocinio(patrocinioId, patrocinioEntity);
        return getPatrocinio(entityId, patrocinioId);
    }
    
    /**
     * Actualiza el patrocinio dados un id del evento, el id del patrocinio, y el nuevo patrocinio
     * @param id identificador del usuario
     * @param patrocinioId identificador del patrocinio
     * @param np nuevo patrocinio
     * @return
     * @throws BusinessException
     */
    public PatrocinioEntity updatePatrocinio(Long id, Long patrocinioId, PatrocinioEntity np) throws BusinessException{
        EventoEntity u = getEntity(id);
        np.setId(patrocinioId);
        if(u==null){
            throw new NotFoundException("El usuario no existe");
        }
        if(u.getPatrocinios()==null){
            throw new BusinessException("No hay patrocinios para actualizar");
        }
        int pos = u.getPatrocinios().indexOf(np);
        if(pos<0) throw new NotFoundException("El patrocinio no existe en la lista del usuario");
        
        np.setEvento(u);
        PatrocinioEntity cambio = patrocinio.updatePatrocinio(np.getId(), np);
        //u.getPatrocinios().set(pos, cambio);
        return cambio;
    }
    
    /**
     * Desasocia un Patrocinio existente de un Evento existente
     *
     * @param entityId Identificador de la instancia de Evento
     * @param patrocinioId Identificador de la instancia de Patrocinio
     *
     */
    public void removePatrocinio(Long entityId, Long patrocinioId) throws BusinessException{
        EventoEntity entity = getEntity(entityId);
        PatrocinioEntity patrocinioEntity = new PatrocinioEntity();
        patrocinioEntity.setId(patrocinioId);
        int index=entity.getPatrocinios().indexOf(patrocinioEntity);
        if(index<0) throw new BusinessException("No existe el patrocinio");
        entity.getPatrocinios().remove(index);
        updateEntity(entity);
    }
    
    /**
     * Obtiene un patrocinio específico de la lógica de patrocinio.<br>
     * @param patrocinioId Id de patrocinio.<br>
     * @return Entidad de patrocinio.<br>
     * @throws BusinessException Excepción de negocio.
     */
    private PatrocinioEntity getSpecificPatrocinio(Long patrocinioId) throws BusinessException {
        List<PatrocinioEntity> pat=patrocinio.allPatrocinios();
        for(PatrocinioEntity p: pat)
        {
            if(p.getId().equals(patrocinioId))
            {
                return p;
            }
        }
        return null;
    }
}
