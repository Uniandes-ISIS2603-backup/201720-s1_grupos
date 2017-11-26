/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.NoticiaDTO;
import co.edu.uniandes.csw.grupos.dtos.NoticiaDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.GrupoLogic;
import co.edu.uniandes.csw.grupos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Recurso usuarioNoticia
 * @author jc161
 */
public class UsuarioNoticiaResource {
    /**
     * Lógica del grupo
     */
    @Inject
    private UsuarioLogic usuarioLogic;
       /**
     * Lógica del blog
     */
    @Inject
    private GrupoLogic grupoLogic;
    
    /**
     * Convierte una lista de NoticiaEntity a una lista de NoticiaDetailDTO.
     *
     * @param entityList Lista de NoticiaEntity a convertir.
     * @return Lista de NoticiaDetailDTO convertida.
     *
     */
    private List<NoticiaDTO> noticiasListEntity2DTO(List<NoticiaEntity> entityList) {
        List<NoticiaDTO> list = new ArrayList<>();
        for (NoticiaEntity entity : entityList) {
            list.add(new NoticiaDTO(entity));
        }
        return list;
    }
    
    /**
     * Obtiene una colección de instancias de NoticiaDetailDTO asociadas a una
     * instancia de Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @return Colección de instancias de NoticiaDetailDTO asociadas a la
     * instancia de Grupo
     *
     */
    @GET
    public List<NoticiaDTO> listNoticias(@PathParam("usuarioId") Long id) throws BusinessException {
        try
        {
            return noticiasListEntity2DTO(usuarioLogic.getNoticias(id));
        }
        catch(javax.ejb.EJBTransactionRolledbackException e)
        {
            throw new NotFoundException("La noticia que busca no existe en el sistema.");
        }
        
       
    }
    
    /**
     * Obtiene una instancia de Noticia asociada a una instancia de Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param NoticiasId Identificador de la instancia de Noticia
     * @return
     *
     */
    @GET
    @Path("{NoticiaId: \\d+}")
    public NoticiaDetailDTO getNoticias(@PathParam("usuarioId") Long usuarioId, @PathParam("NoticiaId") Long noticiaId) throws BusinessException {
        try
        {
            NoticiaEntity e =usuarioLogic.getNoticia(usuarioId, noticiaId);
             if(e==null) 
             {
                 throw new NotFoundException("No existe lo buscado");
             }
             return new NoticiaDetailDTO(e);
        }
        catch(javax.ejb.EJBTransactionRolledbackException e)
        {
            throw new NotFoundException("La noticia que busca no existe en el sistema.");
        }
        catch (javax.ejb.EJBException e)
        {
            throw e;
        }
       
    }
    /**
     * Desasocia un Noticia existente de un Grupo existente
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param NoticiasId Identificador de la instancia de Noticia
     *
     */
    @DELETE
    @Path("{NoticiaId: \\d+}")
    public void removeNoticias(@PathParam("usuarioId") Long usuarioId, @PathParam("NoticiaId") Long noticiaId) throws BusinessException {
        try
        {
            NoticiaEntity noticia=usuarioLogic.getNoticia(usuarioId, noticiaId);
            usuarioLogic.removeNoticia(usuarioId, noticiaId);
        }
        catch (javax.ejb.EJBException e)
        {
            throw e;
        }
        
    }
    /**
     * Actualizar una noticia.<br>
     * @param usuarioId Id del usuario.<br>
     * @param noticiaId Id de la noticia.<br>
     * @param newNoticia dto.<br>
     * @return Dto actualizado.<br>
     * @throws BusinessException Excepción de negocio.
     */
    @PUT
    @Path("{NoticiaId: \\d+}")
    public NoticiaDetailDTO updateNoticia(@PathParam("usuarioId") Long usuarioId, @PathParam("NoticiaId") Long noticiaId, NoticiaDetailDTO newNoticia) throws BusinessException {
        try
        {
            NoticiaEntity e = newNoticia.toEntity();
            return new NoticiaDetailDTO(usuarioLogic.updateNoticia(usuarioId, noticiaId,e));
        }
        catch (javax.ejb.EJBException e)
        {
            throw e;
        }
        
    }
    /**
     * Obtiene multimedia de una noticia.<br>
     * @param usuarioId Id del usuario.<br>
     * @param idNoticia Id de la noticia.<br>
     * @return Clase de noticia multimedia como subrecurso.<br>
     * @throws BusinessException Excepción de negocio.
     */
    @Path("{noticiaid:\\d+}/multimedia")
    public Class<NoticiaMultimediaResource> getMultimedia(@PathParam("usuarioId") Long usuarioId, @PathParam("noticiaid")Long idNoticia) throws BusinessException
    {
        try
        {
            if(usuarioLogic.getNoticia( usuarioId, idNoticia)==null)
            {
                throw new NotFoundException("No existe el grupo con este id");
            }
            return NoticiaMultimediaResource.class;
        }
        catch(javax.ejb.EJBTransactionRolledbackException e)
        {
            throw new NotFoundException("La noticia que busca no existe en el sistema.");
        }
        catch (javax.ejb.EJBException e)
        {
            throw e;
        }
        
        
    }
    /**
     * Retorna los comentarios de una noticia.<br>
     * @param usuarioId Id de usuario.<br>
     * @param id Id de la noticia.<br>
     * @return Comentarios de una noticia.<br>
     * @throws BusinessException Excepción de negocio.
     */
    @Path("{noticiaId: \\d+}/comentarios")
    public Class<NoticiaComentarioResource> getComentarios(@PathParam("usuarioId") Long usuarioId,@PathParam("noticiaId") Long id) throws BusinessException {
        if(usuarioLogic.getNoticia( usuarioId, id)==null) 
        {
            throw new NotFoundException("No existe el grupo con este id");
        }
        return NoticiaComentarioResource.class;
    }
}
