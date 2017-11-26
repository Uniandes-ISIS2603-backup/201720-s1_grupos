/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.NoticiaDTO;
import co.edu.uniandes.csw.grupos.dtos.NoticiaDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.GrupoLogic;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * URI: grupos/{gruposId: \\d+}/noticias
 * @author cm.sarmiento10
 */
public class GrupoNoticiasResource {
    /**
     * Lógica del grupo
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
    public List<NoticiaDTO> listNoticias(@PathParam("grupoId") Long id) {
        try
        {
            return noticiasListEntity2DTO(grupoLogic.listNoticias(id));
        }
        catch (javax.ejb.EJBException e)
        {
            throw e;
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
    public NoticiaDetailDTO getNoticias(@PathParam("grupoId") Long grupoId, @PathParam("NoticiaId") Long noticiaId) throws BusinessException {
        try
        {
            NoticiaEntity e =grupoLogic.getNoticia(grupoId, noticiaId);
            if(e==null) 
            {
                throw new NotFoundException("No existe lo buscado");
            }
            return new NoticiaDetailDTO(e);
        }
        catch (javax.ejb.EJBException e)
        {
            throw e;
        }
    }
    /**
     * Agrega una noticia nueva al sistema.<br>
     * @param grupoId Id del grupo.<br>
     * @param noticia Noticia a gregar.<br>
     * @return Noticia agregada.<br>
     * @throws BusinessException Excepción de negocio
     */
    @POST
    public NoticiaDetailDTO addNoticia(@PathParam("grupoId") Long grupoId, NoticiaDetailDTO noticia) throws BusinessException {
        try
        {
            return new NoticiaDetailDTO(grupoLogic.createNoticia(grupoId, noticia.toEntity()));
        }
        catch (javax.ejb.EJBException e)
        {
            throw e;
        }
    }
    
    /**
     * Asocia un Noticia existente a un Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param NoticiasId Identificador de la instancia de Noticia
     * @return Instancia de NoticiaDetailDTO que fue asociada a Grupo
     *
     */
    @POST
    @Path("{NoticiaId: \\d+}")
    public NoticiaDetailDTO addNoticias(@PathParam("grupoId") Long grupoId, @PathParam("NoticiaId") Long noticiaId) throws BusinessException {
        try
        {
            return new NoticiaDetailDTO(grupoLogic.addNoticia(grupoId, noticiaId));
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
    public void removeNoticias(@PathParam("grupoId") Long grupoId, @PathParam("NoticiaId") Long noticiaId) throws BusinessException {
        try
        {
            grupoLogic.deleteNoticia(grupoId, noticiaId);
        }
        catch (javax.ejb.EJBException e)
        {
            throw e;
        }
    }
    /**
     * Actualiza la noticia del grupo.<br>
     * @param grupoId Id del grupo.<br>
     * @param noticiaId Id de la noticia.<br>
     * @param newNoticia Nueva noticia.<br>
     * @return Noticia actualizada.<br>
     * @throws BusinessException Excepción de negocio.
     */
    @PUT
    @Path("{NoticiaId: \\d+}")
    public NoticiaDetailDTO updateNoticia(@PathParam("grupoId") Long grupoId, @PathParam("NoticiaId") Long noticiaId, NoticiaDetailDTO newNoticia) throws BusinessException {
        try
        {
            NoticiaEntity e = newNoticia.toEntity();
            return new NoticiaDetailDTO(grupoLogic.updateNoticia(grupoId, noticiaId,e));
        }
        catch (javax.ejb.EJBException e)
        {
            throw e;
        }
    }
    /**
     * Una calse de multimedia con noticia.<br>
     * @param grupoId Id del grupo.<br>
     * @param idNoticia Id de la noticia.<br>
     * @return Clase de multimedia.<br>
     * @throws BusinessException Excepción de negocio.
     */
    @Path("{noticiaid:\\d+}/multimedia")
    public Class<NoticiaMultimediaResource> getMultimedia(@PathParam("grupoId") Long grupoId, @PathParam("noticiaid")Long idNoticia) throws BusinessException
    {
        try
        {
            if(grupoLogic.getNoticia( grupoId, idNoticia)==null) 
            {
                throw new NotFoundException("No existe el grupo con este id");
            }
        }
        catch (javax.ejb.EJBTransactionRolledbackException e)
        {
            throw new NotFoundException();
        }
        catch (javax.ejb.EJBException e)
        {
            throw e;
        }
        return NoticiaMultimediaResource.class;
    }
    /**
     * Da los comentarios de una noticia.<br>
     * @param grupoId Id del grupo.<br>
     * @param id Id de la noticia.<br>
     * @return NoticiaComentarioResource<br>
     * @throws BusinessException Excepción de negocio.
     */
    @Path("{noticiaId: \\d+}/comentarios")
    public Class<NoticiaComentarioResource> getComentarios(@PathParam("grupoId") Long grupoId,@PathParam("noticiaId") Long id) throws BusinessException {
        if(grupoLogic.getNoticia( grupoId, id)==null) 
        {
            throw new NotFoundException("No existe el grupo con este id");
        }
        return NoticiaComentarioResource.class;
    }
}
