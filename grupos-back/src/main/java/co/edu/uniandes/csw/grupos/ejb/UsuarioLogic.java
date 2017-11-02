/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.entities.EmpresaEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.grupos.entities.TarjetaEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.UsuarioPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

/**
 * Lógica de usuario
 * @author tefa
 */
@Stateless
public class UsuarioLogic {
    /**
     * Se inyecta la persistencia del usuario
     */
    @Inject
    UsuarioPersistence per;
    
    /**
     * Se inyecta la logica de tarjeta
     */
    @Inject
    TarjetaLogic tarjetaLogic;
    
    /**
     * Se inyecta la logica de empresa
     */
    @Inject
    EmpresaLogic empresaLogic;
    
    /**
     * Se inyecta la logica de noticia
     */
    @Inject
    NoticiaLogic noticiaLogic;
    
    /**
     * Se inyecta la logica de patrocinio
     */
    @Inject
    PatrocinioLogic patrocinioLogic;
    
    /**
     * Metodo que crea un nuevo usuario
     * @param puser entidad nueva a crear
     * @return entidad creada
     * @throws BusinessException si ya existe un usuario con el id especificado. 
     */
    public UsuarioEntity createUser(UsuarioEntity puser) throws BusinessException
    {
        //puser.setId(new Long(1));
        UsuarioEntity check= per.findByEmail(puser.getEmail());
        if(check != null){
            throw new BusinessException("Ya existe un usuario con ese email");
        }
        UsuarioEntity add = per.createEntity(puser);
        return add;
    }
    
    /**
     * Método que busca todos los usuarios registrados en la aplicación.
     * @return lista con todos los usuarios de la aplicación
     * @throws BusinessException 
     */
    public List<UsuarioEntity> allUsers() throws BusinessException
    {
        return per.findAll();
    }
    
    /**
     * Busca un usuario por su email
     * @param pEmail email del usuario a buscar
     * @return entidad que tiene el email especificado por parametro
     */
    public UsuarioEntity findByEmail(String pEmail){
        return per.findByEmail(pEmail);
    }
    
    /**
     * Busca un usuario por su id
     * @param pid id del usuario a buscar
     * @return Entidad que tiene el id especificado por parametro
     * @throws BusinessException 
     */
    public UsuarioEntity findById(Long pid)
    {
        return per.find(pid);
    }
    
    /**
     * Método que actualiza un usuario
     * @param id identificador del usuario que se quiere actualizar.
     * @param puser nueva información del usuario
     * @return usuario actualizado
     * @throws BusinessException si no existe un usuario con el id especificado.
     */
    public UsuarioEntity updateUser(Long id, UsuarioEntity puser) 
    {
        UsuarioEntity found = per.find(id);
        if(found == null)
        {
            throw new NotFoundException("No existe el usuario que se quiere actualizar");
        }
        else
        {
            puser.setId(id);
            return per.updateEntity(puser);
        }
    }
    
    /**
     * Metodo que retorna las tarjetas de un usuario
     * @param id identificador del usuario que posee las tarjetas
     * @return lista de tarjetas del usuario
     */
    public List<TarjetaEntity> listTarjetas(Long id) throws BusinessException{
        return findById(id).getTarjetas();
    }
    
    /**
     * Método que retorna una tarjeta específica de un usuario
     * @param id Identificador del usuario
     * @param pNumTarjeta Numero que identifica la tarjeta que se desea buscar.
     * @return 
     */
    public TarjetaEntity getTarjeta(Long id, int pNumTarjeta) throws BusinessException{
        List<TarjetaEntity> tarjetasUsuario = findById(id).getTarjetas();
        TarjetaEntity tarjetaBuscada = null;
        
        for(TarjetaEntity tarjeta : tarjetasUsuario){
            if(tarjeta.getNumero() == pNumTarjeta){
                tarjetaBuscada = tarjeta;
                break;
            }
         
        }
        
        return tarjetaBuscada;
    }
    
