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
    private List<NoticiaDTO> NoticiasListEntity2DTO(List<NoticiaEntity> entityList) {
        List<NoticiaDTO> list = new ArrayList<>();
        for (NoticiaEntity entity : entityList) {
            list.add(new NoticiaDTO(entity));
        }
        return list;
    }
    
    /**
     * Convierte una lista de NoticiaDetailDTO a una lista de NoticiaEntity.
     *
     * @param dtos Lista de NoticiaDetailDTO a convertir.
     * @return Lista de NoticiaEntity convertida.
     *
     */
    private List<NoticiaEntity> NoticiasListDTO2Entity(List<NoticiaDetailDTO> dtos) {
        List<NoticiaEntity> list = new ArrayList<>();
        for (NoticiaDetailDTO dto : dtos) {
            list.add(dto.toEntity());
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
        return NoticiasListEntity2DTO(grupoLogic.listNoticias(id));
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
    public NoticiaDetailDTO getNoticias(@PathParam("grupoId") Long grupoId, @PathParam("NoticiaId") Long NoticiaId) throws BusinessException {
        NoticiaEntity e =grupoLogic.getNoticia(grupoId, NoticiaId);
        if(e==null) throw new NotFoundException("No existe lo buscado");
        return new NoticiaDetailDTO(e);
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
        return new NoticiaDetailDTO(grupoLogic.createNoticia(grupoId, noticia.toEntity()));
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
    public NoticiaDetailDTO addNoticias(@PathParam("grupoId") Long grupoId, @PathParam("NoticiaId") Long NoticiaId) throws BusinessException {
        return new NoticiaDetailDTO(grupoLogic.addNoticia(grupoId, NoticiaId));
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
    public void removeNoticias(@PathParam("grupoId") Long grupoId, @PathParam("NoticiaId") Long NoticiaId) throws BusinessException {
        grupoLogic.deleteNoticia(grupoId, NoticiaId);
    }
    /**
     * Actualiza la noticia del grupo.<br>
     * @param grupoId Id del grupo.<br>
     * @param NoticiaId Id de la noticia.<br>
     * @param newNoticia Nueva noticia.<br>
     * @return Noticia actualizada.<br>
     * @throws BusinessException Excepción de negocio.
     */
    @PUT
    @Path("{NoticiaId: \\d+}")
    public NoticiaDetailDTO updateNoticia(@PathParam("grupoId") Long grupoId, @PathParam("NoticiaId") Long NoticiaId, NoticiaDetailDTO newNoticia) throws BusinessException {
        NoticiaEntity e = newNoticia.toEntity();
        return new NoticiaDetailDTO(grupoLogic.updateNoticia(grupoId, NoticiaId,e));
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
        if(grupoLogic.getNoticia( grupoId, idNoticia)==null) throw new NotFoundException("No existe el grupo con este id");
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
        if(grupoLogic.getNoticia( grupoId, id)==null) throw new NotFoundException("No existe el grupo con este id");
        return NoticiaComentarioResource.class;
    }
}