    /**
     * Método que relaciona una nueva tarjeta a un usuario
     * @param id Identificador del usuario
     * @param entity Nueva tarjeta
     * @return Nueva Tarjeta
     * @throws BusinessException Cuando se trata de adicionar una tarjeta que ya existe.
     */
    public TarjetaEntity addTarjeta(Long id, TarjetaEntity entity) throws BusinessException{
        TarjetaEntity tarjetaNueva = tarjetaLogic.createTarjeta(entity);
        UsuarioEntity usuario = findById(id);
        usuario.getTarjetas().add(tarjetaNueva);
        return tarjetaLogic.getTarjetaByNumero(entity.getNumero());
        
    }

    /**
     * Méetodo que modifica una tarjeta asociada a un usuario
     * @param id Identificador del usuario dueno de la tarjeta
     * @param entity Entidad que representa la tarjeta con la nueva informacion
     * @return Tarjeta modificada
     * @throws BusinessException En caso de que se trate de modificar una tarjeta que no exista.
     */
    public TarjetaEntity updateTarjeta(Long id, TarjetaEntity entity) throws BusinessException{
        TarjetaEntity tarjetaNueva = tarjetaLogic.update(entity);
        UsuarioEntity usuario = findById(id);
        
        List<TarjetaEntity> tarjetas = usuario.getTarjetas();
        
        for(TarjetaEntity tarjeta : tarjetas){
            if(tarjeta.getNumero() == tarjetaNueva.getNumero()){
                tarjetas.remove(tarjeta);
                tarjetas.add(tarjetaNueva);
                break;
            }
        }
        
        return tarjetaNueva;
    }
    
    /**
     * Método que elimina una tarjeta asociada a un usuario
     * @param id Identificador del usuario dueno de la tarjeta
     * @param pNumTarjeta Número que identifica a la tarjeta 
     * @throws BusinessException En caso de que se trate de borrar una tarjeta que no existe.
     */
    public void removeTarjeta(Long id, int pNumTarjeta) throws BusinessException{
        UsuarioEntity usuario = findById(id);
        TarjetaEntity tarjetaBorrar = getTarjeta(id, pNumTarjeta);
        
        usuario.getTarjetas().remove(tarjetaBorrar);
        tarjetaLogic.deleteTarjeta(pNumTarjeta);
         
    }
    
    
     /**
     * Método que retorna la empresa de un usuario
     * @param id Identificador del usuario
     * @return Empresa del usuario
     */
    public EmpresaEntity getEmpresa(Long id) throws BusinessException{
        EmpresaEntity empresa = findById(id).getEmpresa();        
        return empresa;
    }
    
     /**
     * Método que relaciona una nueva empresa a un usuario
     * @param id Identificador del usuario
     * @param entity Nueva empresa
     * @return Nueva Empresa
     * @throws BusinessException Cuando se trata de adicionar una empresa que ya existe.
     */
    public EmpresaEntity addEmpresa(Long id, EmpresaEntity entity) throws BusinessException{
        EmpresaEntity empresaNueva = empresaLogic.createEmpresa(entity);
        UsuarioEntity usuario = findById(id);
        if(usuario ==null){
            throw new BusinessException("No hay un usuario con el id especificado");
        }
        usuario.setEmpresa(empresaNueva);
        return empresaLogic.getEmpresaByNit(empresaNueva.getNit());
        
    }
    
    
        /**
     * Méetodo que modifica una empresa asociada a un usuario
     * @param id Identificador del usuario dueno de la tarjeta
     * @param entity Entidad que representa la empresa con la nueva informacion
     * @return Empresa modificada
     * @throws BusinessException En caso de que se trate de modificar una empresa que no existe.
     */
    public EmpresaEntity updateEmpresa(Long id, EmpresaEntity entity) throws BusinessException{
        EmpresaEntity empresaNueva = empresaLogic.update(entity);
        UsuarioEntity usuario = findById(id);
        
        usuario.setEmpresa(empresaNueva);   
        
        return empresaNueva;
    }
    
    
     /**
     * Método que elimina una empresa asociada a un usuario
     * @param id Identificador del usuario dueno de la tarjeta
     * @throws BusinessException En caso de que se trate de borrar una empresa que no existe.
     */
    public void removeEmpresa(Long id) throws BusinessException{
        UsuarioEntity usuario = findById(id);
        EmpresaEntity empresa = usuario.getEmpresa();
        usuario.setEmpresa(null);
        empresaLogic.deleteEmpresa(empresa.getNit());
         
    }
    
    /**
     * Retorna la noticia buscada por el id dado por parametro
     * @param id identificador unico de la noticia
     * @return devuelve la noticia con el id dado, null en caso de no encontrar nada
     * @throws BusinessException 
     */
    public NoticiaEntity getNoticia(Long idUsuario, Long idNoticia) throws BusinessException{
        UsuarioEntity usuario = findById(idUsuario);
        if(usuario == null){
            throw new BusinessException("No existe el usuario con id especificado");
        }
        NoticiaEntity res = noticiaLogic.getEntity(idNoticia);
        if(usuario.getNoticias()==null) usuario.setNoticias(new ArrayList<>());
        int index=usuario.getNoticias().indexOf(res);
        if(index<0) throw new NotFoundException("No existe la noticia del usuario dado");
        return usuario.getNoticias().get(index);
    }
    
    /**
     * Agrega una noticia a la lista de noticias del usuario
     * @param id identificador unico del usuario que se le quiere agregar una noticia
     * @param nn nueva noticia que se quiere agregar
     * @return noticiaEntity agregada
     * @throws BusinessException si el usuario no existe.
     */
    public NoticiaEntity addNoticia(Long id, NoticiaEntity nn) throws BusinessException{
        UsuarioEntity usuario = findById(id);
        if(usuario == null){
            throw new BusinessException("No existe el usuario con id especificado");
        }
        NoticiaEntity newn = noticiaLogic.createEntity(nn);
        List<NoticiaEntity> news= usuario.getNoticias();
        if(news==null)
        {
            usuario.setNoticias(new ArrayList<>());
            news=usuario.getNoticias();
        }
        news.add(newn);
        usuario.setNoticias(news);
        return newn;
    }
    
    /**
     * Actualiza una noticia al usuario con id dado por parámetro
     * @param id identificador unico del usuario
     * @param nn nueva noticia que se quiere actualizar
     * @param noticiaId Id de la noticia
     * @return noticia actualizada
     * @throws BusinessException si el usuario no existe
     */
    public NoticiaEntity updateNoticia(Long id, Long noticiaId, NoticiaEntity nn) throws BusinessException{
        UsuarioEntity usuario = findById(id);
        nn.setId(noticiaId);
        if(usuario == null){
            throw new BusinessException("No existe el usuario con id especificado");
        }
        if(usuario.getNoticias()==null) throw new BusinessException("No hay noticias a actualizar");
        int index= usuario.getNoticias().indexOf(nn);
        if(index<0) throw new NotFoundException("La noticia no existe para la ista del usuario");
        NoticiaEntity change = noticiaLogic.updateEntity(nn.getId(), nn);
        usuario.getNoticias().set(index, change);
        return usuario.getNoticias().get(index);
    }
    
    /**
     * Elimina una noticia con identificador dado por parametro del usuario especificado
     * @param idUsuario identificador unico del usuario
     * @param idNoticia identificador unico de la noticia
     * @throws BusinessException si el usuario no existe.
     */
    public void removeNoticia(Long idUsuario, Long idNoticia) throws BusinessException{
        UsuarioEntity usuario = findById(idUsuario);
        if(usuario == null){
            throw new BusinessException("No existe el usuario con id especificado");
        }
        noticiaLogic.deleteEntity(idNoticia);
        usuario.deleteNoticia(idNoticia);
    }

    public List<NoticiaEntity> getNoticias(Long id) throws BusinessException {
        UsuarioEntity u = findById(id);
        if(u==null) throw new NotFoundException("El usuario no existe");
        return u.getNoticias();
    }

    /**
     * Da todos los patrocinios de un usuario con el id dado por parámetro
     * @param id identificador del usuario
     * @return lista de patrocinios
     * @throws BusinessException 
     */
    public List<PatrocinioEntity> getPatrocinios(Long id) throws BusinessException{
        UsuarioEntity u = findById(id);
        if(u==null){
            throw new NotFoundException("El usuario no existe");
        }
        return u.getPatrocinios();
    }
    
    /**
     * Genera un nuevo patrocinio de un usuario con el id dado por parametro
     * @param id identificador del usuario
     * @param pe patrocinio que se quiere crear
     * @return patrocinio creado
     * @throws BusinessException 
     */
    public PatrocinioEntity addPatrocinio(Long id, PatrocinioEntity pe) throws BusinessException{
        UsuarioEntity u = findById(id);
        if(u==null){
            throw new NotFoundException("El usuario no existe");
        }
        pe.setUsuario(u);
        PatrocinioEntity nuevo = patrocinioLogic.createPatrocinio(pe);
        return nuevo;
    }

    /**
     * Actualiza el patrocinio dados un id del usuario, el id del patrocinio, y el nuevo patrocinio
     * @param id identificador del usuario
     * @param patrocinioId identificador del patrocinio
     * @param np nuevo patrocinio
     * @return
     * @throws BusinessException 
     */
    public PatrocinioEntity updatePatrocinio(Long id, Long patrocinioId, PatrocinioEntity np) throws BusinessException{
        UsuarioEntity u = findById(id);
        np.setId(patrocinioId);
        if(u==null){
            throw new NotFoundException("El usuario no existe");
        }
        if(u.getPatrocinios()==null){
            throw new BusinessException("No hay patrocinios para actualizar");
        }
        int pos = u.getPatrocinios().indexOf(np);
        if(pos<0) throw new NotFoundException("El patrocinio no existe en la lista del usuario");
        
        np.setUsuario(u);
        PatrocinioEntity cambio = patrocinioLogic.updatePatrocinio(np.getId(), np);
        //u.getPatrocinios().set(pos, cambio);
        return cambio;
    }

    /**
     *
     * @param id, id del grupo al que se le buscarán los eventos
     * @return la lista de eventos asociadas al grupo
     */
    public List<BlogEntity> listBlogs(Long id) {
        return findById(id).getBlogsFavoritos();
    }
    
    /**
     *
     * @param usuarioId, id del usuario del que se obtendrá el blog
     * @param blogId, id del evento asociado con el grupo a obtener
     * @return evento asociado con el grupo a buscar
     */
    public BlogEntity getBlog(Long usuarioId, Long blogId) {
        List<BlogEntity> list = findById(usuarioId).getBlogsFavoritos();
        BlogEntity blogEntity= new BlogEntity();
        blogEntity.setId(blogId);
        int index = list.indexOf(blogEntity);
        if (index >= 0) {
            return list.get(index);
        }
        throw new NotFoundException("No se encuentra el elemento buscado");
    }
    
    /**
     *
     * @param usuarioId, id del usuario al que se le vinculará el blog
     * @param blogId, id del evento a vincularse con el grupo
     * @return el blog recién vinculado
     * @throws BusinessException, excepcion si no encuentra el blog
     * @throws co.edu.uniandes.csw.grupos.exceptions.NotFoundException, excepción si no encuentra el blog
     */
    public void addBlog(Long usuarioId, BlogEntity blogEntity) throws BusinessException {
        UsuarioEntity usuarioEntity = findById(usuarioId);
        int indice= usuarioEntity.getBlogsFavoritos().indexOf(blogEntity);
        if(indice>0)
        {
            throw new BusinessException("El usuario actual ya posee este blog como favorito");
        }
        usuarioEntity.getBlogsFavoritos().add(blogEntity);
        updateUser(usuarioId, usuarioEntity);
        
    }
    
    /**
     *
     * @param usuarioId, id del usuario al que se le desvinculará el blog
     * @param blogId, id del blog a desvincular del usuario
     */
    public void removeBlog(Long usuarioId, Long blogId) {
        UsuarioEntity entity = findById(usuarioId);
        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setId(blogId);
        int index = entity.getBlogsFavoritos().indexOf(blogEntity);
        if(index < 0) {
            throw new NotFoundException("No existe el evento con el id dado en el grupo con id dado");
        }
        entity.getBlogsFavoritos().remove(blogEntity);
        updateUser(usuarioId, entity);
    }
}
